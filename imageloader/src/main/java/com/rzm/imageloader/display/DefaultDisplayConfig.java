package com.rzm.imageloader.display;

import android.support.annotation.DrawableRes;

public class DefaultDisplayConfig extends AbstractDisplay {

    private int loadingImage;
    private int errorImage;

    @Override
    public int getLoadingImage() {
        return loadingImage;
    }

    @Override
    public int getErrorImage() {
        return errorImage;
    }

    public static class Builder {
        DefaultDisplayConfig config;

        public Builder() {
            config = new DefaultDisplayConfig();
        }

        public Builder setLoadingImage(@DrawableRes int res) {
            config.loadingImage = res;
            return this;
        }

        public Builder setErrorImage(@DrawableRes int res) {
            config.errorImage = res;
            return this;
        }

        public DefaultDisplayConfig build() {
            return config;
        }
    }

}
