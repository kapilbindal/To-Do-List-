package com.example.kapil.test.model;

/**
 * Created by KAPIL on 19-12-2017.
 */

public class Task {
    String data;
    Boolean isChecked;
    int id;

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
