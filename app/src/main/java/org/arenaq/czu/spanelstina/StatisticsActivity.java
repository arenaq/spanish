package org.arenaq.czu.spanelstina;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import org.arenaq.czu.spanelstina.database.Database;
import org.arenaq.czu.spanelstina.database.model.Lecture;
import org.arenaq.czu.spanelstina.database.model.Word;

import java.util.List;

/**
 * Created by arenaq on 30.3.2015.
 */
public class StatisticsActivity extends Activity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        TextView textView = new TextView(this);
        setContentView(textView);

        Database db = new Database(this);
        String text = "";
        List<Lecture> lectures = db.getAllLectures();

        for (Lecture lecture : lectures) {
            text += "id=" + lecture.getId() + ",  " + lecture.getName() + ", words={";
            for (Word word : lecture.getWords()) {
                text += "[" + word.getId() + "," + word.getSpanish() + "," + word.getCzech() + "],";
            }
            text += "}\n";
        }

        textView.setText(text);
    }

}
