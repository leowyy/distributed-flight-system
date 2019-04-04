package common.schema;

public class MonitorAvailabilityRequest {
    public int id;
    public int serviceNum;
    public int flightId;
    public int duration;

    public MonitorAvailabilityRequest() {
        this(0, 0, 0, 0);
    }

    public MonitorAvailabilityRequest(int id, int serviceNum, int flightId, int duration) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.flightId = flightId;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(int serviceNum) {
        this.serviceNum = serviceNum;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
