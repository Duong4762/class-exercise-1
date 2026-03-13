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
import com.example.class_exercise.model.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private FloatingActionButton fabAdd;
    
    private ActivityResultLauncher<Intent> startForResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        setupActivityResultLauncher();
        setupRecyclerView();
        setupListeners();
    }
    
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void setupActivityResultLauncher() {
        // Sử dụng API mới để nhận kết quả trả về từ Activity khác
        startForResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Làm mới danh sách khi Thêm hoặc Sửa thành công
                        roomAdapter.notifyDataSetChanged();
                    }
                }
        );
    }
    
    private void setupRecyclerView() {
        roomAdapter = new RoomAdapter(RoomManager.getRoomList(), new RoomAdapter.OnRoomClickListener() {
            @Override
            public void onItemClick(Room room, int position) {
                Toast.makeText(MainActivity.this, "Phòng: " + room.getRoomName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEditClick(Room room, int position) {
                Intent intent = new Intent(MainActivity.this, EditRoomActivity.class);
                intent.putExtra("room_id", room.getRoomId());
                startForResultLauncher.launch(intent);
            }

            @Override
            public void onDeleteClick(Room room, int position) {
                showDeleteConfirmDialog(position);
            }
        });
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(roomAdapter);
    }
    
    private void setupListeners() {
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
            startForResultLauncher.launch(intent);
        });
    }

    private void showDeleteConfirmDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    RoomManager.deleteRoom(position);
                    roomAdapter.notifyItemRemoved(position);
                    roomAdapter.notifyItemRangeChanged(position, RoomManager.getRoomList().size());
                    Toast.makeText(this, "Đã xóa phòng", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
