package common.schema;

public class TopUpAccountReply {
    private int id;
    private int serviceNum;
    private int accountId;
    private float topUpAmount;
    private float newBalance;

    public TopUpAccountReply(int id, int serviceNum, int accountId, float topUpAmount, float newBalance) {
        this.id = id;
        this.serviceNum = serviceNum;
        this.accountId = accountId;
        this.topUpAmount = topUpAmount;
        this.newBalance = newBalance;
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

    public float getNewBalance() {
        return newBalance;
    }
}
