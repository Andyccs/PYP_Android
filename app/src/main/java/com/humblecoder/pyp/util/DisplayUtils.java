package com.humblecoder.pyp.util;

import android.content.res.Resources;

/**
 * An utility that convert to/fo dp and pixel
 *
 * @author HumbleCoder
 */
public class DisplayUtils {

    private Resources resources;

    public DisplayUtils(Resources resources) {
        this.resources = resources;
    }

    public float convertDpToPixel(float paramFloat) {
        return paramFloat
                * (resources.getDisplayMetrics().densityDpi / 160.0F);
    }

    public float convertPixelsToDp(float paramFloat) {
        return paramFloat
                / (resources.getDisplayMetrics().densityDpi / 160.0F);
    }

    public int getDisplayHeightPixels() {
        return resources.getDisplayMetrics().heightPixels;
    }

    public int getDisplayWidthPixels() {
        return resources.getDisplayMetrics().widthPixels;
    }

}
