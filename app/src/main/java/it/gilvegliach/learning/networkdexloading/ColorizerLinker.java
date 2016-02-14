package it.gilvegliach.learning.networkdexloading;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import dalvik.system.DexClassLoader;

@Singleton
public class ColorizerLinker {
    @Inject
    public ColorizerLinker() {
    }

    public Colorizer link(String className, File dex, File codeCacheDir, ClassLoader parent) {
        try {
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath(),
                    codeCacheDir.getAbsolutePath(), null, parent);
            Class clazz = classLoader.loadClass(className);
            return (Colorizer) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}