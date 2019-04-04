package client;

import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandleReserveSeats {
    public static byte[] constructMessage(Scanner scanner, int accountId, int id) throws UnsupportedEncodingException {
        List message = new ArrayList();
        System.out.println(Constants.SEPARATOR);

        System.out.println(Constants.ENTER_FLIGHT_ID_MSG);
        String input = scanner.nextLine();
        int flightId = Integer.parseInt(input);

        System.out.println(Constants.ENTER_NUM_RESERVE_MSG);
        String numReserveStr = scanner.nextLine();
        int numReserve = Integer.parseInt(numReserveStr);

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_RESERVE_SEATS);
//        Utils.appendMessage(message, accountId);
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

        int flightId = Utils.unmarshalMsgInteger(response, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

        int numReserve = Utils.unmarshalMsgInteger(response, ptr);

        if (status == Constants.SEATS_SUCCESSFULLY_RESERVED_STATUS) {
            System.out.printf(Constants.SEATS_SUCCESSFULLY_RESERVED_MSG, numReserve, flightId);
        }
        else if (status == Constants.FLIGHT_NOT_FOUND_STATUS) {
            System.out.printf(Constants.FAILED_FLIGHT_DETAILS, flightId);
        }
        else if (status == Constants.NO_AVAILABILITY_STATUS) {
            System.out.printf(Constants.FAILED_TO_RESERVE_SEATS_MSG, numReserve, flightId);
        }
        else if (status == Constants.NEGATIVE_RESERVATION_QUANTITY_STATUS) {
            System.out.printf(Constants.NEGATIVE_RESERVATION_QUANTITY_MSG, numReserve);
        }
        else if (status == Constants.NOT_ENOUGH_MONEY_STATUS) {
//            System.out.printf(Constants.NOT_ENOUGH_MONEY_MSG, currentBalance, seatsPrice);
        }

    }
}
