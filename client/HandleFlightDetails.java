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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int flightId = Integer.parseInt(input);
        byte[] b = Utils.marshal(flightId);
        System.out.println(flightId);
        int x = Utils.unmarshalInteger(b, 0);
        System.out.println(x);
    }

    public static byte[] constructMessage(int id, int flightId, int departure_time, int availability, float airfare, String destination) throws UnsupportedEncodingException {
        List message = new ArrayList();

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHT_DETAILS);
        Utils.appendMessage(message, flightId);
        Utils.appendMessage(message, departure_time);
        Utils.appendMessage(message, availability);
        Utils.appendMessage(message, airfare);
        Utils.appendMessage(message, destination);

        return Utils.byteUnboxing(message);
    }

    public static void handleResponse(byte[] response) {
        int ptr = 0;
        int id = Utils.unmarshalInteger(response, ptr);

        ptr += Constants.INT_SIZE;
        int serviceNum = Utils.unmarshalInteger(response, ptr);

        ptr += Constants.INT_SIZE;
        int flightId = Utils.unmarshalMsgInteger(response, ptr);

        ptr += Constants.INT_SIZE + Constants.INT_SIZE;
        int departure_time = Utils.unmarshalMsgInteger(response, ptr);

        ptr += Constants.INT_SIZE + Constants.INT_SIZE;
        int availability = Utils.unmarshalMsgInteger(response, ptr);

        ptr += Constants.INT_SIZE + Constants.INT_SIZE;
        float airfare = Utils.unmarshalMsgFloat(response, ptr);

        ptr += Constants.INT_SIZE + Constants.FLOAT_SIZE;
        String destination = Utils.unmarshalMsgString(response, ptr);

        System.out.println(id);
        System.out.println(serviceNum);
        System.out.println(flightId);
        System.out.println(departure_time);
        System.out.println(availability);
        System.out.println(airfare);
        System.out.println(destination);
    }
}
