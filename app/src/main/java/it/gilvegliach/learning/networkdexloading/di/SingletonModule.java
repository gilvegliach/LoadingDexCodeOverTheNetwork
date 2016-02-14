package it.gilvegliach.learning.networkdexloading.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.gilvegliach.learning.networkdexloading.AsyncColorizerLoader;
import it.gilvegliach.learning.networkdexloading.AsyncTaskColorizerLoader;

@Module
public class SingletonModule {
    private final Context appContext;

    public SingletonModule(Context context) {
        appContext = context.getApplicationContext();
    }

    @Provides
    @Singleton
    public AsyncColorizerLoader asyncColorizerLoader(AsyncTaskColorizerLoader impl) {
        return impl;
    }

    @Provides
    @Singleton
    public Context context() {
        return appContext;
    }
}
