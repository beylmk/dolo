package maddie.dolo.data;

import android.arch.lifecycle.LiveData;
import android.net.Network;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import maddie.dolo.BuildConfig;
import maddie.dolo.R;
import maddie.dolo.dagger.App;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class WeatherRepository implements NetworkAvailabilityTask.Consumer {
    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

    private final WeatherService webservice;
    private final WeatherEntryDao weatherEntryDao;
    private final Executor executor;
    private Date lastRefresh = null;

    @Inject
    public WeatherRepository(WeatherService webservice, WeatherEntryDao weatherEntryDao, Executor executor) {
        this.webservice = webservice;
        this.weatherEntryDao = weatherEntryDao;
        this.executor = executor;
    }

    public LiveData<List<WeatherEntry>> getWeather() {
        refreshWeather();
        return weatherEntryDao.getWeather(); // return a LiveData directly from the database.
    }

    private void refreshWeather() {
        new NetworkAvailabilityTask(this).execute();
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    @Override
    public void accept(Boolean internet) {
        if (internet) {
            callWeatherService();
        } else {
            Toast.makeText(App.context, R.string.no_internet, Toast.LENGTH_LONG).show();
        }
    }

    private void callWeatherService() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                webservice.getWeather(38, 122, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, final Response<WeatherResponse> response) { Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                        Toast.makeText(App.context, R.string.weather_refreshed, Toast.LENGTH_LONG).show();
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                List<WeatherEntry> daysOfWeather = response.body().getList();
                                lastRefresh = new Date();

                                weatherEntryDao.insertWeather(daysOfWeather);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Toast.makeText(App.context, R.string.trouble_fetching_weather, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
