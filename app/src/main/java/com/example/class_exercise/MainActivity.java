package com.example.class_exercise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.class_exercise.adapter.RoomAdapter;
import com.example.class_exercise.controller.RoomManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements RoomAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private FloatingActionButton fabAdd;
    
    private ActivityResultLauncher<Intent> addRoomLauncher;
    private ActivityResultLauncher<Intent> editRoomLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        addSampleData();
        initViews();
        setupRecyclerView();
        setupActivityResultLaunchers();
        setupListeners();
    }
    
    private void addSampleData() {
        if (RoomManager.getRoomList().isEmpty()) {
            RoomManager.addRoom(new com.example.class_exercise.model.Room("P001", "Phòng 101", 2000000, "Còn trống", "", ""));
            RoomManager.addRoom(new com.example.class_exercise.model.Room("P002", "Phòng 102", 2500000, "Đã thuê", "Nguyễn Văn A", "0912345678"));
            RoomManager.addRoom(new com.example.class_exercise.model.Room("P003", "Phòng 201", 3000000, "Còn trống", "", ""));
        }
    }
    
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
    }
    
    private void setupRecyclerView() {
        roomAdapter = new RoomAdapter(RoomManager.getRoomList(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(roomAdapter);
    }
    
    private void setupActivityResultLaunchers() {
        addRoomLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    roomAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Đã thêm phòng thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        );
        
        editRoomLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    roomAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Đã cập nhật phòng thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }
    
    private void setupListeners() {
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
            addRoomLauncher.launch(intent);
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, EditRoomActivity.class);
        intent.putExtra("ROOM_POSITION", position);
        editRoomLauncher.launch(intent);
    }

    @Override
    public void onEditClick(int position) {
        Intent intent = new Intent(MainActivity.this, EditRoomActivity.class);
        intent.putExtra("ROOM_POSITION", position);
        editRoomLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        new AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
            .setPositiveButton("Xóa", (dialog, which) -> {
                RoomManager.deleteRoom(position);
                roomAdapter.notifyItemRemoved(position);
                roomAdapter.notifyItemRangeChanged(position, RoomManager.getRoomList().size());
                Toast.makeText(this, "Đã xóa phòng!", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Hủy", null)
            .show();
    }
}