package client;
import common.Constants;
import common.Utils;

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
        int ptr = 0;

        int serviceNum = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int status = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int flightId = Utils.unmarshalMsgInteger(response, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

        if (status == Constants.FAIL_STATUS) {
            System.out.printf(Constants.FAILED_FLIGHT_DETAILS, flightId);
            return;
        }

        int departureTime = Utils.unmarshalMsgInteger(response, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

        int availability = Utils.unmarshalMsgInteger(response, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

        float airfare = Utils.unmarshalMsgFloat(response, ptr);
        ptr += Constants.INT_SIZE + Constants.FLOAT_SIZE;

        String destination = Utils.unmarshalMsgString(response, ptr);

        System.out.printf(Constants.SUCCESSFUL_FLIGHT_DETAILS, flightId, departureTime, availability, airfare, destination);
    }
}
