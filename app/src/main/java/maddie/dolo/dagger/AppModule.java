package maddie.dolo.dagger;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import maddie.dolo.data.WeatherDao;
import maddie.dolo.data.WeatherDatabase;
import maddie.dolo.data.WeatherRepository;
import maddie.dolo.data.WeatherService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    WeatherDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                WeatherDatabase.class, "weather.db")
                .build();
    }

    @Provides
    @Singleton
    WeatherDao provideWeatherDao(WeatherDatabase database) { return database.weatherDao(); }

    // --- REPOSITORY INJECTION ---

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherService webservice, WeatherDao userDao, Executor executor) {
        return new WeatherRepository(webservice, userDao, executor);
    }

    // --- NETWORK INJECTION ---
    private static String BASE_URL = "http://api.openweathermap.org/";

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    WeatherService provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(WeatherService.class);
    }
}
