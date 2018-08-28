package maddie.dolo.data;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("data/2.5//forecast?")
    Call<List<Weather>> getWeather(@Query("lat") long latitude, @Query("lon") long longitude, @Query("appid") String apiKey);


}
