package it.gilvegliach.learning.networkdexloading;

public interface AsyncColorizerLoader {

    interface OnColorizerFetchedListener {
        void onColorizerFetched(String url, Colorizer colorizer);
    }

    void loadColorizer(String url);

    boolean addListener(OnColorizerFetchedListener listener);

    // The parameter would be useful if we manage a list of listeners instead of only one
    void removeListener(OnColorizerFetchedListener listener);
}
