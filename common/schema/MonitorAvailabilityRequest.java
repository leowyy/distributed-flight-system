package common.schema;

public class MonitorAvailabilityRequest {
    public int id;
    public int serviceNum;
    public int flightId;
    public int duration;

    public MonitorAvailabilityRequest() {
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

    public int getServiceNum() {
        return serviceNum;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceNum(int serviceNum) {
        this.serviceNum = serviceNum;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
