package org.arenaq.czu.spanelstina;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.arenaq.czu.spanelstina.database.Database;
import org.arenaq.czu.spanelstina.database.model.Lecture;

import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lessons = (Button)findViewById(R.id.lessons);
        Button statistics = (Button)findViewById(R.id.statistics);
        Button exit = (Button)findViewById(R.id.exit);

        lessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickLesson().show();
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
