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
    public long[] insertWeather(WeatherModel... weather);

    @Query("SELECT * FROM weather")
    public LiveData<List<WeatherModel>> getWeather();

    @Query("SELECT * FROM weather WHERE date = :date")
    public LiveData<WeatherModel> getWeatherByDate(int date);

}
