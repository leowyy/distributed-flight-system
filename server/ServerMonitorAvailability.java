package server;

import common.Constants;
import common.Utils;
import common.schema.MonitorAvailabilityReply;
import common.schema.MonitorAvailabilityRequest;

import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetAddress;


// for UDPServer: packageByte = ServerFlightsBySourceDestination.handleResponse(curID, message.payload, flightManager,
// message.clientAddress, message.clientPort, this.clientSocket);

public class ServerMonitorAvailability {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager, InetAddress inetAddress,
                                        int port, DatagramSocket udpSocket) throws UnsupportedEncodingException {
        // Deconstruct message
        MonitorAvailabilityRequest request = (MonitorAvailabilityRequest) Utils.unmarshal(message, new MonitorAvailabilityRequest());
        int flightId = request.getFlightId();
        int duration = request.getDuration();

        flightManager.registerCallback(flightId, duration, inetAddress, port, udpSocket);

        // Construct response
        MonitorAvailabilityReply reply = new MonitorAvailabilityReply(id, Constants.FLIGHT_FOUND_STATUS, flightId, duration, -1);

        return Utils.marshal(reply);
    }

//    public static byte[] constructMessage(int id, int flightId, int duration) throws UnsupportedEncodingException {
//        List message = new ArrayList();
//
//        // message to say that monitoring has started
//        Utils.append(message, id);
//        Utils.append(message, Constants.SERVICE_MONITOR_AVAILABILITY);
//        Utils.append(message, Constants.FLIGHT_FOUND_STATUS);
//        Utils.appendMessage(message, flightId);
//        Utils.appendMessage(message, duration);
//
//        return Utils.byteUnboxing(message);
//    }
}
