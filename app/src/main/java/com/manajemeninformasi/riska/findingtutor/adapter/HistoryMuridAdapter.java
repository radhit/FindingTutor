package com.manajemeninformasi.riska.findingtutor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.manajemeninformasi.riska.findingtutor.DetilMuridActivity;
import com.manajemeninformasi.riska.findingtutor.GeneratorActivity;
import com.manajemeninformasi.riska.findingtutor.R;
import com.manajemeninformasi.riska.findingtutor.RatingActivity;
import com.manajemeninformasi.riska.findingtutor.model.HistoryMuridData;
import com.manajemeninformasi.riska.findingtutor.model.KeahlianTutorData;

import java.util.List;

/**
 * Created by Isja on 31/03/2017.
 */

public class HistoryMuridAdapter extends ArrayAdapter<HistoryMuridData>{
    List<HistoryMuridData> historyData;
    Context contextHistory;


    public HistoryMuridAdapter(Context context, int resource, List<HistoryMuridData> objects) {
        super(context, resource, objects);
        this.historyData = objects;
        this.contextHistory = context;
    }
    private static class viewHolder
    {
        TextView tanggal;
        TextView pelajaran;
        TextView rating;
        Button btnrating;
    }
    public View getView(final int position, View ConvertView, ViewGroup parent)
    {
        final HistoryMuridData dataMurid = getItem(position);
        Log.d("lala",dataMurid.getTanggal());
        viewHolder viewHistory;

        if (ConvertView == null)
        {
            viewHistory = new viewHolder();
            ConvertView = LayoutInflater.from(getContext()).inflate(R.layout.design_history_murid,parent,false);
            viewHistory.tanggal = (TextView) ConvertView.findViewById(R.id.tvtanggal);
            viewHistory.pelajaran = (TextView) ConvertView.findViewById(R.id.tvpelajaran);
            viewHistory.rating = (TextView) ConvertView.findViewById(R.id.tvrating);

            viewHistory.btnrating = (Button) ConvertView.findViewById(R.id.btnrating);
            ConvertView.setTag(viewHistory);

        }
        else {
            viewHistory = (HistoryMuridAdapter.viewHolder) ConvertView.getTag();
        }
        viewHistory.pelajaran.setText(dataMurid.getPelajaran());
        viewHistory.tanggal.setText(dataMurid.getTanggal());
        viewHistory.rating.setText(dataMurid.getRating());

        viewHistory.btnrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contextHistory, RatingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",dataMurid.getId_history());
                bundle.putString("nama_tutor", dataMurid.getNama_tutor());
                bundle.putString("pelajaran", dataMurid.getPelajaran());
                bundle.putString("tanggal", dataMurid.getTanggal());
                bundle.putString("biaya", dataMurid.getBiaya());
                bundle.putString("komentar",dataMurid.getKomentar());
                intent.putExtra("bundle",bundle);
                contextHistory.startActivity(intent);
            }
        });

        return ConvertView;
    }
}
