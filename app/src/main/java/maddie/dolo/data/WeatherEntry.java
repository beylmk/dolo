package maddie.dolo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

@Entity
public class WeatherEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private long dt;

    @ColumnInfo
    @TypeConverters(Converters.class)
    private Main main;

    @ColumnInfo
    @TypeConverters(Converters.class)
    private List<WeatherObject> weather;

    public WeatherEntry(int id, long dt, Main main) {
        this.id = id;
        this.dt = dt;
        this.main = main;
    }

    public int getId() { return id; }

    public long getDt() { return dt; }

    public Main getMain() { return main; }

    public void setId(int id) { this.id = id; }

    public void setDt(long date) { this.dt = date; }

    public void setMain(Main main) { this.main = main; }

    public List<WeatherObject> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherObject> weatherObject) {
        this.weather = weatherObject;
    }

}
