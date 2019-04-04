package client;

import common.Constants;
import common.Utils;
import common.schema.MonitorAvailabilityReply;
import common.schema.MonitorAvailabilityRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandleMonitorAvailability {
    public static byte[] constructMessage(Scanner scanner, int id) throws UnsupportedEncodingException {
        System.out.println(Constants.SEPARATOR);
        System.out.println(Constants.ENTER_FLIGHT_ID_MSG);
        String input = scanner.nextLine();
        int flightId = Integer.parseInt(input);
        System.out.println(Constants.ENTER_MONITOR_INTERVAL_MSG);
        String monitorInterval = scanner.nextLine();
        int duration = Integer.parseInt(monitorInterval);

        MonitorAvailabilityRequest request = new MonitorAvailabilityRequest(id, Constants.SERVICE_MONITOR_AVAILABILITY, flightId, duration);
        return Utils.marshal(request);
    }

    // response is any updates sent by the callback
    public static int handleResponse(byte[] response) {
        MonitorAvailabilityReply reply = Utils.unmarshal(response, MonitorAvailabilityReply.class);
        System.out.println(reply.generateOutputMessage());
        return reply.getDuration(); // may need to only return this when status is 'monitoring started'
    }
}
