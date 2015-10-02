package com.maverick.di;

import com.sun.jna.platform.WindowUtils;

public class EnvironmentUtils {

    static boolean useDropShadow() {
        return WindowUtils.isWindowAlphaSupported()
            && !System.getProperty("os.name").startsWith("Mac");
    }
}
