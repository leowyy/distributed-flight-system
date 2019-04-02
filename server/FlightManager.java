package server;

import common.Callback;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

// need some timer so as to turn off expired callbacks.

public class FlightManager {
    private ArrayList<Flight> flights;
    private HashMap<Integer, ArrayList<Callback>> flightCallbacks;

    public FlightManager() {
        this.flights = new ArrayList<>();
        this.flightCallbacks = new HashMap<>();
    }

    public ArrayList<Integer> getFlightsBySourceDestination (String source, String destination) {
        ArrayList<Integer> flightIds = new ArrayList<>();
        for (Flight f : this.flights) {
            if (source.equalsIgnoreCase(f.getSource()) && destination.equalsIgnoreCase(f.getDestination())) {
                flightIds.add(f.getFlightId());
            }
        }
        return flightIds;
    }

    // return flight details in string to be unpacked, or in class, or json, or some other way?
    public FlightDetail getFlightDetails (int flightId) {

        for (Flight f : this.flights) {
            if (flightId == f.getFlightId()) {
                return new FlightDetail(flightId, f.getDestination(), f.getDepartureTime(), f.getAirfare(), f.getAvailability());
            }
        }
        return null;
    }

    public Boolean reserveSeatsForFlight (int flightId, int numReserve) {
        Flight f = this.getFlightById(flightId);
        Boolean ack = f.reserveSeats(numReserve);

        if (ack) {
            // do callback action for clients that are monitoring this flight
            int availability = f.getAvailability();
            this.sendUpdates(flightId, availability);
        }
        return ack;
    }

    private void sendUpdates (int flightId, int availability) {
        ArrayList<Callback> callbacks = this.flightCallbacks.get(flightId); // get the callbacks for this flight
        long currentTime = System.currentTimeMillis();
        Iterator iterator = callbacks.iterator();
        while (iterator.hasNext()) {
            Callback callback = (Callback) iterator.next();
            if (callback.hasExpired(currentTime)) { // expired callbacks are only removed when there is a potential update to be sent.
                iterator.remove();
                System.out.println("Callback removed for client with address " + callback.getInetSocketAddress().toString());
            }
            else {
                callback.update(availability);
            }
        }
    }

    public void registerCallback (int flightId, int duration, InetSocketAddress inetSocketAddress) {
        long expiry = System.currentTimeMillis() + (duration * 1000);
        Callback callback = new Callback(flightId, expiry, inetSocketAddress);
        if (!this.flightCallbacks.containsKey(flightId)) {
            this.flightCallbacks.put(flightId, new ArrayList<>());
        }
        ArrayList<Callback> callbacks = this.flightCallbacks.get(flightId);
        callbacks.add(callback);
    }

//    public void registerMonitorAvailabilityCallback(Callback cbObject) throws Exception {
//        this.callbacks.add(cbObject);
//    }
//
//    public void deregisterMonitorAvailabilityCallback(Callback cbObject) throws Exception {
//
//    }

    private Flight getFlightById (int flightId) {
        for (Flight f : this.flights) {
            if (flightId == f.getFlightId()) {
                return f;
            }
        }
        return null;
    }

    public void initialiseDummyData () {
        this.flights.add(new Flight(1, "a", "a", 0, 10, 100, "25"));
        this.flights.add(new Flight(2, "b", "b", 1, 15, 200, "25"));
        this.flights.add(new Flight(3, "c", "c", 2, 20, 300, "25"));

    }

    public static void main(String[] args){
        FlightManager manager = new FlightManager();
        manager.initialiseDummyData();
        FlightDetail foo = manager.getFlightDetails(1);
        foo.print();
    }
}
