package common.schema;

import common.Constants;

public class FlightDetailsReply {
    public int id;
    public int status;
    public int flightId;
    public int departureTime;
    public int availability;
    public float airfare;
    public String source;
    public String destination;

    public FlightDetailsReply() {
        this(0,0,0,0,0,0,"", "");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setAirfare(float airfare) {
        this.airfare = airfare;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public FlightDetailsReply(int id, int status, int flightId, int departureTime, int availability,
                              float airfare, String source, String destination) {
        this.id = id;
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
