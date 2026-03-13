package com.example.class_exercise.adapter;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{

    List<Room> roomList;
    OnItemClick listener;

    public interface OnItemClick{
        void onClick(int position);
        void onDelete(int position);
    }

    public RoomAdapter(List<Room> roomList, OnItemClick listener){
        this.roomList = roomList;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, price, status;
        Button delete;

        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.txtRoomName);
            price = itemView.findViewById(R.id.txtPrice);
            status = itemView.findViewById(R.id.txtStatus);
            edit = itemView.findViewById(R.id.btnEdit);
            delete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){

        Room room = roomList.get(position);

        holder.name.setText(room.getName());
        holder.price.setText("Price: "+room.getPrice());
        holder.status.setText(room.getStatus());

        if(room.getStatus().equals("Available")){
            holder.status.setTextColor(Color.GREEN);
        }else{
            holder.status.setTextColor(Color.RED);
        }

        holder.itemView.setOnClickListener(v -> listener.onClick(position));

        holder.delete.setOnClickListener(v -> listener.onDelete(position));
    }

    @Override
    public int getItemCount(){
        return roomList.size();
    }
}
