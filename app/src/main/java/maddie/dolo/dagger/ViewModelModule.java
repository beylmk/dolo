package maddie.dolo.dagger;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import maddie.dolo.data.WeatherViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    abstract ViewModel bindWeatherViewModel(WeatherViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}