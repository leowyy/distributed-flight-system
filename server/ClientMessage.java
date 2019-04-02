package server;

import common.Constants;

import java.net.InetAddress;

/**
 * Created by signapoop on 2/4/19.
 */
public class ClientMessage {
    int responseId;
    byte[] message;
    InetAddress clientAddress;
    int clientPort;
    int serviceType;
    int messageLength;

    public ClientMessage(int responseId, byte[] message, InetAddress clientAddress,
                         int clientPort, int serviceType, int messageLength) {
        this.responseId = responseId;
        this.message = message;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.serviceType = serviceType;
        this.messageLength = messageLength;
    }

    public void print() {
        System.out.printf(Constants.PRINT_CLIENT_MESSAGE, responseId, serviceType, clientAddress, clientPort, messageLength);
    }
}
