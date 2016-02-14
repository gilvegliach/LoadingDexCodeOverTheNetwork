package it.gilvegliach.learning.networkdexloading;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AsyncTaskColorizerLoader implements AsyncColorizerLoader {
    private static final int INVALID_ID = -1;
    private ColorizerLoader colorizerLoader;
    private OnColorizerFetchedListener listener;
    private volatile int idRequestInProgress = -1;

    @Inject
    public AsyncTaskColorizerLoader(ColorizerLoader loader){
        colorizerLoader = loader;
    }

    @Override
    public void loadColorizer(int id) {
        checkDifferentRequestInProgress(id);
        if (sameRequestIsInProgress(id)) return;
        newAsyncTask(id).execute();
    }

    @NonNull
    private AsyncTask<Void, Void, Colorizer> newAsyncTask(final int id) {
        return new AsyncTask<Void, Void, Colorizer>() {
            @Override
            protected Colorizer doInBackground(Void... params) {
                idRequestInProgress = id;
                return colorizerLoader.load(id);
            }

            @Override
            protected void onPostExecute(Colorizer colorizer) {
                int id = idRequestInProgress;
                idRequestInProgress = INVALID_ID;
                if (listener != null) {
                    listener.onColorizerFetched(id, colorizer);
                }
            }
        };
    }

    private boolean sameRequestIsInProgress(int id) {
        return idRequestInProgress != INVALID_ID && idRequestInProgress == id;
    }

    private void checkDifferentRequestInProgress(int id) {
        if (idRequestInProgress != INVALID_ID && idRequestInProgress != id) {
            throw new RuntimeException("Only one request at a time is allowed");
        }
    }


    @Override
    public boolean addListener(OnColorizerFetchedListener listener) {
        this.listener = listener;
        return idRequestInProgress != INVALID_ID;
    }

    // The parameter would be useful if we manage a list of listeners instead of only one
    @Override
    public void removeListener(OnColorizerFetchedListener listener) {
        this.listener = null;
    }
}
