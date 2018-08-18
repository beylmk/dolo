package maddie.dolo.ui;

import android.arch.persistence.room.Room;
import android.os.Bundle;

import maddie.dolo.R;
import maddie.dolo.data.WeatherDatabase;
import maddie.dolo.ui.BaseActivity;

public class WeatherActivity extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        getWeatherData();
    }

    public void getWeatherData() {
        WeatherDatabase weatherDatabase = Room.databaseBuilder(this, WeatherDatabase.class, "weather").build();
    }
}
