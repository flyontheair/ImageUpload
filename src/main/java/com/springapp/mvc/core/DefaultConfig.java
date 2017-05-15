package com.springapp.mvc.core;

import java.io.IOException;

/**
 * Created by pcdalao on 2017/5/15.
 */
public class DefaultConfig {
    public String imagePrefix;
    public String imageRoot;

    public static synchronized DefaultConfig load() throws IOException {
        return ConfigLoader.loadYamlAs("default.yaml",DefaultConfig.class);
    }

    public String getImagePrefix() {
        return imagePrefix;
    }

    public void setImagePrefix(String imagePrefix) {
        this.imagePrefix = imagePrefix;
    }

    public String getImageRoot() {
        return imageRoot;
    }

    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
    }
}
