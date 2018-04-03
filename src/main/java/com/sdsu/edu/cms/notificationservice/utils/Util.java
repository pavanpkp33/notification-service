package com.sdsu.edu.cms.notificationservice.utils;

import com.sdsu.edu.cms.common.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class Util {
    public static final Set<String> supportedMethods = new HashSet<>(
            Arrays.asList(Constants.SCHEME_EMAIL,
                          Constants.SCHEME_INAPP)
    );

    public static Set<String> getSupportedMethods() {
        return supportedMethods;
    }
}
