package com.example.along.scmusic.utils.common;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static com.example.along.scmusic.utils.common.Genres.*;

/**
 * Created by Long .
 */

@StringDef({ ALTERNATIVEROCK, AMBIENT, CLASSICAL, COUNTRY })
@Retention(RetentionPolicy.SOURCE)
public @interface Genres {
    String ALTERNATIVEROCK = "alternativerock";
    String AMBIENT = "ambient";
    String CLASSICAL = "classical";
    String COUNTRY = "country";
}
