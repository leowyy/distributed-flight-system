package common.schema;

import common.Constants;

import java.util.Arrays;

public class FlightsBySourceDestinationReply {
    private int id;
    private int serviceNum;
    private String source;
    private String destination;
    private int status;
    private int[] flights;

    public FlightsBySourceDestinationReply(int id, int serviceNum, String source, String destination, int status, int[] flights) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.source = source;
        this.destination = destination;
        this.status = status;
        this.flights = flights;
    }

    public int getId() {
        return id;
    }

    public int getServiceNum() {
        return serviceNum;
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
            return String.format(Constants.FLIGHTS_FOUND_BY_PRICE_MSG, this.price) + Arrays.toString(this.flights);
        }
        else if (this.status == Constants.FLIGHT_NOT_FOUND_STATUS) {
            return String.format(Constants.FLIGHTS_NOT_FOUND_BY_PRICE_MSG, this.price);
        }
        else {
            return "Something went wrong. Status was invalid.";
        }
    }
}
