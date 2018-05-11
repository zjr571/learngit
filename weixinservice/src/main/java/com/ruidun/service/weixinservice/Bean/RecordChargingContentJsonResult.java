package com.ruidun.service.weixinservice.Bean;

public class RecordChargingContentJsonResult {
    private int finishedOrder;
    private int cancalledOrder;
    private int pageIndex;
    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

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
