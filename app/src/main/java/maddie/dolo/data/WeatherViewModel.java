package maddie.dolo.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

public class WeatherViewModel extends ViewModel {

    private LiveData<List<WeatherEntry>> weather;

    private WeatherRepository weatherRepository;

    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void init() {
        if (this.weather != null) {
            return;
        }
        weather = weatherRepository.getWeather();
    }

    public LiveData<List<WeatherEntry>> getWeather() {
        return this.weather;
    }

}
