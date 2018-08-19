package maddie.dolo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1, entities = {WeatherModel.class})
public abstract class WeatherDatabase extends RoomDatabase {

    private static WeatherDatabase INSTANCE;

    public abstract WeatherDao weatherDao();

    public static WeatherDatabase getWeatherDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class,
                    "weather").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
