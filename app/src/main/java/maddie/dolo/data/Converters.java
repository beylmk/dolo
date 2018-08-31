package maddie.dolo.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Converters {

    @TypeConverter
    public String fromMain(Main main) {
        if (main == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Main>() {
        }.getType();
        String json = gson.toJson(main, type);
        return json;
    }

    @TypeConverter
    public Main toMain(String mainString) {
        if (mainString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Main>() {
        }.getType();
        Main main = gson.fromJson(mainString, type);
        return main;
    }
}