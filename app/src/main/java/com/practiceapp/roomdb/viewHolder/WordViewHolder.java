package com.practiceapp.roomdb.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.practiceapp.roomdb.R;

public class WordViewHolder extends RecyclerView.ViewHolder {
    private final TextView wordItemView;
    final ImageView imgDelete;
    final ImageView imgEdit;

    private WordViewHolder(View itemView){
        super(itemView);
        wordItemView=itemView.findViewById(R.id.textView);
        imgDelete=itemView.findViewById(R.id.imgDelete);
        imgEdit=itemView.findViewById(R.id.imgEdit);
    }

    public void bind(String text){
        wordItemView.setText(text);
    }

    static WordViewHolder create(ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item,parent,false);
        return new WordViewHolder(view);
    }


}
