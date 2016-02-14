package it.gilvegliach.learning.networkdexloading;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ColorizerDownloader {
    private static final int BUF_SIZE = 8 * 1024;

    @Inject
    public ColorizerDownloader() {
    }

    public void download(int id, File dex) {
        String url = buildDexUrl(id);
        downloadDexFile(url, dex);
    }

    private String buildDexUrl(int id) {
        return String.format("http://10.0.3.2:8888/dex-%d.dex", id);
    }

    private void downloadDexFile(String dexUrl, File dest) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            URL url = new URL(dexUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Download didn't work, http status: " + responseCode);
            }

            bis = new BufferedInputStream(httpConn.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(dest));
            copy(bis, bos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(bos, bis);
        }
    }

    private void copy(BufferedInputStream src, OutputStream dst) throws IOException {
        int len;
        byte[] buf = new byte[BUF_SIZE];
        while ((len = src.read(buf, 0, BUF_SIZE)) > 0) {
            dst.write(buf, 0, len);
        }
    }

    private static void closeQuietly(Closeable... cls) {
        for (Closeable cl : cls) {
            if (cl != null) {
                try {
                    cl.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}