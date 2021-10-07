package com.example.devsu.my_ui;

/**
 * Created by MJ on 10/7/2021.
 */

public class ROLES {
    String ROLE_ID;
    String ROLE_DESC;

    public ROLES(String ROLE_ID, String ROLE_DESC) {
        this.ROLE_ID = ROLE_ID;
        this.ROLE_DESC = ROLE_DESC;
    }

    public String getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(String ROLE_ID) {
        this.ROLE_ID = ROLE_ID;
    }

    public String getROLE_DESC() {
        return ROLE_DESC;
    }

    public void setROLE_DESC(String ROLE_DESC) {
        this.ROLE_DESC = ROLE_DESC;
    }
}
