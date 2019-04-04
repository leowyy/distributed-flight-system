package client;
import common.Constants;
import common.Utils;
import common.schema.FlightsBySourceDestinationReply;
import common.schema.FlightsBySourceDestinationRequest;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Created by signapoop on 1/4/19.
 */
class HandleFlightsBySourceDestination {
    public static byte[] constructMessage(Scanner scanner, int id) throws UnsupportedEncodingException {
        System.out.println(Constants.SEPARATOR);
        System.out.println(Constants.ENTER_SOURCE_MSG);
        String source = scanner.nextLine();
        System.out.println(Constants.ENTER_DESTINATION_MSG);
        String destination = scanner.nextLine();

        FlightsBySourceDestinationRequest request = new FlightsBySourceDestinationRequest(id, Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION,
                source, destination);
        return Utils.marshal(request);
    }

    public static void handleResponse(byte[] response) {
        FlightsBySourceDestinationReply reply = (FlightsBySourceDestinationReply) Utils.unmarshal(response, FlightsBySourceDestinationReply.class);
        System.out.println(reply.generateOutputMessage());
    }
}
