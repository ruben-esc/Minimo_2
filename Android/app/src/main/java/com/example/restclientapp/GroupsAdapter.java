package com.example.restclientapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restclientapp.model.Group;
import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    private List<Group> groups;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onJoinClick(String groupId, int position);
    }
    public GroupsAdapter(List<Group> groups, OnItemClickListener listener) {
        this.groups = groups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { // Ojo: quita 'final' si lo tenías
        Group group = groups.get(position);
        holder.tvName.setText(group.getNombre());
        holder.tvDesc.setText(group.getDescripcion());

        if (group.isMember()) {
            holder.btnJoin.setText("UNIDO");
            holder.btnJoin.setEnabled(false); // Desactivar click
            holder.btnJoin.setBackgroundColor(android.graphics.Color.GRAY);
        } else {
            holder.btnJoin.setText("UNIRSE");
            holder.btnJoin.setEnabled(true);
        }

        holder.btnJoin.setOnClickListener(v -> {
            listener.onJoinClick(group.getId(), holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc;
        Button btnJoin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvGroupName);
            tvDesc = itemView.findViewById(R.id.tvGroupDesc);
            btnJoin = itemView.findViewById(R.id.btnJoinGroup);
        }
    }
    public void setJoined(int position) {
        Group g = groups.get(position);
        g.setMember(true); // Cambiamos el estado a "verdadero"
        notifyItemChanged(position); // Avisamos a Android para que repinte esa fila
    }
}
