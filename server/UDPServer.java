package server;

import common.Constants;
import common.Utils;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by signapoop on 1/4/19.
 */
class UDPServer {

    private DatagramSocket udpSocket;
    private int port;
    private int idCounter;
    private HashMap<ClientRecord, byte[]> memo;

    private double failProb;

    public UDPServer(int port, boolean debug) throws SocketException, UnknownHostException {
        this.udpSocket = new DatagramSocket(port);
        this.port = port;
        this.idCounter = 0;
        this.memo = new HashMap<>();
        this.failProb = Constants.DEFAULT_SERVER_FAILURE_PROB;
    }

    public static void main(String[] args)throws Exception {
        boolean moreQuotes = true;

        int port = Constants.DEFAULT_SERVER_PORT;
        boolean debug = true;
        boolean handled;

        UDPServer udpServer = new UDPServer(port, debug);

        FlightManager flightManager = new FlightManager();
        flightManager.initialiseDummyData();

        while (moreQuotes) {
            try {
                ClientMessage message = udpServer.receive(true);
                if (debug) message.print();

                if (Constants.InvoSem.DEFAULT != Constants.InvoSem.AT_MOST_ONCE) handled = false;
                else handled = udpServer.checkAndSendOldResponse(message);
                if (!handled) {
                    int curID = udpServer.getID();
                    byte[] packageByte;
                    switch (message.serviceType) {
                        case Constants.SERVICE_GET_FLIGHT_DETAILS:
                            packageByte = ServerFlightDetails.handleResponse(curID, message.payload, flightManager);
                            udpServer.updateMemo(message, packageByte);
                            udpServer.send(packageByte, message.clientAddress, message.clientPort);
                            break;
                        case Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION:
                            packageByte = ServerFlightsBySourceDestination.handleResponse(curID, message.payload, flightManager);
                            udpServer.updateMemo(message, packageByte);
                            udpServer.send(packageByte, message.clientAddress, message.clientPort);
                            break;
                        case Constants.SERVICE_RESERVE_SEATS:
                            packageByte = ServerReserveSeats.handleResponse(curID, message.payload, flightManager);
                            udpServer.updateMemo(message, packageByte);
                            udpServer.send(packageByte, message.clientAddress, message.clientPort);
                            break;
                        case Constants.SERVICE_MONITOR_AVAILABILITY:
                            packageByte = ServerMonitorAvailability.handleResponse(curID, message.payload, flightManager,
                                    message.clientAddress, message.clientPort, udpServer.udpSocket);
                            udpServer.updateMemo(message, packageByte);
                            udpServer.send(packageByte, message.clientAddress, message.clientPort);
                            break;
                        case Constants.SERVICE_GET_FLIGHTS_BY_PRICE:
                            packageByte = ServerFlightsByPrice.handleResponse(curID, message.payload, flightManager);
                            udpServer.updateMemo(message, packageByte);
                            udpServer.send(packageByte, message.clientAddress, message.clientPort);
                            break;
                        default:
                            System.out.println(Constants.UNRECOGNIZE_SVC_MSG);
                    }
                }
                System.out.println(Constants.SEPARATOR);

            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        udpServer.udpSocket.close();
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

    public void send(byte[] message, InetAddress clientAddress, int clientPort) throws IOException, InterruptedException{
        if (Math.random() < this.failProb) {
            System.out.println("Server dropping packet to simulate lost request");
        }
        else {
            byte[] header = Utils.marshal(message.length);
            DatagramPacket headerPacket = new DatagramPacket(header, header.length, clientAddress, clientPort);
            this.udpSocket.send(headerPacket);

            DatagramPacket sendPacket = new DatagramPacket(message, message.length, clientAddress, clientPort);
            this.udpSocket.send(sendPacket);
        }
    }


    public ClientMessage receive(boolean monitor) throws IOException, InterruptedException{

        DatagramPacket receivePacket;
        byte[] header = new byte[4];
        DatagramPacket headerPacket = new DatagramPacket(header, header.length);
        this.udpSocket.receive(headerPacket);

        int messageLength = Utils.unmarshalInteger(headerPacket.getData(), 0);

        byte[] receiveData = new byte[messageLength];
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        this.udpSocket.receive(receivePacket);

        int responseID = Utils.unmarshalInteger(receivePacket.getData(), 0);
        int serviceType = Utils.unmarshalInteger(receivePacket.getData(), Constants.INT_SIZE);
        InetAddress clientAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();

        return new ClientMessage(
                responseID,
                Arrays.copyOfRange(receivePacket.getData(), 2*Constants.INT_SIZE, messageLength),
                clientAddress,
                clientPort,
                serviceType,
                messageLength
        );
    }

    public boolean checkAndSendOldResponse(ClientMessage message){
        ClientRecord record = new ClientRecord(message.clientAddress, message.clientPort, message.responseId);
        boolean isKeyPresent = this.memo.containsKey(record);
        if (isKeyPresent) {
            byte[] packageByte = this.memo.get(record);
            try{
                System.out.println("Duplicate request detected. Resending...");
                this.send(packageByte, message.clientAddress, message.clientPort);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return isKeyPresent;
    }

    public void updateMemo(ClientMessage message, byte[] payload){
        ClientRecord record = new ClientRecord(message.clientAddress, message.clientPort, message.responseId);
        this.memo.put(record, payload);
    }
}
