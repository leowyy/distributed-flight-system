package common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Callback {
    private final DatagramSocket udpSocket;
    private int flightId;
    private long expiry;
    private final InetAddress clientAddress;
    private final int clientPort;

    public Callback (int flightId, long expiry, InetAddress clientAddress, int clientPort, DatagramSocket udpSocket) {
        this.flightId = flightId;
        this.expiry = expiry;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.udpSocket = udpSocket;
    }
    public void update (int availability) throws IOException, InterruptedException {
        // need to do some sending
        byte[] packageByte = this.constructMessage(0, availability); // ID is just arbitrarily set as 0 for now
        this.send(packageByte);
    }

    private byte[] constructMessage(int id, int availability) throws UnsupportedEncodingException {
        List message = new ArrayList();
        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_MONITOR_AVAILABILITY);
        Utils.append(message, Constants.MONITORING_NEW_UPDATE_STATUS);
        Utils.appendMessage(message, this.flightId);
        Utils.appendMessage(message, availability);

        return Utils.byteUnboxing(message);
    }

    public int getFlightId() {
        return flightId;
    }

    public InetAddress getClientAddress() {
        return clientAddress;
    }

    public Boolean hasExpired(long currentTime) {
        return currentTime > this.expiry;
    }

    private long getRemainingTime() {
        long currentTime = System.currentTimeMillis();
        return this.expiry - currentTime;
    }

    public int getClientPort() {
        return clientPort;
    }

    private void send(byte[] message) throws IOException, InterruptedException{
        byte[] header = Utils.marshal(message.length);
        DatagramPacket headerPacket = new DatagramPacket(header, header.length, this.clientAddress, this.clientPort);
        this.udpSocket.send(headerPacket);

        DatagramPacket sendPacket = new DatagramPacket(message, message.length, this.clientAddress, this.clientPort);
        this.udpSocket.send(sendPacket);
    }
}

