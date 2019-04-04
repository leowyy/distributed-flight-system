package common.schema;

import common.Constants;

import java.util.Arrays;

public class FlightsByPriceReply {
    private int id;
    private int status;
    private float price;
    private int[] flights;

    public FlightsByPriceReply(int id, int status, float price, int[] flights) {
        this.id = id;
        this.status = status;
        this.price = price;
        this.flights = flights;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public float getPrice() {
        return price;
    }

    public int[] getFlights() {
        return flights;
    }

    public String generateOutputMessage() {
        if (this.status == Constants.FLIGHT_FOUND_STATUS) {
            return String.format(Constants.FLIGHTS_FOUND_BY_PRICE_MSG, this.price) + Arrays.toString(this.flights);
        }
        else if (this.status == Constants.FLIGHT_NOT_FOUND_STATUS) {
            return String.format(Constants.FLIGHTS_NOT_FOUND_BY_PRICE_MSG, this.price);
        }
        else {
            return "Something went wrong. Status was invalid.";
        }
    }
}
