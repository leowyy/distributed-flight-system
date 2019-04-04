package common.schema;

import common.Constants;

import java.util.Arrays;

public class FlightsBySourceDestinationReply {
    public int id;
    public String source;
    public String destination;
    public int status;
    public int[] flights;

    public FlightsBySourceDestinationReply() {
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFlights(int[] flights) {
        this.flights = flights;
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
