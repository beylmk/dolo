package maddie.dolo.data;

import android.arch.lifecycle.LiveData;
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
public class WeatherRepository {
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

    // ---

    public LiveData<List<WeatherEntry>> getWeather() {
//        Date now = new Date();
//        Date fiveDaysAgo = new Date(now.getTime() - 10000);
//        if (lastRefresh != null && lastRefresh.before(fiveDaysAgo)) {
            refreshWeather();
//        }
        return weatherEntryDao.getWeather(); // return a LiveData directly from the database.
    }

    // ---

    private void refreshWeather() {
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

    public Date getLastRefresh() {
        return lastRefresh;
    }
}
