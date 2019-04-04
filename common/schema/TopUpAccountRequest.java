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

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceNum(int serviceNum) {
        this.serviceNum = serviceNum;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setTopUpAmount(float topUpAmount) {
        this.topUpAmount = topUpAmount;
    }
}
