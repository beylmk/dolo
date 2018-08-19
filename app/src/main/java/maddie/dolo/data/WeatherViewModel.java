package maddie.dolo.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {

    private final LiveData<List<WeatherModel>> weatherList;

    private WeatherDatabase weatherDatabase;

    public WeatherViewModel(Application application) {
        super(application);

        weatherDatabase = WeatherDatabase.getWeatherDatabase(this.getApplication());
        weatherList = weatherDatabase.weatherDao().getWeather();
    }

    public LiveData<List<WeatherModel>> getWeatherList() {
        return weatherList;
    }
}
