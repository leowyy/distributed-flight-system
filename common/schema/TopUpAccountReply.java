package common.schema;

import common.Constants;

public class TopUpAccountReply {
    public int id;
    public int accountId;

    public TopUpAccountReply() {
    }

    public float topUpAmount;
    public float newBalance;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setTopUpAmount(float topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public void setNewBalance(float newBalance) {
        this.newBalance = newBalance;
    }

    public String generateOutputMessage() {
        return String.format(Constants.ACCOUNT_TOPPED_UP_MSG, this.topUpAmount, this.newBalance);
    }
}
