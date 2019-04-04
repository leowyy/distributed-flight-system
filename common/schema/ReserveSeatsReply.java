package common.schema;

import common.Constants;

public class ReserveSeatsReply {
    public int id;
    public int status;
    public int flightId;
    public int numReserve;

    public ReserveSeatsReply() {
    }

    public float newBalance;
    public float price;

    public ReserveSeatsReply(int id, int status, int flightId, int numReserve, float newBalance, float price) {
        this.id = id;
        this.status = status;
        this.flightId = flightId;
        this.numReserve = numReserve;
        this.newBalance = newBalance;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getNumReserve() {
        return numReserve;
    }

    public float getNewBalance() {
        return newBalance;
    }

    public float getPrice() {
        return price;
    }

    public String generateOutputString() {
        if (status == Constants.SEATS_SUCCESSFULLY_RESERVED_STATUS) {
            return String.format(Constants.SEATS_SUCCESSFULLY_RESERVED_MSG, this.numReserve, this.flightId);
        }
        else if (status == Constants.FLIGHT_NOT_FOUND_STATUS) {
            return String.format(Constants.FAILED_FLIGHT_DETAILS, this.flightId);
        }
        else if (status == Constants.NO_AVAILABILITY_STATUS) {
            return String.format(Constants.FAILED_TO_RESERVE_SEATS_MSG, this.numReserve, this.flightId);
        }
        else if (status == Constants.NEGATIVE_RESERVATION_QUANTITY_STATUS) {
            return String.format(Constants.NEGATIVE_RESERVATION_QUANTITY_MSG, this.numReserve);
        }
        else if (status == Constants.NOT_ENOUGH_MONEY_STATUS) {
            return String.format(Constants.NOT_ENOUGH_MONEY_MSG, this.newBalance, this.price);
        }
        else {
            return "Something went wrong. Status was invalid.";
        }
    }
}
