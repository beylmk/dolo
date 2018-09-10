package maddie.dolo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import maddie.dolo.R;
import maddie.dolo.data.DayOfWeather;
import maddie.dolo.data.WeatherObject;
import maddie.dolo.data.WeatherViewModel;

public class WeatherActivity extends BaseActivity {

    private static final String BASE_IMAGE_URL = "http://openweathermap.org/img/w/";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private WeatherViewModel weatherViewModel;

    private ImageView todaysWeatherIcon;

    private DayOfWeather todaysWeather;

    private TextView mainDescriptionTextView;

    private TextView secondaryDescriptionTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        todaysWeatherIcon = findViewById(R.id.todays_weather_icon);
        mainDescriptionTextView = findViewById(R.id.main_description_text_view);
        secondaryDescriptionTextView = findViewById(R.id.secondary_description_text_view);

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
                    Log.e("in dayOfWeather", dayOfWeather.toString());
                }
            }
        });
    }

    private void setUpTodaysWeather() {
        if (!todaysWeather.getWeather().isEmpty()) {

            WeatherObject todaysWeatherObject = todaysWeather.getWeather().get(0);

            Glide.with(this)
                    .load(BASE_IMAGE_URL + todaysWeatherObject.getIcon() + ".png")
                    .into(todaysWeatherIcon);

            mainDescriptionTextView.setText(todaysWeatherObject.getMain());
            secondaryDescriptionTextView.setText(todaysWeatherObject.getDescription());
        }

        if (todaysWeather.getMain() != null) {

        }

    }

}
