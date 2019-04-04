package server;

import common.Constants;
import common.Utils;
import common.schema.FlightDetailsReply;
import common.schema.FlightDetailsRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by signapoop on 1/4/19.
 */
class ServerFlightDetails {

    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws UnsupportedEncodingException {
        // Deconstruct message

        FlightDetailsRequest request = (FlightDetailsRequest) Utils.unmarshal(message, new FlightDetailsRequest());
        FlightDetail ret = flightManager.getFlightDetails(request.getFlightId());

        FlightDetailsReply reply;
        if (ret == null) {
            reply = new FlightDetailsReply(id, Constants.FLIGHT_NOT_FOUND_STATUS, request.getFlightId(), -1, -1, -1, "nil", "nil");
        }
        else {
            reply = new FlightDetailsReply(id, Constants.FLIGHT_FOUND_STATUS, ret.flightId, ret.departureTime, ret.availability, ret.airfare, ret.source, ret.destination);
        }
        return Utils.marshal(reply);
    }
//
//    public static byte[] constructMessage(int id, int flightId, int departure_time, int availability, float airfare, String source,
//                                          String destination) throws UnsupportedEncodingException {
//        // if ret == null
//
//        List message = new ArrayList();
//
//        Utils.append(message, id);
//        Utils.append(message, Constants.SERVICE_GET_FLIGHT_DETAILS);
//        Utils.append(message, Constants.FLIGHT_FOUND_STATUS);
//        Utils.appendMessage(message, flightId);
//        Utils.appendMessage(message, departure_time);
//        Utils.appendMessage(message, availability);
//        Utils.appendMessage(message, airfare);
//        Utils.appendMessage(message, source);
//        Utils.appendMessage(message, destination);
//
//        return Utils.byteUnboxing(message);
//    }
//
//    public static byte[] constructFailMessage(int id, int flightId) throws UnsupportedEncodingException{
//        List message = new ArrayList();
//
//        Utils.append(message, id);
//        Utils.append(message, Constants.SERVICE_GET_FLIGHT_DETAILS);
//        Utils.append(message, Constants.FLIGHT_NOT_FOUND_STATUS);
//        Utils.appendMessage(message, flightId);
//
//        return Utils.byteUnboxing(message);
//    }
}
