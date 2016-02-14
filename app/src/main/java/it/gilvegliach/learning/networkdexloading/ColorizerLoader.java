package it.gilvegliach.learning.networkdexloading;

import android.content.Context;
import android.os.Build;

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

    public Colorizer load(int id) {
        File dex = newDexFile(id);
        downloader.download(id, dex);

        String className = buildClassName(id);
        File codeCacheDir = getCodeCacheDir();
        ClassLoader classLoader = context.getClassLoader();
        return linker.link(className, dex, codeCacheDir, classLoader);
    }

    private File newDexFile(int id) {
        String name =  String.format("dex-%d.dex", id);
        return new File(context.getDir("dex", Context.MODE_PRIVATE), name);
    }

    private String buildClassName(int id) {
        switch (id) {
            case 1: return "it.gilvegliach.learning.networkdexloading.OneTwoSwapperColorizer";
            case 2: return "it.gilvegliach.learning.networkdexloading.OneThreeSwapperColorizer";
            default: throw new RuntimeException("Cannot build class name for id: " + id);
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
