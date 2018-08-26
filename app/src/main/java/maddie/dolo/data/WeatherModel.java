package maddie.dolo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WeatherModel {

    @PrimaryKey
    private int id;

    @ColumnInfo
    private int date;

    @ColumnInfo(name = "temp_min")
    private long tempMin;

    @ColumnInfo(name = "temp_max")
    private long tempMax;

    @ColumnInfo
    private String main;

    @ColumnInfo
    private String description;

    public WeatherModel(int id, int date, long tempMin, long tempMax, String main, String description) {
        this.id = id;
        this.date = date;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.main = main;
        this.description = description;
    }

    public int getId() { return id; }

    public int getDate() { return date; }

    public long getTempMin() { return tempMin; }

    public long getTempMax() { return tempMax; }

    public String getMain() { return main; }

    public String getDescription() { return description; }


}
