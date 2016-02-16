package it.gilvegliach.learning.networkdexloading;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements AsyncColorizerLoader.OnColorizerFetchedListener {

    private static final int ONE_TWO_SWAPPER_ID = 1;
    private static final int ONE_THREE_SWAPPER_ID = 2;
    private Colorizer oneTwoSwapper;
    private Colorizer oneThreeSwapper;

    @Inject AsyncColorizerLoader loader;
    @Bind(R.id.text) TextView tv;
    @Bind(R.id.host_port) EditText hostPort;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        injectDependencies();
        loader.addListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loader.removeListener(this);
    }

    @OnClick(R.id.swap_one_two)
    public void swapOneTwo() {
        if (oneTwoSwapper == null) {
            loader.loadColorizer(url(ONE_TWO_SWAPPER_ID));
            return;
        }
        updateColor(oneTwoSwapper);
    }

    @OnClick(R.id.swap_one_three)
    public void swapOneThree() {
        if (oneThreeSwapper == null) {
            loader.loadColorizer(url(ONE_THREE_SWAPPER_ID));
            return;
        }
        updateColor(oneThreeSwapper);
    }

    @Override
    public void onColorizerFetched(String url, Colorizer colorizer) {
        saveColorizer(url, colorizer);
        updateColor(colorizer);
    }

    private void saveColorizer(String url, Colorizer colorizer) {
        if (url(ONE_TWO_SWAPPER_ID).equalsIgnoreCase(url)) {
            oneTwoSwapper = colorizer;
        } else if (url(ONE_THREE_SWAPPER_ID).equalsIgnoreCase(url)) {
            oneThreeSwapper = colorizer;
        } else {
            throw new RuntimeException("id must be 1 or 2");
        }
    }

    private void updateColor(Colorizer colorizer) {
        int oldColor = ((ColorDrawable) tv.getBackground()).getColor();
        int newColor = colorizer.colorize(oldColor);
        String newColorStr = "#" + Integer.toHexString(newColor).substring(2);
        tv.setBackgroundColor(newColor);
        tv.setText(newColorStr);
    }

    private String url(int id) {
        String baseUrl = hostPort.getText().toString();
        return String.format("%s/dex-%d.dex", baseUrl, id);
    }

    private void injectDependencies() {
        ((App) getApplicationContext()).getSingletonComponent().inject(this);
    }
}
