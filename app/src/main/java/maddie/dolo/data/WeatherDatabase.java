package maddie.dolo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(version = 1, entities = {Weather.class})
abstract public class WeatherDatabase extends RoomDatabase {
    abstract public WeatherDao weatherDao();
}
