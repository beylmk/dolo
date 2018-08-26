package maddie.dolo.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherService {

    @GET("https://api.github.com/")
    Call<List<WeatherModel>> getWeather();
}
