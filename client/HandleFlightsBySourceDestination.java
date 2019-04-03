package client;
import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by signapoop on 1/4/19.
 */
class HandleFlightsBySourceDestination {

    public static byte[] constructMessage(Scanner scanner, int id) throws UnsupportedEncodingException {
        List message = new ArrayList();
        System.out.println(Constants.SEPARATOR);

        System.out.println(Constants.ENTER_SOURCE_MSG);
        String source = scanner.nextLine();
        System.out.println(Constants.ENTER_DESTINATION_MSG);
        String destination = scanner.nextLine();

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION);
        Utils.appendMessage(message, source);
        Utils.appendMessage(message, destination);

        return Utils.byteUnboxing(message);
    }

    public static void handleResponse(byte[] response) {
        int ptr = 0;

        int serviceNum = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int status = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        if (status == Constants.SUCCESS_STATUS) {
            int[] flightIds = Utils.unmarshalMsgIntArray(response, ptr);
            System.out.println(Constants.FLIGHTS_FOUND_MSG);
            System.out.println(Arrays.toString(flightIds));
        }
        else if (status == Constants.FAIL_STATUS) {
            System.out.println(Constants.NO_FLIGHTS_FOUND_MSG);
        }
    }
}
