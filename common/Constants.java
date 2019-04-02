package common;

/**
 * Created by signapoop on 1/4/19.
 */
public class Constants {
    // Argument Constant
    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 8080;

    // Type Constant
    public static final int INT_SIZE = 4;
    public static final int FLOAT_SIZE = 4;

    // Service constant
    public static final int SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION = 1;
    public static final int SERVICE_GET_FLIGHT_DETAILS = 2;
    public static final int SERVICE_EXIT = -1;

    // Main UI Constant
    public static final String GET_FLIGHT_BY_SOURCE_DESTINATION_SVC_MSG = "1. Get flight by source and destination.";
    public static final String GET_FLIGHT_DETAILS_SVC_MSG = "2. Get flight details.";
    public static final String EXIT_SVC_MSG = "-1. Exit.";

    public static final String ERR_MSG = "Error: %s\n";
    public static final String SUCCESS_MSG = "SUCCESS!";
    public static final String SEPARATOR = "================================================================================\n";
    public static final String EXIT_MSG = "Thank you for using our flight system!";
    public static final String UNRECOGNIZE_SVC_MSG = "Sorry we cannot recognize your service choice!";

    // Get Flight Details Constant
    public static final String ENTER_FLIGHT_ID_MSG = "Enter flight ID: ";

    // Server Debug Constant
    public static final String PRINT_CLIENT_MESSAGE = "Response ID: %d\nService Type: %d\nClient address: %s\nClient port: %d\nMessage length: %d\n\n";


    // Get Flight Details Constant
    public static final String SUCCESSFUL_FLIGHT_DETAILS = "Flight Details.\nFlight ID: %d\nDeparture time: %d\nAvailability: %d\nAirfare: %f\nDestination: %s\n";
}
