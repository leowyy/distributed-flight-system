package server;

import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by signapoop on 1/4/19.
 */
class ServerFlightDetails {

    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws UnsupportedEncodingException {
        // Deconstruct message
        int ptr = 0;
        int flightId = Utils.unmarshalMsgInteger(message, ptr);

        FlightDetail ret = flightManager.getFlightDetails(flightId);

        // Construct response
        return constructMessage(id, ret.flightId, ret.departureTime, ret.availability, ret.airfare, ret.destination);
    }

    public static byte[] constructMessage(int id, int flightId, int departure_time, int availability, float airfare, String destination) throws UnsupportedEncodingException {
        List message = new ArrayList();

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHT_DETAILS);
        Utils.appendMessage(message, flightId);
        Utils.appendMessage(message, departure_time);
        Utils.appendMessage(message, availability);
        Utils.appendMessage(message, airfare);
        Utils.appendMessage(message, destination);

        return Utils.byteUnboxing(message);
    }
}
