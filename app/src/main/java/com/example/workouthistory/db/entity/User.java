package com.example.workouthistory.db.entity;

import java.util.ArrayList;

public class User {
    public static final String TABLE_NAME = "exercises";

    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_BODYWEIGHT = "bodyweight";
    public static final String COL_AGE = "age";
    public static final String COL_HEIGHT = "height";
    public static final String COL_BB_CHEST_PRESS = "bb_chest_press";
    public static final String COL_BB_INCLINE_CHEST_PRESS = "bb_incline_chest_press";
    public static final String COL_BB_ROW = "bb_row";
    public static final String COL_BB_SQUAT = "bb_squat";
    public static final String COL_CABLE_CROSSOVER = "cable_crossover";
    public static final String COL_CABLE_HAMMER_CURLS = "cable_hammer_curls";
    public static final String COL_CABLE_LATERAL_RAISE = "cable_lateral_raise";
    public static final String COL_CLOSE_GRIP_PRESS = "close_grip_press";
    public static final String COL_CLOSE_GRIP_PULLDOWN = "close_grip_pulldown";
    public static final String COL_DB_BENT_OVER_ROW = "db_bent_over_row";
    public static final String COL_DB_CHEST_PRESS = "db_chest_press";
    public static final String COL_DB_CURLS = "db_curls";
    public static final String COL_DB_HAMMER_CURLS = "db_hammer_curls";
    public static final String COL_DB_INCLINE_CHEST_PRESS = "db_incline_chest_press";
    public static final String COL_DB_LATERAL_RAISES = "db_lateral_raises";
    public static final String COL_DB_MILITARY_PRESS = "db_military_press";
    public static final String COL_EZ_CURL = "ez_curl";
    public static final String COL_INCLINE_DB_CURLS = "incline_db_curls";
    public static final String COL_MACHINE_CHEST_PRESS = "machine_chest_press";
    public static final String COL_PARALLEL_BARS_DIP = "parallel_bars_dip";
    public static final String COL_ROPE_PUSHDOWN = "rope_pushdown";
    public static final String COL_SEATED_CABLE_ROW = "seated_cable_row";
    public static final String COL_SKULLCRUSHERS = "skullcrushers";
    public static final String COL_SM_MILITARY_PRESS = "sm_military_press";
    public static final String COL_STRAIGHT_ARM_PULLDOWN = "straight_arm_pulldown";
    public static final String COL_T_BAR_ROW = "t_bar_row";
    public static final String COL_TRICEPS = "triceps";
    public static final String COL_Y_CABLE_CROSSOVERS = "y_cable_crossovers";

    ArrayList<String> columnsList = new ArrayList<>();
    private String username, logs;
    private int age, id;
    private float body_weight, height;

    public User()
    {

    }
    public User(String username, int age, int id, float body_weight, float height) {
        this.username = username;
        this.age = age;
        this.body_weight = body_weight;
        this.height = height;
        this.id=id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getBody_weight() {
        return body_weight;
    }

    public void setBody_weight(float body_weight) {
        this.body_weight = body_weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_USERNAME + " TEXT," +
            COL_BODYWEIGHT + " REAL," +
            COL_AGE + " INTEGER," +
            COL_HEIGHT + " REAL," +
            COL_BB_CHEST_PRESS + " TEXT," +
            COL_BB_INCLINE_CHEST_PRESS + " TEXT," +
            COL_BB_ROW + " TEXT," +
            COL_BB_SQUAT + " TEXT," +
            COL_CABLE_CROSSOVER + " TEXT," +
            COL_CABLE_HAMMER_CURLS + " TEXT," +
            COL_CABLE_LATERAL_RAISE + " TEXT," +
            COL_CLOSE_GRIP_PRESS + " TEXT," +
            COL_CLOSE_GRIP_PULLDOWN + " TEXT," +
            COL_DB_BENT_OVER_ROW + " TEXT," +
            COL_DB_CHEST_PRESS + " TEXT," +
            COL_DB_CURLS + " TEXT," +
            COL_DB_HAMMER_CURLS + " TEXT," +
            COL_DB_INCLINE_CHEST_PRESS + " TEXT," +
            COL_DB_LATERAL_RAISES + " TEXT," +
            COL_DB_MILITARY_PRESS + " TEXT," +
            COL_EZ_CURL + " TEXT," +
            COL_INCLINE_DB_CURLS + " TEXT," +
            COL_MACHINE_CHEST_PRESS + " TEXT," +
            COL_PARALLEL_BARS_DIP + " TEXT," +
            COL_ROPE_PUSHDOWN + " TEXT," +
            COL_SEATED_CABLE_ROW + " TEXT," +
            COL_SKULLCRUSHERS + " TEXT," +
            COL_SM_MILITARY_PRESS + " TEXT," +
            COL_STRAIGHT_ARM_PULLDOWN + " TEXT," +
            COL_T_BAR_ROW + " TEXT," +
            COL_TRICEPS + " TEXT," +
            COL_Y_CABLE_CROSSOVERS + " TEXT" +
            ")";
}
