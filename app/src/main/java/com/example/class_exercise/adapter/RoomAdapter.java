package com.example.class_exercise.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.class_exercise.R;
import com.example.class_exercise.model.Room;

import java.util.List;
import java.util.Locale;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private List<Room> roomList;
    private OnRoomClickListener listener;

    // Interface để xử lý sự kiện click
    public interface OnRoomClickListener {
        void onItemClick(Room room, int position);
        void onEditClick(Room room, int position);
        void onDeleteClick(Room room, int position);
    }

    public RoomAdapter(List<Room> roomList, OnRoomClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtRoomName, txtPrice, txtStatus;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRoomName = itemView.findViewById(R.id.txtRoomName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = roomList.get(position);

        // Đổ dữ liệu vào view
        holder.txtRoomName.setText(room.getRoomName());
        holder.txtPrice.setText("Giá: " + String.format(Locale.getDefault(), "%,.0f", room.getPrice()) + " VND");
        holder.txtStatus.setText("Trạng thái: " + room.getStatus());

        // Hiển thị màu sắc theo trạng thái
        if ("Trống".equalsIgnoreCase(room.getStatus()) || "Available".equalsIgnoreCase(room.getStatus())) {
            holder.txtStatus.setTextColor(Color.GREEN);
        } else {
            holder.txtStatus.setTextColor(Color.RED);
        }

        // Xử lý sự kiện click item sử dụng getAdapterPosition() để đảm bảo chính xác
        holder.itemView.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (listener != null && currentPos != RecyclerView.NO_POSITION) {
                listener.onItemClick(roomList.get(currentPos), currentPos);
            }
        });

        // Xử lý nút Edit
        holder.btnEdit.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (listener != null && currentPos != RecyclerView.NO_POSITION) {
                listener.onEditClick(roomList.get(currentPos), currentPos);
            }
        });

        // Xử lý nút Delete
        holder.btnDelete.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (listener != null && currentPos != RecyclerView.NO_POSITION) {
                listener.onDeleteClick(roomList.get(currentPos), currentPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList != null ? roomList.size() : 0;
    }

    // Phương thức để cập nhật lại danh sách dữ liệu
    public void updateData(List<Room> newList) {
        this.roomList = newList;
        notifyDataSetChanged();
    }
}
