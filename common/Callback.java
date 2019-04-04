package common;

import common.schema.MonitorAvailabilityReply;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Callback {
    private final DatagramSocket udpSocket;
    private final InetAddress clientAddress;
    private final int clientPort;
    private int flightId;
    private long expiry;

    public Callback(int flightId, long expiry, InetAddress clientAddress, int clientPort, DatagramSocket udpSocket) {
        this.flightId = flightId;
        this.expiry = expiry;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.udpSocket = udpSocket;
    }

    public void update(int availability) throws IOException, InterruptedException {
        // need to do some sending
        byte[] packageByte = this.constructMessage(0, availability); // ID is just arbitrarily set as 0 for now
        packageByte = addHeaders(packageByte, 0, Constants.SERVICE_MONITOR_AVAILABILITY);
        this.send(packageByte);
    }

    private byte[] constructMessage(int id, int availability) throws UnsupportedEncodingException {
        MonitorAvailabilityReply reply = new MonitorAvailabilityReply(id, Constants.MONITORING_NEW_UPDATE_STATUS, this.flightId, -1, availability);
        return Utils.marshal(reply);
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

    public int getClientPort() {
        return clientPort;
    }

    private void send(byte[] message) throws IOException {
        byte[] header = Utils.marshal(message.length);
        DatagramPacket headerPacket = new DatagramPacket(header, header.length, this.clientAddress, this.clientPort);
        this.udpSocket.send(headerPacket);

        DatagramPacket sendPacket = new DatagramPacket(message, message.length, this.clientAddress, this.clientPort);
        this.udpSocket.send(sendPacket);
    }

    private byte[] addHeaders(byte[] packageByte, int id, int serviceNum) throws IOException {
        List message = new ArrayList();
        Utils.append(message, id);
        Utils.append(message, serviceNum);
        byte[] header = Utils.byteUnboxing(message);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(header);
        baos.write(packageByte);

        return baos.toByteArray();
    }

}

