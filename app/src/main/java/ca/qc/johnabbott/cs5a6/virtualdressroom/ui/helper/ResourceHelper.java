package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper;

import android.net.Uri;

import java.util.Objects;

import ca.qc.johnabbott.cs5a6.virtualdressroom.R;

public class ResourceHelper {
    public static String getURLForResource (int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://"+ Objects.requireNonNull(R.class.getPackage()).getName() + "/" + resourceId).toString();
    }
}
