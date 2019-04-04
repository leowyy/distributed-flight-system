package server;

import common.Constants;
import common.Utils;
import common.schema.MonitorAvailabilityReply;
import common.schema.MonitorAvailabilityRequest;

import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
}
