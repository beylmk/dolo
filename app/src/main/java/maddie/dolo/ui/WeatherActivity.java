package maddie.dolo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import maddie.dolo.R;
import maddie.dolo.data.WeatherEntry;
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

    private WeatherEntry todaysWeather;

    private TextView mainDescriptionTextView;

    private TextView secondaryDescriptionTextView;

    private TextView todaysTemperatureTextView;

    private TextView todaysHumidityTextView;

    private ProgressBar spinner;

    private Group content;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        todaysWeatherIcon = findViewById(R.id.todays_weather_icon);
        mainDescriptionTextView = findViewById(R.id.main_description_text_view);
        secondaryDescriptionTextView = findViewById(R.id.secondary_description_text_view);
        todaysTemperatureTextView = findViewById(R.id.todays_temperature_text_view);
        todaysHumidityTextView = findViewById(R.id.todays_humidity_text_view);
        spinner = findViewById(R.id.spinner);
        content = findViewById(R.id.content);

        AndroidInjection.inject(this);

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
        weatherViewModel.init();
        toggleSpinner(true);
        getWeatherData();

    }

    private void toggleSpinner(boolean isSpinnerVisible) {
        spinner.setVisibility(isSpinnerVisible ? View.VISIBLE : View.GONE);
        content.setVisibility(!isSpinnerVisible ? View.VISIBLE : View.GONE);
    }

    public void getWeatherData() {
        weatherViewModel.getWeather().observe(WeatherActivity.this, new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
                if (weatherEntries != null && weatherEntries.size() > 0) {

                    int nowWeatherIndex = 0;
                    Date now = new Date();
                    //TODO find weather entry closest to now

                    toggleSpinner(false);

                    todaysWeather = weatherEntries.get(0);
                    setUpTodaysWeather();

                    setUpUpcomingWeatherByIndex(0, weatherEntries.get(nowWeatherIndex + 1));
                    setUpUpcomingWeatherByIndex(1, weatherEntries.get(nowWeatherIndex + 2));
                    setUpUpcomingWeatherByIndex(2, weatherEntries.get(nowWeatherIndex + 3));
                    setUpUpcomingWeatherByIndex(3, weatherEntries.get(nowWeatherIndex + 4));
                }
            }
        });
    }

    private void setUpUpcomingWeatherByIndex(int index, WeatherEntry weatherEntry) {
        if (index < 0 || index > 3) return;
        View layout;
        switch (index) {
            case 0: layout = findViewById(R.id.upcoming_day_1);
                    break;
            case 1: layout = findViewById(R.id.upcoming_day_2);
                    break;
            case 2: layout = findViewById(R.id.upcoming_day_3);
                    break;
            default: layout = findViewById(R.id.upcoming_day_4);
        }

        ImageView weatherIcon = layout.findViewById(R.id.weather_icon);
        TextView timeTextView = layout.findViewById(R.id.time_text_view);
        TextView temperatureTextView = layout.findViewById(R.id.temperature_text_view);
        TextView descriptionTextView = layout.findViewById(R.id.upcoming_weather_description_text_view);

        Date dateOfWeather = new Date(weatherEntry.getDt() * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("h:'00' a");
        timeTextView.setText(formatter.format(dateOfWeather));

        if (!weatherEntry.getWeather().isEmpty()) {
            WeatherObject weatherObject = weatherEntry.getWeather().get(0);

            loadImage(weatherObject.getIcon(), weatherIcon);

            descriptionTextView.setText(weatherObject.getMain());
        }

        if (weatherEntry.getMain() != null) {
            temperatureTextView.setText(getString(R.string.temperature_value, Math.round(weatherEntry.getMain().getTemp())));

        }
    }

    private void loadImage(String imageUrl, ImageView imageView) {
        if (imageUrl != null && imageView != null) {
            Glide.with(this)
                    .load(BASE_IMAGE_URL + imageUrl + PNG)
                    .into(imageView);
        }

    }

    private void setUpTodaysWeather() {
        if (!todaysWeather.getWeather().isEmpty()) {

            WeatherObject todaysWeatherObject = todaysWeather.getWeather().get(0);

            loadImage(todaysWeatherObject.getIcon(), todaysWeatherIcon);

            mainDescriptionTextView.setText(todaysWeatherObject.getMain());
            secondaryDescriptionTextView.setText(todaysWeatherObject.getDescription());
        }

        if (todaysWeather.getMain() != null) {
            Main todaysMainWeather = todaysWeather.getMain();
            todaysTemperatureTextView.setText(getString(R.string.temperature, Math.round(todaysMainWeather.getTemp())));
            todaysHumidityTextView.setText(getString(R.string.humidity, todaysMainWeather.getHumidity()));
        }

    }

}
