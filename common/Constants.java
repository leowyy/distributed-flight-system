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
    public static final int SERVICE_RESERVE_SEATS = 3;
    public static final int SERVICE_MONITOR_AVAILABILITY = 4;
    public static final int SERVICE_EXIT = -1;

    // Main UI Constant
    public static final String GET_FLIGHT_BY_SOURCE_DESTINATION_SVC_MSG = "1. Get flight by source and destination.";
    public static final String GET_FLIGHT_DETAILS_SVC_MSG = "2. Get flight details.";
    public static final String RESERVE_SEATS_SVC_MSG = "3. Reserve seats for a flight.";
    public static final String MONITOR_FLIGHT_AVAILABILITY_SVC_MSG = "4. Monitor availability for a flight.";
    public static final String IDEMPOTENT_SERVICE = "5. Get flight details.";
    public static final String NON_IDEMPOTENT_SERVICE = "6. Get flight details.";
    public static final String EXIT_SVC_MSG = "-1. Exit.";

    public static final String ERR_MSG = "Error: %s\n";
    public static final String SUCCESS_MSG = "SUCCESS!";
    public static final String SEPARATOR = "================================================================================\n";
    public static final String EXIT_MSG = "Thank you for using our flight system!";
    public static final String UNRECOGNIZE_SVC_MSG = "Sorry we cannot recognize your service choice!";

    // Status constants
    public static final int SUCCESS_STATUS = 1;
    public static final int FAIL_STATUS = 0;
    public static final int MONITORING_NEW_UPDATE_STATUS = 2;

    // Get Flight Details Constant
    public static final String ENTER_FLIGHT_ID_MSG = "Enter flight ID: ";

    // Server Debug Constant
    public static final String PRINT_CLIENT_MESSAGE = "Response ID: %d\nService Type: %d\nClient address: %s\nClient port: %d\nMessage length: %d\n\n";

    // Get Flight Details Constant
    public static final String SUCCESSFUL_FLIGHT_DETAILS = "Flight Details.\nFlight ID: %d\nDeparture time: %d\nAvailability: %d\nAirfare: %f\nDestination: %s\n";

    // Get Flight by Source Destination
    public static final String ENTER_SOURCE_MSG = "Enter flight source: ";
    public static final String ENTER_DESTINATION_MSG = "Enter flight destination: ";
    public static final String NO_FLIGHTS_FOUND_MSG = "No flights found for this source and destination pair.";
    public static final String FLIGHTS_FOUND_MSG = "These are the flights found: ";

    // Reserve seats
    public static final String ENTER_NUM_RESERVE_MSG = "Enter the number of seats you want to reserve: ";
    public static final String SEATS_SUCCESSFULLY_RESERVED_MSG = "Your %d seats have been reserved.\n";
    public static final String FAILED_TO_RESERVE_SEATS_MSG = "Could not reserve your %d seats, no availability.\n";

    // Monitor availability
    public static final String ENTER_MONITOR_INTERVAL_MSG = "Enter monitor interval (in seconds): ";
    public static final String MONITORING_STARTED_MSG = "Now monitoring flight ID %d...\n";
    public static final String MONITORING_UPDATE_MSG = "There are now %d seats available for flight ID %d.\n";
}
