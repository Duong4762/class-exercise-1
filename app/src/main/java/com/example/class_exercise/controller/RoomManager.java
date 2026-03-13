package com.example.class_exercise.controller;

import com.example.class_exercise.model.Room;

import java.util.ArrayList;

public class RoomManager {

    private static ArrayList<Room> roomList = new ArrayList<>();

    public static ArrayList<Room> getRoomList() {
        return roomList;
    }

    public static void addRoom(Room room) {
        roomList.add(room);
    }

    public static void updateRoom(int position, Room room) {
        if (position >= 0 && position < roomList.size()) {
            roomList.set(position, room);
        }
    }

    public static void deleteRoom(int position) {
        if (position >= 0 && position < roomList.size()) {
            roomList.remove(position);
        }
    }

    public static Room getRoom(int position) {
        if (position >= 0 && position < roomList.size()) {
            return roomList.get(position);
        }
        return null;
    }
    
    public static Room getRoomById(String roomId) {
        for (Room room : roomList) {
            if (room.getRoomId().equals(roomId)) {
                return room;
            }
        }
        return null;
    }
}