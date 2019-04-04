package common;

/**
 * Created by signapoop on 1/4/19.
 */
public class Constants {
    // Argument Constant
    public static final String DEFAULT_HOST = "10.27.249.51";
    public static final int DEFAULT_SERVER_PORT = 8888;

    // Invocation Semantics
    public class InvoSem {
        public static final int NONE = 0;
        public static final int AT_LEAST_ONCE = 1;
        public static final int AT_MOST_ONCE = 2;
        public static final int DEFAULT = AT_LEAST_ONCE;
    }

    // Timeout Constants
    public class Timeout {
        public static final int DEFAULT_MAX_TIME = 1000;
        public static final int DEFAULT_NO_TIME = 0;
        public static final int DEFAULT_MAX_TRIES = 0;
    }

    public static final double DEFAULT_SERVER_FAILURE_PROB = 0.1;
    public static final double DEFAULT_CLIENT_FAILURE_PROB = 0.1;



    // Type Constant
    public static final int INT_SIZE = 4;
    public static final int FLOAT_SIZE = 4;

    // Service constant
    public static final int SERVICE_GET_FLIGHT_BY_SOURCE_DESTINATION = 1;
    public static final int SERVICE_GET_FLIGHT_DETAILS = 2;
    public static final int SERVICE_RESERVE_SEATS = 3;
    public static final int SERVICE_MONITOR_AVAILABILITY = 4;
    public static final int SERVICE_GET_FLIGHTS_BY_PRICE = 5;
    public static final int SERVICE_TOP_UP_ACCOUNT = 6;
    public static final int SERVICE_EXIT = -1;

    // Main UI Constant
    public static final String WELCOME_MSG = "Welcome to NTU Airlines!";
    public static final String REQUEST_ACC_ID_MSG = "Enter Account ID: ";

    public static final String GET_FLIGHT_BY_SOURCE_DESTINATION_SVC_MSG = "1. Get flight by source and destination.";
    public static final String GET_FLIGHT_DETAILS_SVC_MSG = "2. Get flight details.";
    public static final String RESERVE_SEATS_SVC_MSG = "3. Reserve seats for a flight.";
    public static final String MONITOR_FLIGHT_AVAILABILITY_SVC_MSG = "4. Monitor availability for a flight.";
    public static final String FIND_FLIGHT_BY_PRICE_SVC_MSG = "5. Find flights below a certain price.";
    public static final String TOP_UP_ACCOUNT_MSG = "6. Top up your account.";
    public static final String EXIT_SVC_MSG = "-1. Exit.";

    public static final String ERR_MSG = "Error: %s\n";
    public static final String SUCCESS_MSG = "SUCCESS!";
    public static final String SEPARATOR = "================================================================================\n";
    public static final String EXIT_MSG = "Thank you for using our flight system!";
    public static final String UNRECOGNIZE_SVC_MSG = "Sorry we cannot recognize your service choice!";

    // Status constants
    public static final int FLIGHT_FOUND_STATUS = 1;
    public static final int FLIGHT_NOT_FOUND_STATUS = 0;
    public static final int MONITORING_NEW_UPDATE_STATUS = 2;
    public static final int SEATS_SUCCESSFULLY_RESERVED_STATUS = 3;
    public static final int NO_AVAILABILITY_STATUS = 4;
    public static final int NEGATIVE_RESERVATION_QUANTITY_STATUS = 5;
    public static final int NOT_ENOUGH_MONEY_STATUS = 6;

    // Get Flight Details Constant
    public static final String ENTER_FLIGHT_ID_MSG = "Enter flight ID: ";

    // Server Debug Constant
    public static final String PRINT_CLIENT_MESSAGE = "Response ID: %d\nService Type: %d\nClient address: %s\nClient port: %d\nMessage length: %d\n\n";

    // Get Flight Details Constant
    public static final String SUCCESSFUL_FLIGHT_DETAILS = "Flight Details.\nDeparture time: %d\nAirfare: %f\nAvailability: %d\n";
    public static final String FAILED_FLIGHT_DETAILS = "Flight details not found for flight ID %d\n";

    // Get Flight by Source Destination
    public static final String ENTER_SOURCE_MSG = "Enter flight source: ";
    public static final String ENTER_DESTINATION_MSG = "Enter flight destination: ";
    public static final String NO_FLIGHTS_FOUND_MSG = "No flights found for this source and destination pair.";
    public static final String FLIGHTS_FOUND_MSG = "These are the IDs of the flights found: ";

    // Reserve seats
    public static final String ENTER_NUM_RESERVE_MSG = "Enter the number of seats you want to reserve: ";
    public static final String SEATS_SUCCESSFULLY_RESERVED_MSG = "Your %d seats have been reserved for flight ID %d.\n";
    public static final String FAILED_TO_RESERVE_SEATS_MSG = "Could not reserve your %d seats for flight ID %d, no availability.\n";
    public static final String NEGATIVE_RESERVATION_QUANTITY_MSG = "You tried to reserve %d seats. Negative numbers are not allowed.\n";
    public static final String NOT_ENOUGH_MONEY_MSG = "You do not have enough money in your account.\nCurrent balance: %f\nPrice of seats:%f\n";

    // Monitor availability
    public static final String ENTER_MONITOR_INTERVAL_MSG = "Enter monitor interval (in seconds): ";
    public static final String MONITORING_STARTED_MSG = "Now monitoring flight ID %d...\n";
    public static final String MONITORING_UPDATE_MSG = "There are now %d seats available for flight ID %d.\n";
    public static final String MONITORING_END_MSG = "Monitoring has ended.\n";

    // Get Flight by price
    public static final String ENTER_PRICE_MSG = "Enter desired maximum price: ";
    public static final String FLIGHTS_FOUND_BY_PRICE_MSG = "These are the IDs of the flights found that are cheaper than %f dollars:\n";
    public static final String FLIGHTS_NOT_FOUND_BY_PRICE_MSG = "No flights found that are cheaper than %f dollars.\n";

    // Top up account
    public static final String ACCOUNT_TOPPED_UP_MSG = "Account balance has been topped up by %f to %f.\n";
}
