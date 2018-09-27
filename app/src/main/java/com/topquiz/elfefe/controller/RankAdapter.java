package com.topquiz.elfefe.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.topquiz.elfefe.R;

import java.util.ArrayList;
import java.util.Map;


public class RankAdapter extends BaseAdapter {

    private Map ranks;
    private LayoutInflater inflater;

    RankAdapter(@NonNull Context context, Map ranks) {
        super();
        inflater = LayoutInflater.from(context);
        this.ranks = ranks;
    }

    @Override
    public int getCount() {
        if (ranks.size() < 5)
            return ranks.size();
        else
            return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        if (convertView == null){
            convertView = inflater.inflate(R.layout.view_rank,null);
        }

        RankViewHolder viewHolder = (RankViewHolder) convertView.getTag();

        if (viewHolder == null){
            viewHolder = new RankViewHolder();
            viewHolder.player = convertView.findViewById(R.id.rank_player);
            viewHolder.score = convertView.findViewById(R.id.rank_score);
            convertView.setTag(viewHolder);
        }


        ArrayList<String> listePlayer = new ArrayList<>(ranks.keySet());
        ArrayList<String> listeScore = new ArrayList<>(ranks.values());


        viewHolder.player.setText(listePlayer.get(position));
        viewHolder.score.setText(String.valueOf(listeScore.get(position)));

        return convertView;

    }
    private class RankViewHolder{
        TextView player,score;
    }
}
