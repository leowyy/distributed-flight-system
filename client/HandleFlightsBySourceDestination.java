package client;
import common.Constants;
import common.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by signapoop on 1/4/19.
 */
class HandleFlightsBySourceDestination {

    public static byte[] constructMessage(int id, int[] flightIds) throws UnsupportedEncodingException {
        List message = new ArrayList();

        Utils.append(message, id);
        Utils.append(message, Constants.SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION);
        Utils.appendMessage(message, flightIds);

        return Utils.byteUnboxing(message);
    }

    public static void handleResponse(byte[] response) {
        int ptr = 0;
        int id = Utils.unmarshalInteger(response, ptr);

        ptr += Constants.INT_SIZE;
        int serviceNum = Utils.unmarshalInteger(response, ptr);

        ptr += Constants.INT_SIZE;
        int[] flightIds = Utils.unmarshalMsgIntArray(response, ptr);

        System.out.println(id);
        System.out.println(serviceNum);
        System.out.println(Arrays.toString(flightIds));
    }
}
