package client;
import common.Constants;
import common.Utils;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by signapoop on 1/4/19.
 */
class UDPClient {

    private DatagramSocket clientSocket;
    private InetAddress IPAddress;
    private int port;
    private int idCounter;


    public UDPClient(String ip, int port, boolean debug) throws SocketException, UnknownHostException {
        this.clientSocket = new DatagramSocket();
        this.IPAddress = InetAddress.getByName(ip);
        this.port = port;
        this.idCounter = 0;
    }

    public static void main(String[] args)throws Exception {

        String host = Constants.DEFAULT_HOST;
        int port = Constants.DEFAULT_PORT;
        boolean debug = true;

        UDPClient udpClient = new UDPClient(host, port, debug);

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
                    udpClient.send(packageByte);
                    response = udpClient.receive(false);
                    HandleFlightDetails.handleResponse(response);
                    break;
                case Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION:
                    packageByte = HandleFlightsBySourceDestination.constructMessage(scanner, curID);
                    udpClient.send(packageByte);
                    response = udpClient.receive(false);
                    HandleFlightsBySourceDestination.handleResponse(response);
                    break;
                case Constants.SERVICE_RESERVE_SEATS:
                    packageByte = HandleReserveSeats.constructMessage(scanner, curID);
                    udpClient.send(packageByte);
                    response = udpClient.receive(false);
                    HandleReserveSeats.handleResponse(response);
                    break;
                case Constants.SERVICE_MONITOR_AVAILABILITY:
                    packageByte = HandleMonitorAvailability.constructMessage(scanner, curID);
                    udpClient.send(packageByte);
                    response = udpClient.receive(true);
                    HandleMonitorAvailability.handleResponse(response);
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

        byte[] header = Utils.marshal(message.length);
        DatagramPacket headerPacket = new DatagramPacket(header, header.length, this.IPAddress, this.port);
        this.clientSocket.send(headerPacket);

        DatagramPacket sendPacket = new DatagramPacket(message, message.length, this.IPAddress, this.port);
        this.clientSocket.send(sendPacket);
    }


    public byte[] receive(boolean monitor) throws IOException, InterruptedException{
        int responseID;
        int messageLength;
        DatagramPacket receivePacket;

        byte[] header = new byte[4];
        DatagramPacket headerPacket = new DatagramPacket(header, header.length);
        this.clientSocket.receive(headerPacket);

        messageLength = Utils.unmarshalInteger(headerPacket.getData(), 0);

        byte[] receiveData = new byte[messageLength];
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        this.clientSocket.receive(receivePacket);
        responseID = Utils.unmarshalInteger(receivePacket.getData(), 0);

        return Arrays.copyOfRange(receivePacket.getData(), Constants.INT_SIZE, messageLength);
    }

}
