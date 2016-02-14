package it.gilvegliach.learning.networkdexloading;

import android.app.Application;

import it.gilvegliach.learning.networkdexloading.di.DaggerSingletonComponent;
import it.gilvegliach.learning.networkdexloading.di.SingletonComponent;
import it.gilvegliach.learning.networkdexloading.di.SingletonModule;

public class App extends Application {
    private static SingletonComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerSingletonComponent.builder()
                .singletonModule(new SingletonModule(this))
                .build();
    }

    public static SingletonComponent getSingletonComponent() {
        return component;
    }
}
