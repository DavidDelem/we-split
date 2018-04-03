package com.uqac.wesplit.enums;

public enum PeriodesEnum {

    SEPTJOURS ("7 derniers jours "),
    QUINZEJOURS ("15 derniers jours"),
    TRENTEJOURS ("30 derniers jours"),
    TOUT ("Tout prendre en compte");

    private String periode = "";

    PeriodesEnum(String periode){
        this.periode = periode;
    }

    public String toString(){
        return periode;
    }

}
