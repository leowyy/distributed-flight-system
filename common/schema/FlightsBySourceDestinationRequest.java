package common.schema;

public class FlightsBySourceDestinationRequest {
    private int id;
    private int serviceNum;
    private String source;
    private String destination;

    public FlightsBySourceDestinationRequest(int id, int serviceNum, String source, String destination) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.source = source;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public int getServiceNum() {
        return serviceNum;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}
