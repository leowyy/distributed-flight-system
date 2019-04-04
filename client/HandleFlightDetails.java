package client;
import common.Constants;
import common.Utils;
import common.schema.FlightDetailsReply;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by signapoop on 1/4/19.
 */
class HandleFlightDetails {

    public static byte[] constructMessage(Scanner scanner, int id) throws UnsupportedEncodingException {
        List message = new ArrayList();
        System.out.println(Constants.SEPARATOR);
        System.out.println(Constants.ENTER_FLIGHT_ID_MSG);

        String input = scanner.nextLine();
        int flightId = Integer.parseInt(input);

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHT_DETAILS);
        Utils.appendMessage(message, flightId);

        return Utils.byteUnboxing(message);
    }

    public static void handleResponse(byte[] response) {
        FlightDetailsReply flightDetailsReply = Utils.unmarshal(response);
        System.out.println(flightDetailsReply.generateOutputMessage());
    }
}
