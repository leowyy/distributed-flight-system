package common.schema;

public class TopUpAccountRequest {
    public int id;
    public int serviceNum;
    public int accountId;
    public float topUpAmount;

    public TopUpAccountRequest() {
    }

    public TopUpAccountRequest(int id, int serviceNum, int accountId, float topUpAmount) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.accountId = accountId;
        this.topUpAmount = topUpAmount;
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

    public float getTopUpAmount() {
        return topUpAmount;
    }
}
