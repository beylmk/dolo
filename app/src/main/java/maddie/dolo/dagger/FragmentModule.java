package maddie.dolo.dagger;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract Fragment contributeUserProfileFragment();
}
