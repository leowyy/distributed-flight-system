package common.schema;

public class MonitorAvailabilityReply {
    private int id;
    private int serviceNum;
    private int status;
    private int flightId;
    private int duration;
    private int availability;

    public MonitorAvailabilityReply(int id, int serviceNum, int status, int flightId, int duration, int availability) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.status = status;
        this.flightId = flightId;
        this.duration = duration;
        this.availability = availability;
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

    public int getDuration() {
        return duration;
    }

    public int getAvailability() {
        return availability;
    }
}
