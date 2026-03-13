package com.example.class_exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.class_exercise.model.Room;
import com.example.class_exercise.controller.RoomManager;

public class EditRoomActivity extends AppCompatActivity {

    private TextView tvRoomId;
    private EditText edtRoomName, edtPrice, edtTenantName, edtPhone;
    private RadioGroup radioGroupStatus;
    private RadioButton rbAvailable, rbRented;
    private LinearLayout layoutTenantInfo;
    private Button btnSave, btnCancel;
    
    private int roomPosition;
    private Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initViews();
        loadRoomData();
        setupListeners();
    }

    private void initViews() {
        tvRoomId = findViewById(R.id.tvRoomId);
        edtRoomName = findViewById(R.id.edtRoomName);
        edtPrice = findViewById(R.id.edtPrice);
        edtTenantName = findViewById(R.id.edtTenantName);
        edtPhone = findViewById(R.id.edtPhone);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        rbAvailable = findViewById(R.id.rbAvailable);
        rbRented = findViewById(R.id.rbRented);
        layoutTenantInfo = findViewById(R.id.layoutTenantInfo);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadRoomData() {
        roomPosition = getIntent().getIntExtra("ROOM_POSITION", -1);
        if (roomPosition == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy phòng!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        currentRoom = RoomManager.getRoom(roomPosition);
        if (currentRoom == null) {
            Toast.makeText(this, "Lỗi: Không tìm thấy phòng!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        tvRoomId.setText(currentRoom.getRoomId());
        edtRoomName.setText(currentRoom.getRoomName());
        edtPrice.setText(String.valueOf(currentRoom.getPrice()));
        
        if ("Đã thuê".equals(currentRoom.getStatus())) {
            rbRented.setChecked(true);
            layoutTenantInfo.setVisibility(View.VISIBLE);
            edtTenantName.setText(currentRoom.getTenantName());
            edtPhone.setText(currentRoom.getPhone());
        } else {
            rbAvailable.setChecked(true);
            layoutTenantInfo.setVisibility(View.GONE);
        }
    }

    private void setupListeners() {
        radioGroupStatus.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbRented) {
                layoutTenantInfo.setVisibility(View.VISIBLE);
            } else {
                layoutTenantInfo.setVisibility(View.GONE);
                edtTenantName.setText("");
                edtPhone.setText("");
            }
        });

        btnSave.setOnClickListener(v -> {
            if (validateInput()) {
                updateRoom();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private boolean validateInput() {
        if (edtRoomName.getText().toString().trim().isEmpty()) {
            edtRoomName.setError("Vui lòng nhập tên phòng");
            edtRoomName.requestFocus();
            return false;
        }

        String priceStr = edtPrice.getText().toString().trim();
        if (priceStr.isEmpty()) {
            edtPrice.setError("Vui lòng nhập giá thuê");
            edtPrice.requestFocus();
            return false;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                edtPrice.setError("Giá thuê phải lớn hơn 0");
                edtPrice.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            edtPrice.setError("Giá thuê không hợp lệ");
            edtPrice.requestFocus();
            return false;
        }

        if (rbRented.isChecked()) {
            if (edtTenantName.getText().toString().trim().isEmpty()) {
                edtTenantName.setError("Vui lòng nhập tên người thuê");
                edtTenantName.requestFocus();
                return false;
            }

            String phone = edtPhone.getText().toString().trim();
            if (phone.isEmpty()) {
                edtPhone.setError("Vui lòng nhập số điện thoại");
                edtPhone.requestFocus();
                return false;
            }

            if (phone.length() < 10 || phone.length() > 11) {
                edtPhone.setError("Số điện thoại phải 10-11 số");
                edtPhone.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void updateRoom() {
        String roomName = edtRoomName.getText().toString().trim();
        double price = Double.parseDouble(edtPrice.getText().toString().trim());
        String status = rbAvailable.isChecked() ? "Còn trống" : "Đã thuê";
        
        String tenantName = "";
        String phone = "";
        
        if (rbRented.isChecked()) {
            tenantName = edtTenantName.getText().toString().trim();
            phone = edtPhone.getText().toString().trim();
        }

        currentRoom.setRoomName(roomName);
        currentRoom.setPrice(price);
        currentRoom.setStatus(status);
        currentRoom.setTenantName(tenantName);
        currentRoom.setPhone(phone);
        
        RoomManager.updateRoom(roomPosition, currentRoom);

        setResult(RESULT_OK);
        Toast.makeText(this, "Cập nhật phòng thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }
}