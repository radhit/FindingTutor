package com.manajemeninformasi.riska.findingtutor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.manajemeninformasi.riska.findingtutor.R;
import com.manajemeninformasi.riska.findingtutor.RatingActivity;
import com.manajemeninformasi.riska.findingtutor.model.HistoryMuridData;
import com.manajemeninformasi.riska.findingtutor.model.HistoryTutorData;

import java.util.List;

/**
 * Created by Isja on 31/03/2017.
 */

public class HistoryTutorAdapter extends ArrayAdapter<HistoryTutorData> {
    List<HistoryTutorData> data;
    Context context;

    public HistoryTutorAdapter(Context context, int resource, List<HistoryTutorData> objects) {
        super(context, resource, objects);
        this.data = objects;
        this.context = context;
    }
    private static class viewHolder
    {
        TextView tanggal;
        TextView rating;
        TextView komentar;
    }
    public View getView(final int position, View ConvertView, ViewGroup parent)
    {
        final HistoryTutorData dataTutor = getItem(position);
        Log.d("lala",dataTutor.getTanggal());
        viewHolder viewHistory;

        if (ConvertView == null)
        {
            viewHistory = new viewHolder();
            ConvertView = LayoutInflater.from(getContext()).inflate(R.layout.design_history_tutor,parent,false);
            viewHistory.tanggal = (TextView) ConvertView.findViewById(R.id.tvtanggal);
            viewHistory.rating = (TextView) ConvertView.findViewById(R.id.tvrating);
            viewHistory.komentar = (TextView) ConvertView.findViewById(R.id.tvkomentar);
            ConvertView.setTag(viewHistory);

        }
        else {
            viewHistory = (HistoryTutorAdapter.viewHolder) ConvertView.getTag();
        }
        viewHistory.tanggal.setText(dataTutor.getTanggal());
        viewHistory.rating.setText(dataTutor.getRating());
        viewHistory.komentar.setText(dataTutor.getKomentar());

        return ConvertView;
    }
}
