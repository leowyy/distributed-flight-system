package common.schema;

import common.Constants;

public class FlightDetailsReply {
    private int id;
    private int serviceNum;
    private int status;
    private int flightId;
    private int departureTime;
    private int availability;
    private float airfare;
    private String source;
    private String destination;

    public FlightDetailsReply(int id, int serviceNum, int status, int flightId, int departureTime, int availability,
                              float airfare, String source, String destination) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.status = status;
        this.flightId = flightId;
        this.departureTime = departureTime;
        this.availability = availability;
        this.airfare = airfare;
        this.source = source;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public int getServiceNum() {
        return serviceNum;
    }

    public int getStatus() {
        return status;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public int getAvailability() {
        return availability;
    }

    public float getAirfare() {
        return airfare;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String generateOutputMessage() {
        if (this.status == Constants.FLIGHT_FOUND_STATUS) {
            return String.format(Constants.SUCCESSFUL_FLIGHT_DETAILS, this.departureTime, this.airfare, this.availability);
        }
        else if (this.status == Constants.FLIGHT_NOT_FOUND_STATUS) {
            return String.format(Constants.FAILED_FLIGHT_DETAILS, this.flightId);
        }
        else {
            return "Something went wrong. Status was invalid.";
        }
    }
}
