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

        if (ret == null) {
            return constructFailMessage(id, flightId);
        }
        else {
            return constructMessage(id, ret.flightId, ret.departureTime, ret.availability, ret.airfare, ret.source, ret.destination);
        }
    }

    public static byte[] constructMessage(int id, int flightId, int departure_time, int availability, float airfare, String source,
                                          String destination) throws UnsupportedEncodingException {
        // if ret == null

        List message = new ArrayList();

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHT_DETAILS);
        Utils.append(message, Constants.FLIGHT_FOUND_STATUS);
        Utils.appendMessage(message, flightId);
        Utils.appendMessage(message, departure_time);
        Utils.appendMessage(message, availability);
        Utils.appendMessage(message, airfare);
        Utils.appendMessage(message, source);
        Utils.appendMessage(message, destination);

        return Utils.byteUnboxing(message);
    }

    public static byte[] constructFailMessage(int id, int flightId) throws UnsupportedEncodingException{
        List message = new ArrayList();

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHT_DETAILS);
        Utils.append(message, Constants.FLIGHT_NOT_FOUND_STATUS);
        Utils.appendMessage(message, flightId);

        return Utils.byteUnboxing(message);
    }
}
