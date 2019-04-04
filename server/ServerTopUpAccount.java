package server;

import common.Constants;
import common.Utils;
import common.schema.TopUpAccountReply;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ServerTopUpAccount {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws IOException, InterruptedException {
        // Deconstruct message

        accountId = ;
        topUpAmount =

        int status = flightManager.topUpAccount(accountId, topUpAmount);

        // Construct response
        return constructMessage(id, flightId, numReserve, status);
    }

    public static byte[] constructMessage(int id, int flightId, int numReserve, int status) throws UnsupportedEncodingException {
        TopUpAccountReply topUpAccountReply = new TopUpAccountReply(id, Constants.SERVICE_TOP_UP_ACCOUNT, accountId, topUpAmount, newBalance);

        return Utils.byteUnboxing(topUpAccountReply);
    }
}
