package it.gilvegliach.learning.networkdexloading;

public class OneThreeSwapperColorizer implements Colorizer {
    @Override
    public int colorize(int color) {
        int a = color >>> 24;
        int r = (color >>> 16) & 0xff;
        int g = (color >>> 8) & 0xff;
        int b = color & 0xff;
        return (a << 24) | (b << 16) | (g << 8) | r;
    }
}
