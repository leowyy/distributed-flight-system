package server;

import common.Constants;
import common.Utils;
import common.schema.ReserveSeatsReply;
import common.schema.ReserveSeatsRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ServerReserveSeats {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws IOException, InterruptedException {
        // Deconstruct message
        ReserveSeatsRequest request = Utils.unmarshal(message, ReserveSeatsRequest.class);
        int accountId = request.getAccountId();
        int flightId = request.getFlightId();
        int numReserve = request.getNumReserve();
        float price = flightManager.getFlightDetails(flightId).airfare * numReserve;
        int status = flightManager.reserveSeatsForFlight(accountId, flightId, numReserve);
        float newBalance = flightManager.getBalance(accountId);
        // Construct response
        ReserveSeatsReply reply = new ReserveSeatsReply(id, status, flightId, numReserve, newBalance, price);
        return Utils.marshal(reply);
    }

//    public static byte[] constructMessage(int id, int flightId, int numReserve, int status) throws UnsupportedEncodingException {
//        List message = new ArrayList();
//        Utils.append(message, id);
//        Utils.append(message, Constants.SERVICE_RESERVE_SEATS);
//        Utils.append(message, status);
//        Utils.appendMessage(message, flightId);
//        Utils.appendMessage(message, numReserve);
//
//        return Utils.byteUnboxing(message);
//    }
}
