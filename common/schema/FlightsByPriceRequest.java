package common.schema;

public class FlightsByPriceRequest {
    private int id;
    private int serviceNum;
    private float price;

    public FlightsByPriceRequest(int id, int serviceNum, float price) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getServiceNum() {
        return serviceNum;
    }

    public float getPrice() {
        return price;
    }
}
