package common;

import java.net.InetSocketAddress;

public class Callback {
    private int flightId;
    private long expiry;
    private final InetSocketAddress inetSocketAddress;

    public Callback (int flightId, long expiry, InetSocketAddress inetSocketAddress) {
        this.flightId = flightId;
        this.expiry = expiry;
        this.inetSocketAddress = inetSocketAddress;
    }
    public void update (int availability) {
        // need to do some sending
        System.out.println("Available seats: " + availability);
    }

    public int getFlightId() {
        return flightId;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public Boolean hasExpired(long currentTime) {
        return currentTime > this.expiry;
    }
}
