package client;

import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HandleFlightsByPrice {
    public static byte[] constructMessage(Scanner scanner, int id) throws UnsupportedEncodingException {
        List message = new ArrayList();
        System.out.println(Constants.SEPARATOR);

        System.out.println(Constants.ENTER_PRICE_MSG);
        String priceStr = scanner.nextLine();
        int price = Integer.parseInt(priceStr);

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHTS_BY_PRICE);
        Utils.appendMessage(message, price);

        return Utils.byteUnboxing(message);
    }

    public static void handleResponse(byte[] response) {
        int ptr = 0;

        int serviceNum = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int status = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int price = Utils.unmarshalMsgInteger(response, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

        if (status == Constants.FLIGHT_FOUND_STATUS) {
            int[] flightIds = Utils.unmarshalMsgIntArray(response, ptr);
            System.out.printf(Constants.FLIGHTS_FOUND_BY_PRICE_MSG, price);
            System.out.println(Arrays.toString(flightIds));
        }
        else if (status == Constants.FLIGHT_NOT_FOUND_STATUS) {
            System.out.printf(Constants.FLIGHTS_NOT_FOUND_BY_PRICE_MSG, price);
        }
    }
}
