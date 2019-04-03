package server;

import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ServerFlightsBySourceDestination {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws UnsupportedEncodingException {
        // Deconstruct message
        int ptr = 0;

        int sourceLength = Utils.unmarshalInteger(message, ptr);
        ptr += Constants.INT_SIZE;

        String source = Utils.unmarshalString(message, ptr, ptr+sourceLength);
        ptr += sourceLength;

        int destinationLength = Utils.unmarshalInteger(message, ptr);
        ptr += Constants.INT_SIZE;

        String destination = Utils.unmarshalString(message, ptr, ptr+destinationLength);

        ArrayList<Integer> ret = flightManager.getFlightsBySourceDestination(source, destination);

        // Construct response
        return constructMessage(id, ret);
    }

    public static byte[] constructMessage(int id, ArrayList<Integer> flightIds) throws UnsupportedEncodingException {
        List message = new ArrayList();
        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION);

        // convert ArrayList into array of primitive integers
        int[] intArray = flightIds.stream().mapToInt(Integer::intValue).toArray();

        if (flightIds.size() > 0) {
            Utils.append(message, Constants.FLIGHT_FOUND_STATUS);
            Utils.appendMessage(message, intArray);
        }
        else { // send 0 failure status
            Utils.append(message, Constants.FLIGHT_NOT_FOUND_STATUS);
        }

        return Utils.byteUnboxing(message);
    }
}
