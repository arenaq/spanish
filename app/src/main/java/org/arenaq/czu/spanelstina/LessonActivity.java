package org.arenaq.czu.spanelstina;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by arenaq on 30.3.2015.
 */
public class LessonActivity extends Activity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        int lesson = getIntent().getIntExtra("LESSON", 1);
        Resources res = getResources();
        String[] espanol;
        String[] czech;

        switch (lesson) {
            case 1:
                espanol = res.getStringArray(R.array.lesson1_es);
                czech = res.getStringArray(R.array.lesson1_cs);
                break;
            case 2:
                espanol = res.getStringArray(R.array.lesson2_es);
                czech = res.getStringArray(R.array.lesson2_cs);
                break;
            case 3:
                espanol = res.getStringArray(R.array.lesson3_es);
                czech = res.getStringArray(R.array.lesson3_cs);
                break;
            default:
                espanol = res.getStringArray(R.array.lesson1_es);
                czech = res.getStringArray(R.array.lesson1_cs);
                break;
        }

        TextView word = (TextView)findViewById(R.id.word);
        Button opt1 = (Button)findViewById(R.id.opt1);
        Button opt2 = (Button)findViewById(R.id.opt2);
        Button opt3 = (Button)findViewById(R.id.opt3);
        TextView result = (TextView)findViewById(R.id.result);

        word.setText(czech[0]);
        opt1.setText(espanol[1]);
        opt2.setText(espanol[2]);
        opt3.setText(espanol[3]);
    }

}
