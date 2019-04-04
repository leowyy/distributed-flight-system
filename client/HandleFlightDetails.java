package client;

import common.Constants;
import common.Utils;
import common.schema.FlightDetailsReply;
import common.schema.FlightDetailsRequest;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Created by signapoop on 1/4/19.
 */
class HandleFlightDetails {
    public static byte[] constructMessage(Scanner scanner, int id) throws UnsupportedEncodingException {
        System.out.println(Constants.SEPARATOR);
        System.out.println(Constants.ENTER_FLIGHT_ID_MSG);
        String input = scanner.nextLine();
        int flightId = Integer.parseInt(input);

        FlightDetailsRequest request = new FlightDetailsRequest(id, Constants.SERVICE_GET_FLIGHT_DETAILS, flightId);

        return Utils.marshal(request);
    }

    public static void handleResponse(byte[] response) {
        FlightDetailsReply flightDetailsReply = (FlightDetailsReply) Utils.unmarshal(response, new FlightDetailsReply());
        System.out.println(flightDetailsReply.generateOutputMessage());
    }
}
