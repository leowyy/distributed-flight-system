package common.schema;

import common.Constants;

import java.util.Arrays;

public class FlightsByPriceReply {
    public int id;
    public int status;
    public float price;
    public int[] flights;

    public FlightsByPriceReply() {
        this(0, 0, 0, new int[0]);
    }

    public FlightsByPriceReply(int id, int status, float price, int[] flights) {
        this.id = id;
        this.status = status;
        this.price = price;
        this.flights = flights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int[] getFlights() {
        return flights;
    }

    public void setFlights(int[] flights) {
        this.flights = flights;
    }

    public String generateOutputMessage() {
        if (this.status == Constants.FLIGHT_FOUND_STATUS) {
            return String.format(Constants.FLIGHTS_FOUND_BY_PRICE_MSG, this.price) + Arrays.toString(this.flights);
        } else if (this.status == Constants.FLIGHT_NOT_FOUND_STATUS) {
            return String.format(Constants.FLIGHTS_NOT_FOUND_BY_PRICE_MSG, this.price);
        } else {
            return "Something went wrong. Status was invalid.";
        }
    }
}
