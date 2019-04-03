package server;

import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


// for UDPServer: packageByte = ServerFlightsBySourceDestination.handleResponse(curID, message.payload, flightManager,
// message.clientAddress, message.clientPort, this.clientSocket);

public class ServerMonitorAvailability {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager, InetAddress inetAddress,
                                        int port, DatagramSocket udpSocket) throws UnsupportedEncodingException {
        // Deconstruct message
        int ptr = 0;

        int flightId = Utils.unmarshalMsgInteger(message, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;
        int duration = Utils.unmarshalMsgInteger(message, ptr);

        flightManager.registerCallback(flightId, duration, inetAddress, port, udpSocket);

        // Construct response
        return constructMessage(id, flightId);
    }

    public static byte[] constructMessage(int id, int flightId) throws UnsupportedEncodingException {
        List message = new ArrayList();

        // message to say that monitoring has started
        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_MONITOR_AVAILABILITY);
        Utils.append(message, Constants.SUCCESS_STATUS);
        Utils.appendMessage(message, flightId);

        return Utils.byteUnboxing(message);
    }
}
