package server;

import common.Constants;
import common.Utils;

import java.net.*;
import java.io.*;
import java.util.Arrays;

/**
 * Created by signapoop on 1/4/19.
 */
class UDPServer {

    private DatagramSocket clientSocket;
    private int port;
    private int idCounter;

    public UDPServer(int port, boolean debug) throws SocketException, UnknownHostException {
        this.clientSocket = new DatagramSocket(port);
        this.port = port;
        this.idCounter = 0;
    }

    public static void main(String[] args)throws Exception {
        boolean moreQuotes = true;

        int port = Constants.DEFAULT_PORT;
        boolean debug = true;

        UDPServer udpServer = new UDPServer(port, debug);

        FlightManager flightManager = new FlightManager();
        flightManager.initialiseDummyData();

        while (moreQuotes) {
            try {
                ClientMessage message = udpServer.receive(true);
                if (debug) message.print();

                switch (message.serviceType) {
                    case Constants.SERVICE_GET_FLIGHT_DETAILS:
                        ServerFlightDetails.handleResponse(message, flightManager);
                        break;
                    default:
                        System.out.println(Constants.UNRECOGNIZE_SVC_MSG);
                }
                System.out.println(Constants.SEPARATOR);

            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        udpServer.clientSocket.close();
    }

    public ClientMessage receive(boolean monitor) throws IOException, InterruptedException{

        DatagramPacket receivePacket;
        byte[] header = new byte[4];
        DatagramPacket headerPacket = new DatagramPacket(header, header.length);
        this.clientSocket.receive(headerPacket);

        int messageLength = Utils.unmarshalInteger(headerPacket.getData(), 0);

        byte[] receiveData = new byte[messageLength];
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        this.clientSocket.receive(receivePacket);

        int responseID = Utils.unmarshalInteger(receivePacket.getData(), 0);
        int serviceType = Utils.unmarshalInteger(receivePacket.getData(), Constants.INT_SIZE);
        InetAddress client_address = receivePacket.getAddress();
        int client_port = receivePacket.getPort();

        return new ClientMessage(
                responseID,
                Arrays.copyOfRange(receivePacket.getData(), Constants.INT_SIZE, messageLength),
                client_address,
                client_port,
                serviceType,
                messageLength
        );
    }
}
