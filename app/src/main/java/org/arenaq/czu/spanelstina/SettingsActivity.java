package org.arenaq.czu.spanelstina;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by arenaq on 15.4.2015.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
