package client;

import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandleMonitorAvailability {
    public static byte[] constructMessage(Scanner scanner, int id) throws UnsupportedEncodingException {
        List message = new ArrayList();
        System.out.println(Constants.SEPARATOR);

        System.out.println(Constants.ENTER_FLIGHT_ID_MSG);
        String input = scanner.nextLine();
        int flightId = Integer.parseInt(input);

        System.out.println(Constants.ENTER_MONITOR_INTERVAL_MSG);
        String monitorInterval = scanner.nextLine();
        int duration = Integer.parseInt(monitorInterval);

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_MONITOR_AVAILABILITY);
        Utils.appendMessage(message, flightId);
        Utils.appendMessage(message, duration);

        return Utils.byteUnboxing(message);
    }

    // response is any updates sent by the callback
    public static void handleResponse(byte[] response) {
        int ptr = 0;

        int serviceNum = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int status = Utils.unmarshalInteger(response, ptr);
        ptr += Constants.INT_SIZE;

        int flightId = Utils.unmarshalMsgInteger(response, ptr);
        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

        if (status == Constants.SUCCESS_STATUS) {
            System.out.printf(Constants.MONITORING_STARTED_MSG, flightId);
        }
        else if (status == Constants.MONITORING_NEW_UPDATE_STATUS) {
            int availability = Utils.unmarshalMsgInteger(response, ptr);
            System.out.printf(Constants.MONITORING_UPDATE_MSG, availability, flightId);
        }
    }
}
