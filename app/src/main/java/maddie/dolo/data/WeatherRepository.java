package maddie.dolo.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import maddie.dolo.BuildConfig;
import maddie.dolo.dagger.App;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class WeatherRepository {
    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

    private final WeatherService webservice;
    private final WeatherDao weatherDao;
    private final Executor executor;

    @Inject
    public WeatherRepository(WeatherService webservice, WeatherDao weatherDao, Executor executor) {
        this.webservice = webservice;
        this.weatherDao = weatherDao;
        this.executor = executor;
    }

    // ---

    public LiveData<List<DayOfWeather>> getWeather() {
        refreshWeather(); // try to refresh data if possible from Github Api
        return weatherDao.getWeather(); // return a LiveData directly from the database.
    }

    // ---

    private void refreshWeather() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Check if user was fetched recently
//            boolean userExists = (userDao.hasUser(userLogin, getMaxRefreshTime(new Date())) != null);
                // If user have to be updated
//            if (!userExists) {
                webservice.getWeather(38, 122, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, final Response<WeatherResponse> response) { Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                //TODO filter out to only include data points we care about
                                List<DayOfWeather> daysOfWeather = response.body().getList();
//                            user.setLastRefresh(new Date());

                                weatherDao.insertWeather(daysOfWeather);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e("TAG", "");
                    }
                });
//            }
            }
        });
    }

    // ---

//    private Date getMaxRefreshTime(Date currentDate){
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(currentDate);
//        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
//        return cal.getTime();
//    }
}
