package common.schema;

import common.Constants;

public class MonitorAvailabilityReply {
    public int id;
    public int status;
    public int flightId;
    public int duration;
    public int availability;

    public MonitorAvailabilityReply() {
    }

    public MonitorAvailabilityReply(int id, int status, int flightId, int duration, int availability) {
        this.id = id;
        this.status = status;
        this.flightId = flightId;
        this.duration = duration;
        this.availability = availability;
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

    public int getDuration() {
        return duration;
    }

    public int getAvailability() {
        return availability;
    }

    public String generateOutputMessage() {
        if (status == Constants.FLIGHT_FOUND_STATUS) {
            return String.format(Constants.MONITORING_STARTED_MSG, this.flightId);
        }
        else if (status == Constants.MONITORING_NEW_UPDATE_STATUS) {
            return String.format(Constants.MONITORING_UPDATE_MSG, this.availability, this.flightId);
        }
        else {
            return "Something went wrong. Status was invalid.";
        }
    }
}
