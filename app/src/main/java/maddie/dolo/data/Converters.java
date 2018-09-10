package maddie.dolo.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromMain(Main main) {
        if (main == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Main>() {
        }.getType();
        return gson.toJson(main, type);
    }

    @TypeConverter
    public Main toMain(String mainString) {
        if (mainString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Main>() {
        }.getType();
        return gson.fromJson(mainString, type);
    }

    @TypeConverter
    public String fromWeatherObject(List<WeatherObject> weatherObject) {
        if (weatherObject == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<WeatherObject>>() {
        }.getType();
        return gson.toJson(weatherObject, type);
    }

    @TypeConverter
    public List<WeatherObject> toWeatherObject(String weatherObjectString) {
        if (weatherObjectString == null) {
            return (null);
        }
        Gson gson = new Gson();

        Type type = new TypeToken<List<WeatherObject>>() {}.getType();
        return gson.fromJson(weatherObjectString, type);
    }

//    @TypeConverter
//    public Date toDate(long dateText) {
//        return new Date(dateText*1000L);
//    }
//
//    @TypeConverter
//    public long toLong(Date date) {
//        return date.getTime() / 1000;
//    }
}