package it.gilvegliach.learning.networkdexloading;

public interface AsyncColorizerLoader {

    interface OnColorizerFetchedListener {
        void onColorizerFetched(int id, Colorizer colorizer);
    }

    void loadColorizer(int id);

    boolean addListener(OnColorizerFetchedListener listener);

    void removeListener(OnColorizerFetchedListener listener);
}
