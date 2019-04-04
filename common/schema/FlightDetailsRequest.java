package common.schema;

public class FlightDetailsRequest {
    public int id;
    public int serviceNum;
    public int flightId;

    public FlightDetailsRequest() {
        this(0, 0, 0);
    }

    public FlightDetailsRequest(int id, int serviceNum, int flightId) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.flightId = flightId;
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
}
