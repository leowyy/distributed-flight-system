package server;

import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ServerFlightsByPrice {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws UnsupportedEncodingException {
        // Deconstruct message
        int ptr = 0;

        int price = Utils.unmarshalMsgInteger(message, ptr);

        ArrayList<Integer> ret = flightManager.searchFlightsBelowPrice(price);

        // Construct response
        return constructMessage(id, price, ret);
    }

    public static byte[] constructMessage(int id, int price, ArrayList<Integer> flightIds) throws UnsupportedEncodingException {
        List message = new ArrayList();
        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHTS_BY_PRICE);

        // convert ArrayList into array of primitive integers
        int[] intArray = flightIds.stream().mapToInt(Integer::intValue).toArray();

        if (flightIds.size() > 0) {
            Utils.append(message, Constants.FLIGHT_FOUND_STATUS);
            Utils.appendMessage(message, price);
            Utils.appendMessage(message, intArray);
        }
        else { // send 0 failure status
            Utils.append(message, Constants.FLIGHT_NOT_FOUND_STATUS);
            Utils.appendMessage(message, price);
        }

        return Utils.byteUnboxing(message);
    }
}
