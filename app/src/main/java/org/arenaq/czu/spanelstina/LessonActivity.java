package org.arenaq.czu.spanelstina;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import org.arenaq.czu.spanelstina.database.Database;
import org.arenaq.czu.spanelstina.database.model.Lecture;

/**
 * Created by arenaq on 30.3.2015.
 */
public class LessonActivity extends Activity {
    Database db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        TextView tvWord = (TextView)findViewById(R.id.word);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        db = new Database(this);

        int lectureId = getIntent().getIntExtra("lesson_id", -1);

        if (lectureId > 0) {
            Lecture lecture = db.getLecture(lectureId);
            String text = "WORD COUNT: " + lecture.getWords().size();
            tvWord.setText(text);
        }
    }

}
