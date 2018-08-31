package maddie.dolo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

@Entity
public class DayOfWeather {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="dt_text")
    private String date;

    @ColumnInfo
    @TypeConverters(Converters.class)
    private Main main;

    public DayOfWeather(int id, String date, Main main) {
        this.id = id;
        this.date = date;
        this.main = main;
    }

    public int getId() { return id; }

    public String getDate() { return date; }

    public Main getMain() { return main; }

    public void setId(int id) { this.id = id; }

    public void setDate(String date) { this.date = date; }

    public void setMain(Main main) { this.main = main; }

}
