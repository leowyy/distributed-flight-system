package client;

import common.Constants;

/**
 * Created by signapoop on 1/4/19.
 */
public class Test {

    public static void main(String[] args)throws Exception {
        byte[] packageByte;

        try {
            float airfare = 0.7F;
            int[] flightIds = {0, 2, 3, 4};

            packageByte = HandleFlightDetails.constructMessage(0,1,2,3, airfare, "hello");
            System.out.println(packageByte);
            HandleFlightDetails.handleResponse(packageByte);

            System.out.println("Next");
            packageByte = HandleFlightsBySourceDestination.constructMessage(0, flightIds);
            System.out.println(packageByte);
            HandleFlightsBySourceDestination.handleResponse(packageByte);

        } catch (Exception e) {
            System.out.print(Constants.SEPARATOR);
            System.out.printf(Constants.ERR_MSG, e.getMessage());
        }
    }
}
