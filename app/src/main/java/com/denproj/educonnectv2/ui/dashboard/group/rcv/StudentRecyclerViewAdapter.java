package com.denproj.educonnectv2.ui.dashboard.group.rcv;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.StudentSelectableLayoutBinding;
import com.denproj.educonnectv2.room.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.ViewHolder> implements Filterable {


    public void setOriginalList(List<Student> originalList) {
        this.originalList = originalList;
    }

    private List<Student> originalList;

    List<Student> selected = new ArrayList<>();

    public NotifyCheckedChange notifyCheckedChange;

    public StudentRecyclerViewAdapter(List<Student> studentList) {
        originalList = studentList;
    }

    public StudentRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.student_selectable_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = selected.get(position);
        holder.binding.setStudent(student);
        holder.binding.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            student.isSelected = b;
            notifyCheckedChange.notifyChange(originalList.indexOf(student));

        });
        Log.d("RCV", "Executed");
    }

    @Override
    public int getItemCount() {
        return selected.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = getFilterResults(charSequence.toString());
                Log.d("RCVAdapter", "performFiltering");
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                Log.d("RCVAdapter", "publishResults");
                notifyDataSetChanged();
            }
        };
    }

    List<Student> getFilterResults (String section) {
        for (Student student:originalList) {
            if (student.sectionName.equals(section)) {
                selected.add(student);
            } else {
                selected.remove(student);
            }
        }



        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public StudentSelectableLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StudentSelectableLayoutBinding.bind(itemView);
        }
    }

    public interface NotifyCheckedChange {
        void notifyChange(int index);
    }
}
