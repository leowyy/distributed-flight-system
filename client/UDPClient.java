package client;
import common.Constants;
import common.Utils;

import java.io.IOException;
import java.net.*;
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
            System.out.println(Constants.EXIT_SVC_MSG);

            String message = scanner.nextLine();
            int serviceType = Integer.parseInt(message);
            System.out.println();

            byte[] packageByte;
            int curID = udpClient.getID();
            switch (serviceType) {
                case Constants.SERVICE_GET_FLIGHT_DETAILS:
                    float airfare = 0.7F;
                    packageByte = HandleFlightDetails.constructMessage(curID,2,2,3, airfare, "hello");
                    udpClient.send(packageByte);
                    // need to receive and handle response
                    break;
                case Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION:
                    int[] flightIds = {0, 2, 3, 4};
                    packageByte = HandleFlightsBySourceDestination.constructMessage(curID, flightIds);
                    udpClient.send(packageByte);
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
}
