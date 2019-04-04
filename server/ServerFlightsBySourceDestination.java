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
        }
        else {
            int[] arrayPlaceholder = {0, 0};
            reply = new FlightsBySourceDestinationReply(id, Constants.FLIGHT_NOT_FOUND_STATUS, source, destination, arrayPlaceholder);
        }
        return Utils.marshal(reply);
    }

//    public static byte[] constructMessage(int id, ArrayList<Integer> flightIds) throws UnsupportedEncodingException {
//        List message = new ArrayList();
//        Utils.append(message, id);
//        Utils.append(message, Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION);
//
//        // convert ArrayList into array of primitive integers
//        int[] intArray = flightIds.stream().mapToInt(Integer::intValue).toArray();
//
//        if (flightIds.size() > 0) {
//            Utils.append(message, Constants.FLIGHT_FOUND_STATUS);
//            Utils.appendMessage(message, intArray);
//        }
//        else { // send 0 failure status
//            Utils.append(message, Constants.FLIGHT_NOT_FOUND_STATUS);
//        }
//
//        return Utils.byteUnboxing(message);
//    }
}
