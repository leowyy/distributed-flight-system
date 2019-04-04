package server;

import common.Constants;
import common.Utils;
import common.schema.FlightsByPriceReply;
import common.schema.FlightsByPriceRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ServerFlightsByPrice {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws UnsupportedEncodingException {
        // Deconstruct message
        FlightsByPriceRequest request = (FlightsByPriceRequest) Utils.unmarshal(message, new FlightsByPriceRequest());
        float price = request.getPrice();
        ArrayList<Integer> flightIds = flightManager.searchFlightsBelowPrice(price);

        FlightsByPriceReply reply;
        if (flightIds.size() > 0) {
            int[] intArray = flightIds.stream().mapToInt(Integer::intValue).toArray();
            reply = new FlightsByPriceReply(id, Constants.FLIGHT_FOUND_STATUS, price, intArray);
        }
        else {
            int[] arrayPlaceholder = {0,0};
            reply = new FlightsByPriceReply(id, Constants.FLIGHT_NOT_FOUND_STATUS, price, arrayPlaceholder);
        }
        return Utils.marshal(reply);
    }
}
