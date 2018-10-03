package maddie.dolo.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("data/2.5/forecast?")
    Call<WeatherResponse> getWeather(@Query("lat") long latitude, @Query("lon") long longitude, @Query("units") String units, @Query("appid") String apiKey);


}
