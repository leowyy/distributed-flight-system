package common.schema;

import common.Constants;

import java.util.Arrays;

public class FlightsBySourceDestinationReply {
    private int id;
    private String source;
    private String destination;
    private int status;
    private int[] flights;

    public FlightsBySourceDestinationReply(int id, int status, String source, String destination, int[] flights) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.status = status;
        this.flights = flights;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getStatus() {
        return status;
    }

    public int[] getFlights() {
        return flights;
    }

    public String generateOutputMessage() {
        if (this.status == Constants.FLIGHT_FOUND_STATUS) {
            return String.format(Constants.FLIGHTS_FOUND_MSG) + Arrays.toString(this.flights);
        }
        else if (this.status == Constants.FLIGHT_NOT_FOUND_STATUS) {
            return String.format(Constants.NO_FLIGHTS_FOUND_MSG);
        }
        else {
            return "Something went wrong. Status was invalid.";
        }
    }
}
