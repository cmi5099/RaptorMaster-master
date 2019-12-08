package org.tensorflow.lite.examples.classification.model;

import androidx.annotation.NonNull;

/**
 * POJO class for DataSensor
 */
public class SensorDataObject {

    // match with service now column names
    float u_ambient_light;
    float u_x_cord;
    float u_y_cord;
    float u_z_cord;
    float u_temperature;
    float u_pressure;
    float u_humidity;
    float u_battery_level;
    float u_compass;
    String u_compass_direction;


    public float getCompass() { return u_compass;}

    public String getU_compass_direction() {
        return u_compass_direction;
    }

    public void setU_compass_direction(float compassValue) {
        String cardinalOrdinalDirection = "Unavailable";
        float degree = compassValue;

        if (degree >= 350 || degree <= 10)
            cardinalOrdinalDirection = "N";
        if (degree < 350 && degree > 280)
            cardinalOrdinalDirection = "NW";
        if (degree <= 280 && degree > 260)
            cardinalOrdinalDirection = "W";
        if (degree <= 260 && degree > 190)
            cardinalOrdinalDirection = "SW";
        if (degree <= 190 && degree > 170)
            cardinalOrdinalDirection = "S";
        if (degree <= 170 && degree > 100)
            cardinalOrdinalDirection = "SE";
        if (degree <= 100 && degree > 80)
            cardinalOrdinalDirection = "E";
        if (degree <= 80 && degree > 10)
            cardinalOrdinalDirection = "NE";
        this.u_compass_direction = cardinalOrdinalDirection;
    }

    public void setCompass(float compass) { this.u_compass= compass; }

    public float getLight() {
        return u_ambient_light;
    }

    public void setLight(float light) {
        this.u_ambient_light= light;
    }

    public float getAccx() {
        return u_x_cord;
    }

    public void setAccx(float accx) {
        this.u_x_cord = accx;
    }

    public float getAccy() {
        return u_y_cord;
    }

    public void setAccy(float accy) {
        this.u_y_cord = accy;
    }

    public float getAccz() {
        return u_z_cord;
    }

    public void setAccz(float accz) {
        this.u_z_cord = accz;
    }

    public float getAmbient_temp() {
        return u_temperature;
    }

    public void setAmbient_temp(float ambient_temp) {
        this.u_temperature = ambient_temp;
    }

    public void setPressure(float pressure) {
        this.u_pressure = pressure;
    }

    public float getPressure() {
        return u_pressure;
    }

    public void setRelativeHumidity(float relative_humidity) {
        this.u_humidity = relative_humidity;
    }

    public float getRelativeHumidity() {
        return u_humidity;
    }

    public float getU_battery_level() {
        return u_battery_level;
    }

    public void setU_battery_level(float u_battery_level) {
        this.u_battery_level = u_battery_level;
    }

    @NonNull
    @Override
    public String toString() {
        return "Light:"+u_ambient_light
                +", accX:"+u_x_cord
                +", accY:"+u_y_cord
                +", accZ:"+u_z_cord
                +", Ambient Temperature:"+u_temperature
                +", Pressure:"+u_pressure
                +", Relative Humidity"+u_humidity
                +", battery :"+u_battery_level
                +", Compass: "+u_compass;

    }


}
