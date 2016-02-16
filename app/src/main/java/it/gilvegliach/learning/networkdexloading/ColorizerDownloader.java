package it.gilvegliach.learning.networkdexloading;

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

    public void download(String url, File dex) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            URL urlObject = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) urlObject.openConnection();
            int responseCode = httpConn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Download didn't work, http status: " + responseCode);
            }

            bis = new BufferedInputStream(httpConn.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(dex));
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