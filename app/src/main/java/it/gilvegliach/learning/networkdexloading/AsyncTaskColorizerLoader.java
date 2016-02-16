package it.gilvegliach.learning.networkdexloading;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AsyncTaskColorizerLoader implements AsyncColorizerLoader {
    private ColorizerLoader colorizerLoader;
    private OnColorizerFetchedListener listener;
    private volatile String urlRequestInProgress = null;

    @Inject
    public AsyncTaskColorizerLoader(ColorizerLoader loader){
        colorizerLoader = loader;
    }

    @Override
    public void loadColorizer(String url) {
        checkDifferentRequestInProgress(url);
        if (sameRequestIsInProgress(url)) return;
        newAsyncTask(url).execute();
    }

    @NonNull
    private AsyncTask<Void, Void, Colorizer> newAsyncTask(final String url) {
        return new AsyncTask<Void, Void, Colorizer>() {
            @Override
            protected Colorizer doInBackground(Void... params) {
                urlRequestInProgress = url;
                return colorizerLoader.load(url);
            }

            @Override
            protected void onPostExecute(Colorizer colorizer) {
                String url = urlRequestInProgress;
                urlRequestInProgress = null;
                if (listener != null) {
                    listener.onColorizerFetched(url, colorizer);
                }
            }
        };
    }

    private boolean sameRequestIsInProgress(String url) {
        return urlRequestInProgress != null && urlRequestInProgress.equalsIgnoreCase(url);
    }

    private void checkDifferentRequestInProgress(String url) {
        if (urlRequestInProgress != null && !urlRequestInProgress.equalsIgnoreCase(url)) {
            throw new RuntimeException("Only one request at a time is allowed");
        }
    }


    @Override
    public boolean addListener(OnColorizerFetchedListener listener) {
        this.listener = listener;
        return urlRequestInProgress != null;
    }


    @Override
    public void removeListener(OnColorizerFetchedListener listener) {
        this.listener = null;
    }
}
