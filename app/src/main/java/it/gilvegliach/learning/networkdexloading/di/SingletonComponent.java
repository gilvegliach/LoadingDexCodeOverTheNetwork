package it.gilvegliach.learning.networkdexloading.di;

import javax.inject.Singleton;

import dagger.Component;
import it.gilvegliach.learning.networkdexloading.MainActivity;

@Singleton
@Component(modules = SingletonModule.class)
public interface SingletonComponent {
    void inject(MainActivity activity);
}
