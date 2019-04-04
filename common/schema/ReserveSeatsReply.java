package common.schema;

public class ReserveSeatsReply {
    private int id;
    private int serviceNum;
    private int status;
    private int flightId;
    private int numReserve;
    private float newBalance;

    public ReserveSeatsReply(int id, int serviceNum, int status, int flightId, int numReserve, float newBalance) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.status = status;
        this.flightId = flightId;
        this.numReserve = numReserve;
        this.newBalance = newBalance;
    }

    public int getId() {
        return id;
    }

    public int getServiceNum() {
        return serviceNum;
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
}
