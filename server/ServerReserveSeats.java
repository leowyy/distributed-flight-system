package server;

import common.Constants;
import common.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ServerReserveSeats {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws IOException, InterruptedException {
        // Deconstruct message
        int ptr = 0;

        int flightId = Utils.unmarshalMsgInteger(message, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;
        int numReserve = Utils.unmarshalMsgInteger(message, ptr);

        Boolean success = flightManager.reserveSeatsForFlight(flightId, numReserve);

        // Construct response
        return constructMessage(id, numReserve, success);
    }

    public static byte[] constructMessage(int id, int numReserve, Boolean success) throws UnsupportedEncodingException {
        List message = new ArrayList();
        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_RESERVE_SEATS);

        if (success) {
            Utils.append(message, 1);
        }
        else {
            Utils.append(message, 0);
        }

        Utils.appendMessage(message, numReserve);

        return Utils.byteUnboxing(message);
    }
}
