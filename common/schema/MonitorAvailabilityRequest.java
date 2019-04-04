package common.schema;

public class MonitorAvailabilityRequest {
    private int id;
    private int serviceNum;
    private int flightId;
    private int duration;

    public MonitorAvailabilityRequest(int id, int serviceNum, int flightId, int duration) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.flightId = flightId;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public int getServiceNum() {
        return serviceNum;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getDuration() {
        return duration;
    }
}
