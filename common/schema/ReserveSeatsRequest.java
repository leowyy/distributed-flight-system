package common.schema;

public class ReserveSeatsRequest {
    public int id;
    public int serviceNum;
    public int accountId;
    public int flightId;
    public int numReserve;

    public ReserveSeatsRequest() {
    }

    public ReserveSeatsRequest(int id, int serviceNum, int accountId, int flightId, int numReserve) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.accountId = accountId;
        this.flightId = flightId;
        this.numReserve = numReserve;
    }

    public int getId() {
        return id;
    }

    public int getServiceNum() {
        return serviceNum;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getNumReserve() {
        return numReserve;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceNum(int serviceNum) {
        this.serviceNum = serviceNum;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setNumReserve(int numReserve) {
        this.numReserve = numReserve;
    }
}
