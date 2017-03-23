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

import com.manajemeninformasi.riska.findingtutor.DetilMuridActivity;
import com.manajemeninformasi.riska.findingtutor.R;
import com.manajemeninformasi.riska.findingtutor.model.CariMuridData;
import com.manajemeninformasi.riska.findingtutor.model.KeahlianTutorData;

import java.util.List;

/**
 * Created by Isja on 08/03/2017.
 */

public class CariMuridAdapter extends ArrayAdapter<CariMuridData> {
    List<CariMuridData> dataMurid;
    Context contextMurid;

    public CariMuridAdapter(Context context,  int resource, List<CariMuridData> objects) {
        super(context, resource, objects);
        this.dataMurid = objects;
        this.contextMurid = context;
    }
    private static class viewHolder
    {
        TextView nama;
        TextView pelajaran;
        TextView jarak;
    }
    public View getView(final int position, View ConvertView, ViewGroup parent)
    {
        final CariMuridData dataMurid = getItem(position);
        CariMuridAdapter.viewHolder viewMurid;

        if(ConvertView == null)
        {
            viewMurid = new CariMuridAdapter.viewHolder();
            ConvertView = LayoutInflater.from(getContext()).inflate(R.layout.design_cari_murid,parent,false);
            viewMurid.nama= (TextView) ConvertView.findViewById(R.id.tvnama);
            viewMurid.pelajaran = (TextView) ConvertView.findViewById(R.id.tvpelajaran);
            viewMurid.jarak = (TextView) ConvertView.findViewById(R.id.tvbiaya);
            ConvertView.setTag(viewMurid);

            Button detil = (Button) ConvertView.findViewById(R.id.btndetil);
            detil.setTag(position);
            detil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detilMurid =  new Intent(contextMurid,DetilMuridActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nama", dataMurid.getNameuser_pencarian());
                    bundle.putString("kelas", dataMurid.getKelas_pencarian());
                    bundle.putString("pelajaran", dataMurid.getPelajaran_pencarian());
                    bundle.putString("alamat", dataMurid.getAlamat_pencarian());
                    bundle.putString("tanggal", dataMurid.getTanggal_pencarian());
                    bundle.putString("hari", dataMurid.getHari_pencarian());
                    bundle.putString("jam", dataMurid.getJam_pencarian());
                    bundle.putString("biaya", dataMurid.getBiaya_pencarian());
                    bundle.putFloat("jarak", dataMurid.getJarak_pencarian());
                    detilMurid.putExtra("bundle",bundle);
                    contextMurid.startActivity(detilMurid);
                }
            });
        }
        else {
            viewMurid = (CariMuridAdapter.viewHolder) ConvertView.getTag();
        }
        viewMurid.nama.setText(dataMurid.getNameuser_pencarian());
        viewMurid.pelajaran.setText(dataMurid.getPelajaran_pencarian());
        viewMurid.jarak.setText(dataMurid.getJarak_pencarian().toString()+" Km");
        return ConvertView;
    }

}
