package common.schema;

public class FlightsByPriceRequest {
    public int id;
    public int serviceNum;
    public float price;

    public FlightsByPriceRequest() {
        this(0,0,0);
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceNum(int serviceNum) {
        this.serviceNum = serviceNum;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
