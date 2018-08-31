package maddie.dolo.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface WeatherDao {

    @Insert(onConflict = REPLACE)
    void insertWeather(List<DayOfWeather> dayOfWeather);

    @Query("SELECT * FROM DayOfWeather")
    public LiveData<List<DayOfWeather>> getWeather();

    @Query("SELECT * FROM dayofweather WHERE dt_text = :date")
    public LiveData<DayOfWeather> getWeatherByDate(String date);

}
