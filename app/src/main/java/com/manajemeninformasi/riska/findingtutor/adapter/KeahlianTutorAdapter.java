package com.manajemeninformasi.riska.findingtutor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.manajemeninformasi.riska.findingtutor.R;
import com.manajemeninformasi.riska.findingtutor.model.KeahlianTutorData;

import java.util.List;

/**
 * Created by Isja on 07/03/2017.
 */

public class KeahlianTutorAdapter extends ArrayAdapter<KeahlianTutorData> {
    List<KeahlianTutorData> dataKeahlian;
    Context contextKeahlian;

    public KeahlianTutorAdapter(Context context, int resource, List<KeahlianTutorData> objects) {
        super(context, resource, objects);
        this.dataKeahlian = objects;
        this.contextKeahlian = context;
    }
    private static class viewHolder
    {
        TextView pelajaran;
        TextView kelas;

    }
    public View getView(final int position, View ConvertView, ViewGroup parent)
    {
        final KeahlianTutorData dataKeahlian = getItem(position);
        viewHolder viewKeahlian;

        if(ConvertView == null)
        {
            viewKeahlian = new viewHolder();
            ConvertView = LayoutInflater.from(getContext()).inflate(R.layout.design_keahlian_tutor,parent,false);
            viewKeahlian.pelajaran = (TextView) ConvertView.findViewById(R.id.tvpelajaran);
            viewKeahlian.kelas = (TextView) ConvertView.findViewById(R.id.tvkelas);
            ConvertView.setTag(viewKeahlian);

            Button delete = (Button) ConvertView.findViewById(R.id.btndelete);
            delete.setTag(position);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
        else {
            viewKeahlian = (viewHolder) ConvertView.getTag();
        }
        viewKeahlian.pelajaran.setText(dataKeahlian.getPelajaran_keahlian());
        viewKeahlian.kelas.setText(dataKeahlian.getKelas_keahlian());

        return ConvertView;

    }
}
