package client;

import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandleReserveSeats {
    public static byte[] constructMessage(Scanner scanner, int id) throws UnsupportedEncodingException {
        List message = new ArrayList();
        System.out.println(Constants.SEPARATOR);

        System.out.println(Constants.ENTER_FLIGHT_ID_MSG);
        String input = scanner.nextLine();
        int flightId = Integer.parseInt(input);

        System.out.println(Constants.ENTER_NUM_RESERVE_MSG);
        String numReserveStr = scanner.nextLine();
        int numReserve = Integer.parseInt(numReserveStr);

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHT_DETAILS);
        Utils.appendMessage(message, flightId);
        Utils.appendMessage(message, numReserve);

        return Utils.byteUnboxing(message);
    }

    public static void handleResponse(byte[] response) {
        int ptr = 0;

        int serviceNum = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int status = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int numReserve = Utils.unmarshalMsgInteger(response, ptr);

        if (status == Constants.SUCCESS_STATUS) {
            System.out.printf(Constants.SEATS_SUCCESSFULLY_RESERVED_MSG, numReserve);
        }
        else if (status == Constants.FAIL_STATUS) {
            System.out.printf(Constants.FAILED_TO_RESERVE_SEATS_MSG, numReserve);
        }

    }
}