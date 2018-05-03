package com.ruidun.service.weixinservice.Bean;

public class RecordChargingContentJsonResult {
    private int finishedOrder;
    private int cancalledOrder;
    private  Object content;

    public int getFinishedOrder() {
        return finishedOrder;
    }

    public void setFinishedOrder(int finishedOrder) {
        this.finishedOrder = finishedOrder;
    }

    public int getCancalledOrder() {
        return cancalledOrder;
    }

    public void setCancalledOrder(int cancalledOrder) {
        this.cancalledOrder = cancalledOrder;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
