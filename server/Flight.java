package server;

public class Flight {
    private int flightId;
    private String source;
    private String destination;
    private int departureTime;
    private float airfare;
    private int availability;
    private String date;

    public Flight(int flightId, String source, String destination, int departureTime,
                  float airfare, int availability, String date) {
        this.flightId = flightId;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.airfare = airfare;
        this.availability = availability;
        this.date = date;
    }

    public int getFlightId() {
        return flightId;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public float getAirfare() {
        return airfare;
    }

    public int getAvailability() {
        return availability;
    }

    public String getDate() {
        return date;
    }

    public Boolean reserveSeats (int numReserve) {
        if (this.availability >= numReserve) {
            this.availability -= numReserve;
            return true;
        }
        else {
            return false;
        }
    }
}
