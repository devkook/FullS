package com.diginori.fulls.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class History extends RealmObject {

    @PrimaryKey
    private String date;
    private String log;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
