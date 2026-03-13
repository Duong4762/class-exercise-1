package com.example.class_exercise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.class_exercise.adapter.RoomControllerSingleton;
import com.example.class_exercise.controller.RoomManager;
import com.example.class_exercise.model.Room;

public class EditRoomActivity extends AppCompatActivity {
    private EditText edtRoomName, edtTenant, edtStatus;
    private Button btnSave;
    private RoomManager controller;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);

        edtRoomName = findViewById(R.id.edtRoomName);
        edtTenant = findViewById(R.id.edtTenant);
        edtStatus = findViewById(R.id.edtStatus);
        btnSave = findViewById(R.id.btnSave);

        controller = RoomControllerSingleton.getInstance();
        String id = getIntent().getStringExtra("room_id");
        room = controller.getRoomById(id);

        if (room != null) {
            edtRoomName.setText(room.getRoomName());
            edtTenant.setText(room.getTenantName());
            edtStatus.setText(room.getStatus());
        }

        btnSave.setOnClickListener(view -> {
            if (room != null) {
                room.setRoomName(edtRoomName.getText().toString());
                room.setTenantName(edtTenant.getText().toString());
                room.setStatus(edtStatus.getText().toString());
                controller.updateRoom(room);
                setResult(RESULT_OK);
            }
            finish();
        });
    }
}