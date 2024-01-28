package com.practiceapp.roomdb.viewHolder;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.practiceapp.roomdb.deligate.DeleteWordListener;
import com.practiceapp.roomdb.editDeligate.EditWordListener;
import com.practiceapp.roomdb.table.Word;

public class WordListAdapter extends ListAdapter<Word,WordViewHolder> {

    private DeleteWordListener deleteWordListener;
    private EditWordListener editWordListener;

    public WordListAdapter(@NonNull DiffUtil.ItemCallback<Word> diffCallback, DeleteWordListener deleteWordListener){
        super(diffCallback);

        this.deleteWordListener=deleteWordListener;
        this.editWordListener=editWordListener;
    }


    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return WordViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Word current= getItem(position);
        holder.bind(current.getWord());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWordListener.onDeleteWord(current);

            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWordListener.onEditWord(current);

            }
        });

    }

    public static class WordDiff extends DiffUtil.ItemCallback<Word>{

        @Override
        public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem==newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem.getWord().equals(newItem.getWord());
        }
    }

}
