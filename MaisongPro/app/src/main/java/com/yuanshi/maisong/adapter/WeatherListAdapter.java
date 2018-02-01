package com.yuanshi.maisong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desmond.citypicker.callback.IOnCityPickerCheckedCallBack;
import com.google.gson.Gson;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.WeatherBean;
import com.yuanshi.maisong.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dengbocheng on 2017/12/1.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;
    private List<WeatherBean> mData;
    private Context context;
    private String localCity;
    private IOnCityPickerCheckedCallBack onCityPickerCheckedCallBack;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public List<WeatherBean> getmData() {
        return mData;
    }

    public void setmData(List<WeatherBean> mData) {
        this.mData = mData;
    }

    public WeatherListAdapter(ArrayList<WeatherBean> data, Context context, String localCity,IOnCityPickerCheckedCallBack iOnCityPickerCheckedCallBack) {
        this.context = context;
        this.mData = data;
        this.localCity = localCity;
        this.onCityPickerCheckedCallBack = iOnCityPickerCheckedCallBack;
    }

    public void updateData(List<WeatherBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public String getLocalCity() {
        return localCity;
    }

    public void setLocalCity(String localCity) {
        this.localCity = localCity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        WeatherBean weatherBean = mData.get(position);
        YLog.e("adapter--->"+weatherBean.getSummary());
        holder.temperatureText.setText(String.format(context.getString(R.string.tempature_format), weatherBean.getTemperatureMax(), weatherBean.getTemperatureMin()).toString());
        holder.sunriseTime.setText(Utils.getDateTimeFromMillisecond(weatherBean.getSunriseTime()));
        holder.sunsetTime.setText(Utils.getDateTimeFromMillisecond(weatherBean.getSunsetTime()));
        YLog.e(new Gson().toJson(weatherBean));

        if(position != 0){
            holder.weatherText.setText(Utils.getCurrentDate(weatherBean.getSunsetTime())+"    "+weatherBean.getSummary());
            holder.sunriseLayout.setVisibility(View.GONE);
            holder.cityTv.setVisibility(View.GONE);
        }else{
            holder.weatherText.setText(Utils.getCurrentDate(weatherBean.getSunsetTime()));
            holder.sunriseLayout.setVisibility(View.VISIBLE);
            YLog.e("adapter--->"+localCity);
            if(!TextUtils.isEmpty(localCity)){
                holder.cityTv.setText(localCity);
                holder.cityTv.setVisibility(View.VISIBLE);
            }
        }
        switch (weatherBean.getIcon()) {
            case "clear-day"://晴天
                holder.weatherIcon.setImageResource(R.mipmap.weather_sunny);
                break;
            case "clear-night"://晴夜
                holder.weatherIcon.setImageResource(R.mipmap.weather_night);
                break;
            case "rain"://雨
                holder.weatherIcon.setImageResource(R.mipmap.weather_rain);
                break;
            case "snow"://雪
                holder.weatherIcon.setImageResource(R.mipmap.weather_snow);
                break;
            case "sleet"://雨夹雪
                holder.weatherIcon.setImageResource(R.mipmap.weather_sleet);
                break;
            case "wind"://风
                holder.weatherIcon.setImageResource(R.mipmap.weather_wind);
                break;
            case "fog"://雾
                holder.weatherIcon.setImageResource(R.mipmap.weather_fog);
                break;
            case "cloudy"://多云
                holder.weatherIcon.setImageResource(R.mipmap.weather_cloudy);
                break;
            case "partly-cloudy-day"://晴转多云日
                holder.weatherIcon.setImageResource(R.mipmap.weather_partly_cloudy);
                break;
            case "partly-cloudy-night":
                holder.weatherIcon.setImageResource(R.mipmap.weather_partly_cloudy_night);
                break;
            case "hail"://冰雹
                holder.weatherIcon.setImageResource(R.mipmap.weather_sleet);
                break;
            case "thunderstorm"://雷雨
                holder.weatherIcon.setImageResource(R.mipmap.weather_thunderstorm);
                break;
            case "tornado"://龙卷风
                holder.weatherIcon.setImageResource(R.mipmap.weather_wind);
                break;
            default:
                holder.weatherIcon.setImageResource(R.mipmap.weather_umbrellablack);
                break;
        }
        holder.cityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Utils.startCityPicker(context,onCityPickerCheckedCallBack);
            }
        });
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemLongClickListener.onItemLongClick(view,(int)view.getTag());
        }
        return true;
    }


    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public static interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView temperatureText;
        TextView sunriseTime;
        TextView sunsetTime,weatherText;
        ImageView weatherIcon;
        LinearLayout sunriseLayout;
        TextView cityTv;
        public ViewHolder(View itemView) {
            super(itemView);
            temperatureText = itemView.findViewById(R.id.temperature_text);
            sunriseTime = itemView.findViewById(R.id.sunriseTime);
            sunsetTime = itemView.findViewById(R.id.sunsetTime);
            weatherText = itemView.findViewById(R.id.weather_text);
            weatherIcon = itemView.findViewById(R.id.weather_icon);
            sunriseLayout = itemView.findViewById(R.id.sunriseLayout);
            cityTv = itemView.findViewById(R.id.city_tv);
        }
    }
}
