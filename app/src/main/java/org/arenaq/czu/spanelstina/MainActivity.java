package org.arenaq.czu.spanelstina;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.arenaq.czu.spanelstina.database.Database;
import org.arenaq.czu.spanelstina.database.model.Lecture;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.lessons)
    Button lessons;

    @BindView(R.id.statistics)
    Button statistics;

    @BindView(R.id.exit)
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.lessons)
    protected void showLessonPicker() {
        pickLesson().show();
    }

    @OnClick(R.id.statistics)
    protected void showStatistics() {
        Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.exit)
    protected void exitApplication() {
        finish();
    }

    protected Dialog pickLesson() {
        Database db = new Database(this);
        List<Lecture> lectures = db.getAllLectures();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.lessons_dialog_title);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);

        int i = 1;
        for (Lecture lecture : lectures) {
            arrayAdapter.add("Lecture " + i + ": " + lecture.getName());
            i++;
        }

        builder.setNeutralButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, LessonActivity.class);
                intent.putExtra("lesson_id", which+1);
                startActivity(intent);
            }
        });

        builder.setCancelable(true);
        return builder.create();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
