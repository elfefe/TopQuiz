package com.topquiz.elfefe.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.topquiz.elfefe.R;
import com.topquiz.elfefe.model.Ranks;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class RankAdapter extends BaseAdapter {

    Map ranks;
    LayoutInflater inflater;

    public RankAdapter(@NonNull Context context, Map ranks) {
        super();
        inflater = LayoutInflater.from(context);
        this.ranks = ranks;
    }

    @Override
    public int getCount() {
        return ranks.size();
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
