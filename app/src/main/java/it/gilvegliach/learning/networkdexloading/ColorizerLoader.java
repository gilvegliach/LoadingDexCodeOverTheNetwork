package it.gilvegliach.learning.networkdexloading;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ColorizerLoader {
    private final Context context;
    private final ColorizerDownloader downloader;
    private final ColorizerLinker linker;

    @Inject
    public ColorizerLoader(Context appContext, ColorizerDownloader downloader, ColorizerLinker linker) {
        this.context = appContext;
        this.downloader = downloader;
        this.linker = linker;
    }

    public Colorizer load(String url) {
        File dex = newDexFile(url);
        downloader.download(url, dex);

        String className = buildClassName(url);
        File codeCacheDir = getCodeCacheDir();
        ClassLoader classLoader = context.getClassLoader();
        return linker.link(className, dex, codeCacheDir, classLoader);
    }

    private File newDexFile(String url) {
        String name = dexFileName(url);
        return new File(context.getDir("dex", Context.MODE_PRIVATE), name);
    }

    @NonNull
    private String dexFileName(String url) {
        int index = url.lastIndexOf('/') + 1;
        return url.substring(index);
    }

    private String buildClassName(String url) {
        String dex = dexFileName(url);
        switch (dex) {
            case "dex-1.dex": return "it.gilvegliach.learning.networkdexloading.OneTwoSwapperColorizer";
            case "dex-2.dex": return "it.gilvegliach.learning.networkdexloading.OneThreeSwapperColorizer";
            default: throw new RuntimeException("Cannot build class name for id: " + dex);
        }
    }

    private File getCodeCacheDir() {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getCodeCacheDir();
        } else {
            return context.getDir("odex", Context.MODE_PRIVATE);
        }
    }
}
