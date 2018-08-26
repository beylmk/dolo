package maddie.dolo.data;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class WeatherRepository {
    private static int FRESH_TIMEOUT_IN_MINUTES = 3;

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

    public LiveData<List<WeatherModel>> getWeather() {
        refreshWeather(); // try to refresh data if possible from Github Api
        return weatherDao.getWeather(); // return a LiveData directly from the database.
    }

    // ---

    private void refreshWeather() {
        executor.execute(() -> {
            // Check if user was fetched recently
//            boolean userExists = (weatherDao.hasUser(userLogin, getMaxRefreshTime(new Date())) != null);
            // If user have to be updated
//            if (!userExists) {
                webservice.getWeather().enqueue(new Callback<List<WeatherModel>>() {
                    @Override
                    public void onResponse(Call<List<WeatherModel>> call, Response<List<WeatherModel>> response) {
                        executor.execute(() -> {
                            List<WeatherModel> weather = response.body();
//                            user.setLastRefresh(new Date());
                            weatherDao.insertWeather(weather);
                        });
                    }

                    @Override
                    public void onFailure(Call<List<WeatherModel>> call, Throwable t) { }
                });
//            }
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
