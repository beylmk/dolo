package maddie.dolo.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface WeatherEntryDao {

    @Insert(onConflict = REPLACE)
    void insertWeather(List<WeatherEntry> weatherEntry);

    @Query("SELECT * FROM WeatherEntry")
    public LiveData<List<WeatherEntry>> getWeather();

    @Query("SELECT * FROM WeatherEntry WHERE dt = :date")
    public LiveData<WeatherEntry> getWeatherByDate(long date);

}
