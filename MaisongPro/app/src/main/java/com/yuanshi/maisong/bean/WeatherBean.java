package com.yuanshi.maisong.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Dengbocheng on 2017/10/25.
 */

public class WeatherBean {
    private String icon;//天气图标：clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day,partly-cloudy-night，hail， thunderstorm，tornado
    private String summary;//天气描述
    private Long sunriseTime;//日出时间
    private Long sunsetTime;//日落时间
    private Double temperatureMin;//最低气温
    private Double temperatureMax;//最高气温
    private String localCity;

    public String getLocalCity() {
        return localCity;
    }

    public void setLocalCity(String localCity) {
        this.localCity = localCity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(Long sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public Long getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(Long sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }
}
