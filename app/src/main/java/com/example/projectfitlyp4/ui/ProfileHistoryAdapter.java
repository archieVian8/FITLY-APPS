package com.example.projectfitlyp4.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfitlyp4.database.ProfileHistory;
import com.example.projectfitlyp4.databinding.ItemProfileHistoryBinding;
import com.example.projectfitlyp4.helper.ProfileHistoryDiffCallback;

import java.util.ArrayList;
import java.util.List;


public class ProfileHistoryAdapter extends
        RecyclerView.Adapter<ProfileHistoryAdapter.NoteViewHolder> {
    private final ArrayList<ProfileHistory> listNotes = new ArrayList<>();
    void setListNotes(List<ProfileHistory> listNotes) {
        final ProfileHistoryDiffCallback diffCallback = new
                ProfileHistoryDiffCallback(this.listNotes, listNotes);
        final DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(diffCallback);
        this.listNotes.clear();
        this.listNotes.addAll(listNotes);
        diffResult.dispatchUpdatesTo(this);
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProfileHistoryBinding binding =
                ItemProfileHistoryBinding.inflate(LayoutInflater.from(parent.getContext()
                ), parent, false);
        return new NoteViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder
                                         holder, int position) {
        holder.bind(listNotes.get(position));
    }
    @Override
    public int getItemCount() {
        return listNotes.size();
    }
    static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        final ItemProfileHistoryBinding binding;
        NoteViewHolder(ItemProfileHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ProfileHistory note) {
            binding.tvItemTitle.setText(note.getFromName());
            binding.tvItemDate.setText(note.getDate());
            binding.tvItemDescription.setText(note.getToName());
            binding.cvItemNote.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(),
                        ProfileEditActivity.class);
                intent.putExtra(ProfileEditActivity.EXTRA_NOTE, note);
                v.getContext().startActivity(intent);
            });
        }
    }
}