package com.example.class_exercise.controller;

import com.example.class_exercise.model.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {

    private static ArrayList<Room> roomList = new ArrayList<>();

    // Mock data for testing
    static {
        roomList.add(new Room("R001", "Phòng 101", 2500000, "Trống", "", ""));
        roomList.add(new Room("R002", "Phòng 102", 3000000, "Đã thuê", "Nguyễn Văn A", "0987654321"));
    }

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

    public void updateRoom(Room updatedRoom) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getRoomId().equals(updatedRoom.getRoomId())) {
                roomList.set(i, updatedRoom);
                break;
            }
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

    public Room getRoomById(String id) {
        for (Room r : roomList) {
            if (r.getRoomId().equals(id)) return r;
        }
        return null;
    }
}
