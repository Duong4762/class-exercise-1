package com.example.class_exercise.adapter;

import com.example.class_exercise.controller.RoomManager;

public class RoomControllerSingleton {
    private static RoomManager instance;
    public static RoomManager getInstance() {
        if (instance == null)
            instance = new RoomManager();
        return instance;
    }
}
