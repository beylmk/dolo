package maddie.dolo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "weather")
public class Weather {

    @PrimaryKey
    private int id;

    @ColumnInfo
    private int date;

    @ColumnInfo
    private long temp_min;

    @ColumnInfo
    private long temp_max;

    @ColumnInfo
    private String main;

    @ColumnInfo
    private String description;

    public Weather(int id, int date, long temp_min, long temp_max, String main, String description){
        this.id = id;
        this.date = date;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.main = main;
        this.description = description;
    }

    public int getId() { return id; }

    public int getDate() { return date; }

    public long getTempMin() { return temp_min; }

    public long getTempMax() { return temp_max; }

    public String getMain() { return main; }

    public String getDescription() { return description; }


}
