package client;

import common.Constants;
import common.Utils;
import common.schema.FlightsByPriceReply;
import common.schema.FlightsByPriceRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandleFlightsByPrice {
    public static byte[] constructMessage(Scanner scanner, int id) throws UnsupportedEncodingException {
        System.out.println(Constants.SEPARATOR);
        System.out.println(Constants.ENTER_PRICE_MSG);
        String priceStr = scanner.nextLine();
        int price = Integer.parseInt(priceStr);

        FlightsByPriceRequest request = new FlightsByPriceRequest(id, Constants.SERVICE_GET_FLIGHTS_BY_PRICE, price);
        return Utils.marshal(request);
    }

    public static void handleResponse(byte[] response) {
        FlightsByPriceReply flightsByPriceReply = Utils.unmarshal(response, FlightsByPriceReply.class);
        System.out.println(flightsByPriceReply.generateOutputMessage());
    }
}
