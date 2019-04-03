package client;
import common.Constants;
import common.Utils;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

import java.util.concurrent.TimeoutException;

/**
 * Created by signapoop on 1/4/19.
 */
class UDPClient {

    private DatagramSocket udpSocket;
    private InetAddress IPAddress;
    private int serverPort;
    private int idCounter;

    // Invocation Semantics
    private int invSem;

    // Timeout properties
    private int maxTime;
    private int maxTries;

    // Failure Probability
    private double failProb;


    public UDPClient(String ip, int serverPort) throws SocketException, UnknownHostException {
        this.udpSocket = new DatagramSocket();
        this.IPAddress = InetAddress.getByName(ip);
        this.serverPort = serverPort;
        this.idCounter = 0;
        this.invSem = Constants.InvoSem.DEFAULT;
//        this.setMaxTime(Constants.Timeout.DEFAULT_NO_TIME);
        setMaxTime(Constants.Timeout.DEFAULT_NO_TIME);
        this.maxTries = Constants.Timeout.DEFAULT_MAX_TRIES;
        this.failProb = Constants.DEFAULT_FAILURE_PROB;
    }

    public static void main(String[] args)throws Exception {

        String host = Constants.DEFAULT_HOST;
        int serverPort = Constants.DEFAULT_SERVER_PORT;

        UDPClient udpClient = new UDPClient(host, serverPort);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while(!exit) {
            System.out.println(Constants.GET_FLIGHT_BY_SOURCE_DESTINATION_SVC_MSG);
            System.out.println(Constants.GET_FLIGHT_DETAILS_SVC_MSG);
            System.out.println(Constants.RESERVE_SEATS_SVC_MSG);
            System.out.println(Constants.MONITOR_FLIGHT_AVAILABILITY_SVC_MSG);
            System.out.println(Constants.IDEMPOTENT_SERVICE);
            System.out.println(Constants.NON_IDEMPOTENT_SERVICE);
            System.out.println(Constants.EXIT_SVC_MSG);

            String message = scanner.nextLine();
            int serviceType = Integer.parseInt(message);

            byte[] packageByte;
            byte[] response;
            int curID = udpClient.getID();
            switch (serviceType) {
                case Constants.SERVICE_GET_FLIGHT_DETAILS:
                    packageByte = HandleFlightDetails.constructMessage(scanner, curID);
                    response = udpClient.sendAndReceive(packageByte);
                    HandleFlightDetails.handleResponse(response);
                    break;
                case Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION:
                    packageByte = HandleFlightsBySourceDestination.constructMessage(scanner, curID);
                    response = udpClient.sendAndReceive(packageByte);
                    HandleFlightsBySourceDestination.handleResponse(response);
                    break;
                case Constants.SERVICE_RESERVE_SEATS:
                    packageByte = HandleReserveSeats.constructMessage(scanner, curID);
                    response = udpClient.sendAndReceive(packageByte);
                    HandleReserveSeats.handleResponse(response);
                    break;
                case Constants.SERVICE_MONITOR_AVAILABILITY:
                    int origMaxTime = udpClient.maxTime;
                    packageByte = HandleMonitorAvailability.constructMessage(scanner, curID);
                    response = udpClient.sendAndReceive(packageByte);
                    int monitorInterval = HandleMonitorAvailability.handleResponse(response);
                    long expiry = System.currentTimeMillis() + (monitorInterval * 1000);
                    try {
                        int outstandingTime = (int) (expiry - System.currentTimeMillis());
                        while (outstandingTime > 0){
                            udpClient.setMaxTime(outstandingTime);
                            byte[] update = udpClient.receive(true);
                            HandleMonitorAvailability.handleResponse(update);
                            outstandingTime = (int) (expiry - System.currentTimeMillis());
                        }
                        udpClient.setMaxTime(origMaxTime);
                        System.out.println(Constants.MONITORING_END_MSG);
                        System.out.println();
                        System.out.println(Constants.SEPARATOR);
                    } catch (SocketTimeoutException e){
                        udpClient.setMaxTime(origMaxTime);
                        System.out.println(Constants.MONITORING_END_MSG);
                        System.out.println();
                        System.out.println(Constants.SEPARATOR);
                    }
                    break;
                case Constants.SERVICE_EXIT:
                    System.out.println(Constants.EXIT_MSG);
                    exit = true;
                    break;
                default:
                    System.out.println(Constants.UNRECOGNIZE_SVC_MSG);
            }
            System.out.println(Constants.SEPARATOR);

            }
        }


    public int getInvSem() {
        return invSem;
    }

    public void setInvSem(int invSem){
        this.invSem = invSem;
    }

    public void setupInvSem(int invSem, int maxTime) throws SocketException {
        this.invSem = invSem;
        if (invSem != 0) {
            setMaxTime(maxTime);
        }
    }

    public void setupInvSem(int invSem, int maxTime, int maxTries) throws SocketException {
        this.invSem = invSem;
        if (invSem != 0) {
            setMaxTime(maxTime);
            setMaxTries(maxTries);
        }
    }

    public void setMaxTime (int maxTime) throws SocketException {
        udpSocket.setSoTimeout(maxTime);
        this.maxTime = maxTime;
    }

    public void setMaxTries (int maxTries) {
        this.maxTries = maxTries;
    }

    public double getFailProb() {
        return failProb;
    }

    public void setFailProb(double failProb) {
        this.failProb = failProb;
    }

    /**
     * Get new ID and increment global ID
     * @return {@code int} new ID
     * @since 1.9
     */
    public int getID(){
        this.idCounter++;
        return this.idCounter;
    }

    public void send(byte[] message) throws IOException, InterruptedException{
        if (Math.random() < this.failProb){
            System.out.println("Client dropping packet to simulate lost request.");
        } else {
            byte[] header = Utils.marshal(message.length);
            DatagramPacket headerPacket = new DatagramPacket(header, header.length, this.IPAddress, this.serverPort);
            this.udpSocket.send(headerPacket);

            DatagramPacket sendPacket = new DatagramPacket(message, message.length, this.IPAddress, this.serverPort);
            this.udpSocket.send(sendPacket);
        }
    }


    public byte[] receive(boolean monitor) throws IOException, InterruptedException{
        int responseID;
        int messageLength;
        DatagramPacket receivePacket;

        byte[] header = new byte[4];
        DatagramPacket headerPacket = new DatagramPacket(header, header.length);
        this.udpSocket.receive(headerPacket);

        messageLength = Utils.unmarshalInteger(headerPacket.getData(), 0);

        byte[] receiveData = new byte[messageLength];
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        this.udpSocket.receive(receivePacket);
        responseID = Utils.unmarshalInteger(receivePacket.getData(), 0);

        return Arrays.copyOfRange(receivePacket.getData(), Constants.INT_SIZE, messageLength);
    }

    public byte[] sendAndReceive(byte[] message) throws IOException, InterruptedException, TimeoutException{
        byte[] response = new byte[0];
        int tries = 0;
        do{
            try{
                this.send(message);
                response = this.receive(false);
                break;
            } catch(SocketTimeoutException e){
                tries++;
                if (this.maxTries > 0 && tries >= this.maxTries){
                    throw new TimeoutException(String.format("Max tries of %d reached.", this.maxTries));
                }
                System.out.printf("Timeout %d, retrying...\n", tries);
                continue;
            }
        } while(this.getInvSem() != Constants.InvoSem.NONE);
        return response;
    }

}
