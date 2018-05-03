package com.ruidun.service.weixinservice.Bean;

public class NearChargingJsonResult {
    private int pageIndex;
    private int pageSize;
    private Object content = null;

    public int getPageIndex() {
        return pageIndex;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
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
    public NearChargingJsonResult status(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize=pageSize;
        return this;
    }
}
