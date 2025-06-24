package com.stormmind.infrastructure.ai;

import lombok.Getter;

@Getter
public enum ClusterSize {
    THREE(new  float[]{7.1596f, 2.8898e+04f, 2.2029e+01f},
            new float[]{7.4802f, 1.0397e+04f, 2.1813e+01f}),

    SIX(new float[]{8.7132e+00f, 2.8966e+04f, 2.4517e+01f},
            new float[]{7.3103e+00f, 1.0314e+04f, 2.8042e+01f});


    private final float[] mean;
    private final float[] std;

    ClusterSize( float[] mean, float[] std) {
        this.mean = mean;
        this.std = std;
    }

}