package com.denproj.educonnectv2.ui.dashboard.group.rcv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denproj.educonnectv2.databinding.GroupCardBinding;
import com.denproj.educonnectv2.room.entity.Group;

import java.util.List;

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>{


    List<Group> groupList;


    public GroupRecyclerViewAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(GroupCardBinding.inflate(LayoutInflater.from(parent.getContext())).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.groupNameTextView.setText(groupList.get(position).groupName);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public GroupCardBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = GroupCardBinding.bind(itemView);
        }
    }


}
