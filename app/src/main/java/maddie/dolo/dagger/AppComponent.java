package maddie.dolo.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules={ActivityModule.class, AppModule.class})
public interface AppComponent {

    @Component.Builder
    public interface Builder {
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }

    void inject(App app);
}
