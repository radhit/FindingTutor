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
import com.manajemeninformasi.riska.findingtutor.R;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.Jarak;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.TingkatKesulitan;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.Waktu;
import com.manajemeninformasi.riska.findingtutor.model.CariMuridData;
import com.manajemeninformasi.riska.findingtutor.model.KeahlianTutorData;
import com.manajemeninformasi.riska.findingtutor.proses_fuzzy.Fuzzy;

import java.util.Calendar;
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
        Integer kesulitan=0;
        double hargaawal = 0;


        ////////////
        TingkatKesulitan tingkatKesulitan = new TingkatKesulitan();
        if (dataMurid.getKelas_pencarian().matches("SD - Kelas 1"))
        {
            kesulitan = 1;
        }
        else if (dataMurid.getKelas_pencarian().matches("SD - Kelas 2"))
        {
            kesulitan = 2;
        }
        else if (dataMurid.getKelas_pencarian().matches("SD - Kelas 3"))
        {
            kesulitan = 3;
        }
        else if (dataMurid.getKelas_pencarian().matches("SD - Kelas 4"))
        {
            kesulitan = 4;
        }
        else if (dataMurid.getKelas_pencarian().matches("SD - Kelas 5"))
        {
            kesulitan = 5;
        }
        else if (dataMurid.getKelas_pencarian().matches("SD - Kelas 6"))
        {
            kesulitan = 6;
        }
        else if (dataMurid.getKelas_pencarian().matches("SMP - Kelas 7"))
        {
            kesulitan = 7;
        }
        else if (dataMurid.getKelas_pencarian().matches("SMP - Kelas 8"))
        {
            kesulitan = 8;
        }
        else if (dataMurid.getKelas_pencarian().matches("SMP - Kelas 9"))
        {
            kesulitan = 9;
        }
        else if (dataMurid.getKelas_pencarian().matches("SMA - Kelas 10"))
        {
            kesulitan = 10;
        }
        else if (dataMurid.getKelas_pencarian().matches("SMA - Kelas 11"))
        {
            kesulitan = 11;
        }
        else if (dataMurid.getKelas_pencarian().matches("SMA - Kelas 12"))
        {
            kesulitan = 12;
        }
        else if (dataMurid.getKelas_pencarian().matches("UMUM"))
        {
            kesulitan = 13;
        }
        tingkatKesulitan.setTingkatKesulitan(kesulitan);
        Log.d("kesulitan", kesulitan.toString());

        int tanggal = Integer.parseInt(dataMurid.getTanggal_pencarian().split("/")[0]);
        int bulan = Integer.parseInt(dataMurid.getTanggal_pencarian().split("/")[1]);
        int tahun = Integer.parseInt(dataMurid.getTanggal_pencarian().split("/")[2]);


        Calendar today = Calendar.getInstance();

        Calendar tanggalLes = Calendar.getInstance();
        tanggalLes.set(Calendar.DAY_OF_MONTH,tanggal);
        tanggalLes.set(Calendar.MONTH,bulan-1);
        tanggalLes.set(Calendar.YEAR,tahun);

        long diff = tanggalLes.getTimeInMillis()-today.getTimeInMillis();
        long diffDay = diff/(24*60*60*1000);

        Log.d("tanggal",String.valueOf(diffDay));

        Waktu waktu = new Waktu();
        waktu.setWaktu(diffDay);


        Jarak jarak = new Jarak();
        jarak.setJarak(dataMurid.getJarak_pencarian());

        Fuzzy fuzzy = new Fuzzy();
        fuzzy.hitungX(tingkatKesulitan, waktu, jarak);
//        Log.d("tk", tingkatKesulitan.getTingkatKesulitan() + " " + waktu.getWaktu() + " " + jarak.getjarak());
        fuzzy.hitungY();

        hargaawal = fuzzy.defuzzyfikasi();
        Log.d("hargaawal", fuzzy.defuzzyfikasi() + " ");
        if(jarak.getjarak() > 5000 && jarak.getjarak() <= 10000){
            hargaawal = hargaawal + 5000;
        } else if(jarak.getjarak() > 10000 && jarak.getjarak() <= 15000){
            hargaawal = hargaawal + 10000;
        } else if(jarak.getjarak() > 15000 ){
            hargaawal = hargaawal + 20000;
        }


        ///////////////

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
            final Double finalHargaawal = hargaawal;
            detil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detilMurid =  new Intent(contextMurid,DetilMuridActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",dataMurid.getId_pencarian());
                    bundle.putString("username",dataMurid.getUsername_pencarian());
                    bundle.putString("nama", dataMurid.getNameuser_pencarian());
                    bundle.putString("kelas", dataMurid.getKelas_pencarian());
                    bundle.putString("pelajaran", dataMurid.getPelajaran_pencarian());
                    bundle.putString("alamat", dataMurid.getAlamat_pencarian());
                    bundle.putString("tanggal", dataMurid.getTanggal_pencarian());
                    bundle.putString("hari", dataMurid.getHari_pencarian());
                    bundle.putString("jam", dataMurid.getJam_pencarian());
                    if (finalHargaawal.isNaN()){
                        bundle.putString("biaya", "Transaksi Tidak Valid");
                    } else{
                        String biayaDatabase = dataMurid.getBiaya_pencarian();
                        Double hargaFix = finalHargaawal + Double.parseDouble(biayaDatabase);
                        Integer hargaakhir = hargaFix.intValue();
                        hargaakhir = hargaakhir - (hargaakhir%1000) +1000;
//                        Integer hargaakhir = hargaFix.intValue();

//                        Integer hargaakhir = finalHargaawal.intValue();
//                        hargaakhir = hargaakhir - (hargaakhir%1000) +1000;
                        bundle.putString("biaya", String.valueOf(hargaakhir));
                    }

                    bundle.putFloat("jarak", dataMurid.getJarak_pencarian());
                    bundle.putInt("durasi", dataMurid.getDurasi());
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


//        viewMurid.biaya.setText(hargaawal + " ");

        return ConvertView;
    }
}
