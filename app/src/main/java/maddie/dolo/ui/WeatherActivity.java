package maddie.dolo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import maddie.dolo.R;
import maddie.dolo.data.WeatherModel;
import maddie.dolo.data.WeatherViewModel;

public class WeatherActivity extends BaseActivity {

    private WeatherViewModel weatherViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        getWeatherData();
    }

    public void getWeatherData() {
        weatherViewModel.getWeatherList().observe(WeatherActivity.this, new Observer<List<WeatherModel>>() {
            @Override
            public void onChanged(@Nullable List<WeatherModel> weather) {
            }
        });
    }
}
