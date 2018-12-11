package com.example.user.myakademik.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.myakademik.R;
import com.example.user.myakademik.model.Mahasiswa;

import java.util.List;

/**
 * Created by asus on 11/21/2018.
 */

public class AdapterDosen extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Mahasiswa> items;

    public AdapterDosen(Activity activity, List<Mahasiswa> items){
        this.activity = activity;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_dosen ,null);

        TextView nik = (TextView) convertView.findViewById(R.id.nik);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat);

        Mahasiswa data = items.get(position);

        nik.setText(data.getNik());
        nama.setText(data.getNama());
        alamat.setText(data.getAlamat());


        return convertView;
    }
}