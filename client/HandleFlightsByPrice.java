package client;

import common.Constants;
import common.Utils;
import common.schema.FlightsByPriceReply;

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
        FlightsByPriceReply objPlaceholder = new FlightsByPriceReply();
        FlightsByPriceReply flightsByPriceReply = Utils.unmarshal(response, objPlaceholder);
    }
}
