package maddie.dolo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(version = 1, entities = {WeatherEntry.class})
@TypeConverters({Converters.class})
public abstract class WeatherDatabase extends RoomDatabase {

    private static volatile WeatherDatabase INSTANCE;

    public abstract WeatherEntryDao weatherDao();

}
