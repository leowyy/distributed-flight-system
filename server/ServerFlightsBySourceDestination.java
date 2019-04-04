package server;

import common.Constants;
import common.Utils;
import common.schema.FlightsBySourceDestinationReply;
import common.schema.FlightsBySourceDestinationRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ServerFlightsBySourceDestination {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws UnsupportedEncodingException {
        // Deconstruct message
        FlightsBySourceDestinationRequest request = (FlightsBySourceDestinationRequest) Utils.unmarshal(message, new FlightsBySourceDestinationRequest());
        String source = request.getSource();
        String destination = request.getDestination();
        ArrayList<Integer> flightIds = flightManager.getFlightsBySourceDestination(source, destination);

        FlightsBySourceDestinationReply reply;
        if (flightIds.size() > 0) {
            int[] intArray = flightIds.stream().mapToInt(Integer::intValue).toArray();
            reply = new FlightsBySourceDestinationReply(id, Constants.FLIGHT_FOUND_STATUS, source, destination, intArray);
        } else {
            int[] arrayPlaceholder = {0, 0};
            reply = new FlightsBySourceDestinationReply(id, Constants.FLIGHT_NOT_FOUND_STATUS, source, destination, arrayPlaceholder);
        }
        return Utils.marshal(reply);
    }
}
