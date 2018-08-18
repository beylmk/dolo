package maddie.dolo.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface WeatherDao {


    @Insert(onConflict = REPLACE)
    public long[] insertWeather(Weather... weather);


    @Query("SELECT * FROM weather")
    public Weather[] loadAllWeather();

    @Query("SELECT * FROM weather WHERE date = :date")
    public Weather getWeatherByDate(int date);


}
