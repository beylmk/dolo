package maddie.dolo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import maddie.dolo.R;
import maddie.dolo.data.DayOfWeather;
import maddie.dolo.data.Main;
import maddie.dolo.data.WeatherObject;
import maddie.dolo.data.WeatherViewModel;

public class WeatherActivity extends BaseActivity {

    private static final String BASE_IMAGE_URL = "http://openweathermap.org/img/w/";
    public static final String PNG = ".png";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private WeatherViewModel weatherViewModel;

    private ImageView todaysWeatherIcon;

    private DayOfWeather todaysWeather;

    private TextView mainDescriptionTextView;

    private TextView secondaryDescriptionTextView;

    private TextView todaysTemperatureTextView;

    private TextView todaysHumidityTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        todaysWeatherIcon = findViewById(R.id.todays_weather_icon);
        mainDescriptionTextView = findViewById(R.id.main_description_text_view);
        secondaryDescriptionTextView = findViewById(R.id.secondary_description_text_view);
        todaysTemperatureTextView = findViewById(R.id.todays_temperature_text_view);
        todaysHumidityTextView = findViewById(R.id.todays_humidity_text_view);

        AndroidInjection.inject(this);

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
        weatherViewModel.init();
        getWeatherData();

    }

    public void getWeatherData() {
        weatherViewModel.getWeather().observe(WeatherActivity.this, new Observer<List<DayOfWeather>>() {
            @Override
            public void onChanged(@Nullable List<DayOfWeather> dayOfWeather) {
                if (dayOfWeather != null && dayOfWeather.size() > 0) {
                    todaysWeather = dayOfWeather.get(0);
                    setUpTodaysWeather();

                    setUpUpcomingWeatherByIndex(0, dayOfWeather.get(1));
                    setUpUpcomingWeatherByIndex(1, dayOfWeather.get(2));
                    setUpUpcomingWeatherByIndex(2, dayOfWeather.get(3));
                    Log.e("in dayOfWeather", dayOfWeather.toString());
                }
            }
        });
    }

    private void setUpUpcomingWeatherByIndex(int index, DayOfWeather dayOfWeather) {
        if (index < 0 || index > 3) return;
        View layout;
        switch (index) {
            case 0: layout = findViewById(R.id.upcoming_day_1);
                    break;
            case 1: layout = findViewById(R.id.upcoming_day_2);
                    break;
            default: layout = findViewById(R.id.upcoming_day_3);
        }

        ImageView weatherIcon = layout.findViewById(R.id.weather_icon);
        TextView temperatureTextView = layout.findViewById(R.id.temperature_text_view);

        if (!dayOfWeather.getWeather().isEmpty()) {
            WeatherObject weatherObject = dayOfWeather.getWeather().get(0);

            Glide.with(this)
                    .load(BASE_IMAGE_URL + weatherObject.getIcon() + PNG)
                    .into(weatherIcon);
        }

        if (dayOfWeather.getMain() != null) {
//            Math.round(todaysMainWeather.getTemp()
            temperatureTextView.setText(getString(R.string.temperature_value, Math.round(dayOfWeather.getMain().getTemp())));
        }
    }

    private void setUpTodaysWeather() {
        if (!todaysWeather.getWeather().isEmpty()) {

            WeatherObject todaysWeatherObject = todaysWeather.getWeather().get(0);

            Glide.with(this)
                    .load(BASE_IMAGE_URL + todaysWeatherObject.getIcon() + "PNG")
                    .into(todaysWeatherIcon);

            mainDescriptionTextView.setText(todaysWeatherObject.getMain());
            secondaryDescriptionTextView.setText(todaysWeatherObject.getDescription());
        }

        if (todaysWeather.getMain() != null) {
            Main todaysMainWeather = todaysWeather.getMain();
            //TODO round temperature to nearest integer
            todaysTemperatureTextView.setText(getString(R.string.temperature, Math.round(todaysMainWeather.getTemp())));
            todaysHumidityTextView.setText(getString(R.string.humidity, todaysMainWeather.getHumidity()));
        }

    }

}
