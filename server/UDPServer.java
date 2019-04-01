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

        while (moreQuotes) {
            try {
                byte[] buf = udpServer.receive(true);
                int serviceType = Utils.unmarshalInteger(buf, 0);
                switch (serviceType) {
                    case Constants.SERVICE_GET_FLIGHT_DETAILS:
                        ServerFlightDetails.handleResponse(buf);
                        // handle request, send back datagram packet.
                        // send the response to the client at "address" and "port"
//                        InetAddress client_address = buf.getAddress();
//                        int client_port = buf.getPort();
//                        packet = new DatagramPacket(buf, buf.length, address, port);
//                        socket.send(packet);
//                        break;
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
        System.out.println(responseID);

        return Arrays.copyOfRange(receivePacket.getData(), Constants.INT_SIZE, messageLength);
    }
}
