package server;

import common.Constants;
import common.Utils;

/**
 * Created by signapoop on 1/4/19.
 */
class ServerFlightDetails {

    public static void handleResponse(ClientMessage message, FlightManager flightManager) {
        byte[] clientMessageData = message.message;
        int ptr = Constants.INT_SIZE;
        int flightId = Utils.unmarshalMsgInteger(clientMessageData, ptr);

        FlightDetail foo = flightManager.getFlightDetails(flightId);
        foo.print();

        // Construct message from foo and reply
    }
}
