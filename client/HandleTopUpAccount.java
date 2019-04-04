package client;

import common.Constants;
import common.Utils;
import common.schema.TopUpAccountRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandleTopUpAccount {
    public static byte[] constructMessage(Scanner scanner, int accountId, int id) throws UnsupportedEncodingException {
        List message = new ArrayList();
        System.out.println(Constants.SEPARATOR);
        System.out.println(Constants.ENTER_FLIGHT_ID_MSG);

        String topUpAmountStr = scanner.nextLine();
        float topUpAmount = Float.parseFloat(topUpAmountStr);

        TopUpAccountRequest topUpAccountRequest = new TopUpAccountRequest(id, Constants.SERVICE_TOP_UP_ACCOUNT, accountId, topUpAmount);

//        return Utils.byteUnboxing(message);
        return Utils.byteUnboxing(topUpAccountRequest);
    }

    public static void handleResponse(byte[] response) {
        int ptr = 0;

        int serviceNum = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int status = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int flightId = Utils.unmarshalMsgInteger(response, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

        if (status == Constants.FLIGHT_NOT_FOUND_STATUS) {
            System.out.printf(Constants.FAILED_FLIGHT_DETAILS, flightId);
            return;
        }

        int departureTime = Utils.unmarshalMsgInteger(response, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

        int availability = Utils.unmarshalMsgInteger(response, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

        float airfare = Utils.unmarshalMsgFloat(response, ptr);
        ptr += Constants.INT_SIZE + Constants.FLOAT_SIZE;

        int sourceLength = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        String source = Utils.unmarshalString(response, ptr, ptr+sourceLength);
        ptr += sourceLength;
        String destination = Utils.unmarshalMsgString(response, ptr);

        System.out.printf(Constants.SUCCESSFUL_FLIGHT_DETAILS, flightId, departureTime, availability, airfare, source, destination);
    }
}
