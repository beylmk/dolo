package maddie.dolo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1, entities = {Weather.class})
public abstract class WeatherDatabase extends RoomDatabase {

    private static volatile WeatherDatabase INSTANCE;

    public abstract WeatherDao weatherDao();

}
