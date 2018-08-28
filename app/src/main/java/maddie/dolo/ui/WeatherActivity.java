package maddie.dolo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import maddie.dolo.R;
import maddie.dolo.data.Weather;
import maddie.dolo.data.WeatherViewModel;

public class WeatherActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private WeatherViewModel weatherViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        AndroidInjection.inject(this);

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
        weatherViewModel.init();
        getWeatherData();

    }

    public void getWeatherData() {
        weatherViewModel.getWeather().observe(WeatherActivity.this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(@Nullable List<Weather> weather) {
                if (weather != null && weather.size() > 0) {
                    Log.e("in weather activity", weather.get(0).getDescription());
                }
            }
        });
    }

}
