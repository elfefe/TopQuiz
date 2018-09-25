package com.topquiz.elfefe.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.topquiz.elfefe.R;
import com.topquiz.elfefe.model.Ranks;

import java.util.List;


public class RankAdapter extends ArrayAdapter<Ranks> {

    public RankAdapter(@NonNull Context context, List<Ranks> ranks) {
        super(context,0,ranks);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_rank,parent,false);
        }

        RankViewHolder viewHolder = (RankViewHolder) convertView.getTag();
        if (viewHolder == null){
            viewHolder = new RankViewHolder();
            viewHolder.player = convertView.findViewById(R.id.rank_player);
            viewHolder.score = convertView.findViewById(R.id.rank_score);
            convertView.setTag(viewHolder);
        }

        Ranks rank = getItem(position);

        assert rank != null;
        viewHolder.player.setText(rank.getPlayer());
        viewHolder.score.setText(String.valueOf(rank.getScore()));

        return convertView;

    }
    private class RankViewHolder{
        TextView player,score;
    }
}
