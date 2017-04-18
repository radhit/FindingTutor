package com.manajemeninformasi.riska.findingtutor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.manajemeninformasi.riska.findingtutor.R;
import com.manajemeninformasi.riska.findingtutor.RatingActivity;
import com.manajemeninformasi.riska.findingtutor.TransaksiMuridActivity;
import com.manajemeninformasi.riska.findingtutor.model.ListTransaksiMuridData;

import java.util.List;

/**
 * Created by Isja on 18/04/2017.
 */

public class ListTransaksiMuridAdapter extends ArrayAdapter<ListTransaksiMuridData> {
    List<ListTransaksiMuridData> dataTransaksi;
    Context contextTransaksi;

    public ListTransaksiMuridAdapter(Context context, int resource, List<ListTransaksiMuridData> objects) {
        super(context, resource, objects);
        this.dataTransaksi = objects;
        this.contextTransaksi = context;
    }
    private static class viewHolder
    {
        TextView pelajaran;
        TextView tutor;
        Button detil;
    }
    public View getView(final int position, View ConvertView, ViewGroup parent)
    {
        final ListTransaksiMuridData dataTransaksi = getItem(position);
        viewHolder viewTransaksi;

        if(ConvertView == null)
        {
            viewTransaksi = new viewHolder();
            ConvertView = LayoutInflater.from(getContext()).inflate(R.layout.design_list_transaksi_murid,parent,false);
            viewTransaksi.pelajaran = (TextView) ConvertView.findViewById(R.id.tvpelajaran);
            viewTransaksi.tutor = (TextView) ConvertView.findViewById(R.id.tvtutor);
            viewTransaksi.detil= (Button) ConvertView.findViewById(R.id.btndetil);
            ConvertView.setTag(viewTransaksi);
        }
        else {
            viewTransaksi = (viewHolder) ConvertView.getTag();
        }
        viewTransaksi.pelajaran.setText(dataTransaksi.getPelajaran());
        viewTransaksi.tutor.setText(dataTransaksi.getTutor());
        viewTransaksi.detil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contextTransaksi, TransaksiMuridActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",dataTransaksi.getId_transaksi());
                intent.putExtra("bundle",bundle);
                contextTransaksi.startActivity(intent);
            }
        });

        return ConvertView;

    }
}
