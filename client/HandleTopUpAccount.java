package client;

import common.Constants;
import common.Utils;
import common.schema.TopUpAccountReply;
import common.schema.TopUpAccountRequest;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class HandleTopUpAccount {
    public static byte[] constructMessage(Scanner scanner, int accountId, int id) throws UnsupportedEncodingException {
        System.out.println(Constants.SEPARATOR);
        System.out.println(Constants.TOP_UP_AMOUNT_MSG);
        String topUpAmountStr = scanner.nextLine();
        float topUpAmount = Float.parseFloat(topUpAmountStr);

        TopUpAccountRequest request = new TopUpAccountRequest(id, Constants.SERVICE_TOP_UP_ACCOUNT, accountId, topUpAmount);
        return Utils.marshal(request);
    }

    public static void handleResponse(byte[] response) {
        TopUpAccountReply reply = (TopUpAccountReply) Utils.unmarshal(response, new TopUpAccountReply());
        System.out.println(reply.generateOutputMessage());
    }
}
