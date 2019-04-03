package server;

import common.Constants;

/**
 * Created by signapoop on 2/4/19.
 */
public class FlightDetail {
    int flightId;
    String source;
    String destination;
    int departureTime;
    float airfare;
    int availability;

    public FlightDetail(int flightId, String source, String destination, int departureTime,
                  float airfare, int availability) {
        this.flightId = flightId;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.airfare = airfare;
        this.availability = availability;
    }

    public void print() {
        System.out.printf(Constants.SUCCESSFUL_FLIGHT_DETAILS, flightId, departureTime, availability, airfare, destination);
    }
}
