package com.ruidun.service.weixinservice.Menu;



public class ComplexButton extends BaseButton{
    public BaseButton[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(BaseButton[] sub_button) {
        this.sub_button = sub_button;
    }

    private BaseButton[] sub_button;
}
