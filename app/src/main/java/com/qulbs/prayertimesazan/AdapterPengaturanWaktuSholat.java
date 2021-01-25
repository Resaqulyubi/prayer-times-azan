package com.qulbs.prayertimesazan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.qulbs.prayertimesazan.model.db.SholatWajibDB;

/**
 * Created by KLOPOS on 9/25/2017.
 */

public class AdapterPengaturanWaktuSholat extends BaseAdapter {

    private Context mContext;
    private List<SholatWajibDB> mList = new ArrayList<>();


    public AdapterPengaturanWaktuSholat(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public SholatWajibDB getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_pengaturan_waktu, null);

        TextView tv_sholat_name =  v.findViewById(R.id.tv_sholat_name);
        TextView tv_sholat_time =  v.findViewById(R.id.tv_sholat_time);
        LinearLayout lnly_sholat_sunnah =  v.findViewById(R.id.lnly_sholat_sunnah);

        ImageView imgv_alarm =  v.findViewById(R.id.imgv_alarm);


        SholatWajibDB data = mList.get(i);

        tv_sholat_name.setText(data.getNama());
        tv_sholat_time.setText(data.getWaktu());

        if (data.getAktif().equals("1")){

            imgv_alarm.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_alarm_on_indigo_a200_24dp));;
        }else {
            imgv_alarm.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_alarm_off_indigo_a200_24dp));;
        }
        return v;
    }

    public void setList(List<SholatWajibDB> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
