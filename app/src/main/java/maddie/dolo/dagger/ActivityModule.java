package maddie.dolo.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import maddie.dolo.ui.WeatherActivity;


@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract WeatherActivity contributeWeatherActivity();
}
