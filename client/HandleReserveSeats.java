package client;

import common.Constants;
import common.Utils;
import common.schema.ReserveSeatsReply;
import common.schema.ReserveSeatsRequest;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class HandleReserveSeats {
    public static byte[] constructMessage(Scanner scanner, int accountId, int id) throws UnsupportedEncodingException {
        System.out.println(Constants.SEPARATOR);
        System.out.println(Constants.ENTER_FLIGHT_ID_MSG);
        String input = scanner.nextLine();
        int flightId = Integer.parseInt(input);
        System.out.println(Constants.ENTER_NUM_RESERVE_MSG);
        String numReserveStr = scanner.nextLine();
        int numReserve = Integer.parseInt(numReserveStr);

        ReserveSeatsRequest request = new ReserveSeatsRequest(id, Constants.SERVICE_RESERVE_SEATS, accountId, flightId, numReserve);
        return Utils.marshal(request);
    }

    public static void handleResponse(byte[] response) {
        ReserveSeatsReply reply = (ReserveSeatsReply) Utils.unmarshal(response, new ReserveSeatsReply());
        System.out.println(reply.generateOutputString());

    }
}
