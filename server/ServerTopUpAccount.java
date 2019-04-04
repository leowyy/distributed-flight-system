package server;

import common.Constants;
import common.Utils;
import common.schema.TopUpAccountReply;
import common.schema.TopUpAccountRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ServerTopUpAccount {
    public static byte[] handleResponse(int id, byte[] message, FlightManager flightManager) throws IOException, InterruptedException {
        // Deconstruct message
        TopUpAccountRequest request = Utils.unmarshal(message, TopUpAccountRequest.class);
        int accountId = request.getAccountId();
        float topUpAmount = request.getTopUpAmount();

        flightManager.topUpAccount(accountId, topUpAmount);
        float newBalance = flightManager.getBalance(accountId);

        // Construct response
        TopUpAccountReply reply = new TopUpAccountReply(id, accountId, topUpAmount, newBalance);
        return Utils.marshal(reply);
    }

    public static byte[] constructMessage(int id, int flightId, int numReserve, int status) throws UnsupportedEncodingException {
        TopUpAccountReply topUpAccountReply = new TopUpAccountReply(id, Constants.SERVICE_TOP_UP_ACCOUNT, accountId, topUpAmount, newBalance);

        return Utils.byteUnboxing(topUpAccountReply);
    }
}
