package common.schema;

public class FlightDetailsRequest {
    private int id;
    private int serviceNum;
    private int flightId;

    public FlightDetailsRequest(int id, int serviceNum, int flightId) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.flightId = flightId;
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
}
