package client;
import common.Constants;
import common.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        this.invSem = Constants.InvoSem.NONE;
        setMaxTime(Constants.Timeout.DEFAULT_NO_TIME);
        this.maxTries = Constants.Timeout.DEFAULT_MAX_TRIES;
        this.failProb = Constants.DEFAULT_CLIENT_FAILURE_PROB;
    }

    public static void main(String[] args)throws Exception {

        String host = Constants.DEFAULT_HOST;
        int serverPort = Constants.DEFAULT_SERVER_PORT;

        UDPClient udpClient = new UDPClient(host, serverPort);
        udpClient.setInvSem(Constants.InvoSem.DEFAULT);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println(Constants.WELCOME_MSG);
        System.out.println(Constants.REQUEST_ACC_ID_MSG);
        String accountIdString = scanner.nextLine();
        int accountId = Integer.parseInt(accountIdString);

        while(!exit) {
            System.out.println(Constants.GET_FLIGHT_BY_SOURCE_DESTINATION_SVC_MSG);
            System.out.println(Constants.GET_FLIGHT_DETAILS_SVC_MSG);
            System.out.println(Constants.RESERVE_SEATS_SVC_MSG);
            System.out.println(Constants.MONITOR_FLIGHT_AVAILABILITY_SVC_MSG);
            System.out.println(Constants.FIND_FLIGHT_BY_PRICE_SVC_MSG);
            System.out.println(Constants.TOP_UP_ACCOUNT_MSG);
            System.out.println(Constants.EXIT_SVC_MSG);

            String message = scanner.nextLine();
            int serviceType = Integer.parseInt(message);

            byte[] packageByte;
            byte[] response;
            int curID = udpClient.getID();
            switch (serviceType) {
                case Constants.SERVICE_GET_FLIGHT_DETAILS:
                    packageByte = HandleFlightDetails.constructMessage(scanner, curID);
                    packageByte = udpClient.addHeaders(packageByte, curID, serviceType);
                    response = udpClient.sendAndReceive(packageByte);
                    HandleFlightDetails.handleResponse(response);
                    break;
                case Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION:
                    packageByte = HandleFlightsBySourceDestination.constructMessage(scanner, curID);
                    packageByte = udpClient.addHeaders(packageByte, curID, serviceType);
                    response = udpClient.sendAndReceive(packageByte);
                    HandleFlightsBySourceDestination.handleResponse(response);
                    break;
                case Constants.SERVICE_RESERVE_SEATS:
                    packageByte = HandleReserveSeats.constructMessage(scanner, accountId, curID);
                    packageByte = udpClient.addHeaders(packageByte, curID, serviceType);
                    response = udpClient.sendAndReceive(packageByte);
                    HandleReserveSeats.handleResponse(response);
                    break;
                case Constants.SERVICE_MONITOR_AVAILABILITY:
                    int origMaxTime = udpClient.maxTime;
                    packageByte = HandleMonitorAvailability.constructMessage(scanner, curID);
                    packageByte = udpClient.addHeaders(packageByte, curID, serviceType);
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
                case Constants.SERVICE_GET_FLIGHTS_BY_PRICE:
                    packageByte = HandleFlightsByPrice.constructMessage(scanner, curID);
                    packageByte = udpClient.addHeaders(packageByte, curID, serviceType);
                    response = udpClient.sendAndReceive(packageByte);
                    HandleFlightsByPrice.handleResponse(response);
                    break;
                case Constants.SERVICE_TOP_UP_ACCOUNT:
                    packageByte = HandleTopUpAccount.constructMessage(scanner, accountId, curID);
                    packageByte = udpClient.addHeaders(packageByte, curID, serviceType);
                    response = udpClient.sendAndReceive(packageByte);
                    HandleTopUpAccount.handleResponse(response);
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

    private byte[] addHeaders (byte[] packageByte, int id, int serviceNum) throws IOException  {
        List message = new ArrayList();
        Utils.append(message, id);
        Utils.append(message, serviceNum);
        byte[] header = Utils.byteUnboxing(message);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(header);
        baos.write(packageByte);

        return baos.toByteArray();
    }


    public int getInvSem() {
        return invSem;
    }

    public void setInvSem(int invSem) throws SocketException {
        this.invSem = invSem;
        if (invSem != 0) {
            setMaxTime(Constants.Timeout.DEFAULT_MAX_TIME);
        }
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
                if (this.maxTries > 0 && tries == this.maxTries){
                    throw new TimeoutException(String.format("Max tries of %d reached.", this.maxTries));
                }
                System.out.printf("Timeout %d, retrying...\n", tries);
                continue;
            }
        } while(this.getInvSem() != Constants.InvoSem.NONE);
        return response;
    }

}
