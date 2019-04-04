package server;

import common.Callback;
import common.Constants;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class FlightManager {
    private ArrayList<Flight> flights;
    private HashMap<Integer, ArrayList<Callback>> flightCallbacks;
    private HashMap<Integer, Float> accountBalances;

    public FlightManager() {
        this.flights = new ArrayList<>();
        this.flightCallbacks = new HashMap<>();
        this.accountBalances = new HashMap<>();
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
                return new FlightDetail(flightId, f.getSource(), f.getDestination(), f.getDepartureTime(),
                        f.getAirfare(), f.getAvailability());
            }
        }
        return null;
    }

    public int reserveSeatsForFlight (int accountId, int flightId, int numReserve) throws IOException, InterruptedException {
        Flight f = this.getFlightById(flightId);
        if (f == null) return Constants.FLIGHT_NOT_FOUND_STATUS;
        if (numReserve < 1) return Constants.NEGATIVE_RESERVATION_QUANTITY_STATUS;

        // check if account has sufficient money to pay for the seats
        float price = f.getAirfare() * numReserve;
        if (price > this.accountBalances.get(accountId)) return Constants.NOT_ENOUGH_MONEY_STATUS;

        Boolean ack = f.reserveSeats(numReserve);

        if (ack) {
            // deduct from the account balance
            float balance = this.accountBalances.get(accountId);
            this.accountBalances.put(accountId, balance - price);

            // do callback action for clients that are monitoring this flight
            int availability = f.getAvailability();
            this.sendUpdates(flightId, availability);

            return Constants.SEATS_SUCCESSFULLY_RESERVED_STATUS;
        }
        else return Constants.NO_AVAILABILITY_STATUS;
    }

    private void sendUpdates (int flightId, int availability) throws IOException, InterruptedException {
        if (!this.flightCallbacks.containsKey(flightId)) return;
        ArrayList<Callback> callbacks = this.flightCallbacks.get(flightId); // get the callbacks for this flight
        long currentTime = System.currentTimeMillis();
        Iterator iterator = callbacks.iterator();
        while (iterator.hasNext()) {
            Callback callback = (Callback) iterator.next();
            if (callback.hasExpired(currentTime)) { // expired callbacks are only removed when there is a potential update to be sent.
                iterator.remove();
                System.out.println("Callback removed for client with address " + callback.getClientAddress().toString());
            }
            else {
                callback.update(availability);
            }
        }
    }

    public void registerCallback (int flightId, int duration, InetAddress inetAddress, int port, DatagramSocket udpSocket) {
        long expiry = System.currentTimeMillis() + (duration * 1000);
        Callback callback = new Callback(flightId, expiry, inetAddress, port, udpSocket);
        if (!this.flightCallbacks.containsKey(flightId)) {
            this.flightCallbacks.put(flightId, new ArrayList<>());
        }
        ArrayList<Callback> callbacks = this.flightCallbacks.get(flightId);
        callbacks.add(callback);
    }

    public ArrayList<Integer> searchFlightsBelowPrice(float price) {
        ArrayList<Integer> flightIds = new ArrayList<>();
        for (Flight f : this.flights) {
            if (f.getAirfare() <= price) {
                flightIds.add(f.getFlightId());
            }
        }
        return flightIds;
    }

    public void topUpAccount(int accountId, float topUpAmount) {
        float balance = this.accountBalances.get(accountId);
        this.accountBalances.put(accountId, balance + topUpAmount);
    }

    public float getBalance(int accountId) {
        return this.accountBalances.get(accountId);
    }

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
