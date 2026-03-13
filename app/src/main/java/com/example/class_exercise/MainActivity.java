package com.example.class_exercise;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.class_exercise.adapter.RoomAdapter;
import com.example.class_exercise.controller.RoomManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private FloatingActionButton fabAdd;
    
    private ActivityResultLauncher<Intent> addRoomLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        setupRecyclerView();
        setupActivityResultLauncher();
        setupListeners();
    }
    
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
    }
    
    private void setupRecyclerView() {
        roomAdapter = new RoomAdapter(this, RoomManager.getRoomList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(roomAdapter);
    }
    
    private void setupActivityResultLauncher() {
        addRoomLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getBooleanExtra("IS_ADDED", false)) {
                        roomAdapter.notifyDataSetChanged();
                    }
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
}