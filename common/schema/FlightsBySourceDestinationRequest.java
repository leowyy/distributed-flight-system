package common.schema;

public class FlightsBySourceDestinationRequest {
    public int id;
    public int serviceNum;
    public String source;
    public String destination;

    public FlightsBySourceDestinationRequest() {
        this(0,0,"", "");
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceNum(int serviceNum) {
        this.serviceNum = serviceNum;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
