package common.schema;

import common.Constants;

public class TopUpAccountReply {
    private int id;
    private int accountId;
    private float topUpAmount;
    private float newBalance;

    public TopUpAccountReply(int id, int accountId, float topUpAmount, float newBalance) {
        this.id = id;
        this.accountId = accountId;
        this.topUpAmount = topUpAmount;
        this.newBalance = newBalance;
    }

    public int getId() {
        return id;
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

    public String generateOutputMessage() {
        return String.format(Constants.ACCOUNT_TOPPED_UP_MSG, this.topUpAmount, this.newBalance);
    }
}