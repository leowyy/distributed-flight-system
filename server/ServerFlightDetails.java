package server;

import common.Constants;
import common.Utils;

/**
 * Created by signapoop on 1/4/19.
 */
class ServerFlightDetails {

    public static void handleResponse(byte[] response) {
        int ptr = 0;
        int serviceNum = Utils.unmarshalInteger(response, ptr);

        ptr += Constants.INT_SIZE;
        int flightId = Utils.unmarshalMsgInteger(response, ptr);

        ptr += Constants.INT_SIZE + Constants.INT_SIZE;
        int departure_time = Utils.unmarshalMsgInteger(response, ptr);

        ptr += Constants.INT_SIZE + Constants.INT_SIZE;
        int availability = Utils.unmarshalMsgInteger(response, ptr);

        ptr += Constants.INT_SIZE + Constants.INT_SIZE;
        float airfare = Utils.unmarshalMsgFloat(response, ptr);

        ptr += Constants.INT_SIZE + Constants.FLOAT_SIZE;
        String destination = Utils.unmarshalMsgString(response, ptr);

        System.out.printf(Constants.SUCCESSFUL_FLIGHT_DETAILS, flightId, departure_time, availability, airfare, destination);
    }
}
