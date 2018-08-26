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
    void insertWeather(List<WeatherModel> weather);

    @Query("SELECT * FROM weathermodel")
    LiveData<List<WeatherModel>> getWeather();

    @Query("SELECT * FROM weathermodel WHERE date = :date")
    LiveData<WeatherModel> getWeatherByDate(int date);

}
