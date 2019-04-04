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
}
