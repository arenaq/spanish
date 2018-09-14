package org.arenaq.czu.spanelstina;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.arenaq.czu.spanelstina.database.model.Lecture;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @NonNull
    private CompositeDisposable compositeDisposable;

    @NonNull
    private MainViewModel viewModel;

    @BindView(R.id.btnLessons)
    Button btnLessons;

    @BindView(R.id.btnStatistics)
    Button btnStatistics;

    @BindView(R.id.btnExit)
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new MainViewModel();
        ButterKnife.bind(this);
        Environment.getExternalStorageDirectory().get
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBind();
    }

    private void bind() {
        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(viewModel.requestLessonsList(this)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showLessonPicker));
    }

    private void unBind() {
        compositeDisposable.clear();
    }

    @OnClick(R.id.btnLessons)
    protected void requestLessonsList() {
        viewModel.requestLessonsList(this);
    }

    @OnClick(R.id.btnStatistics)
    protected void showStatistics() {
        Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnExit)
    protected void exitApplication() {
        finish();
    }

    protected void showLessonPicker(List<Lecture> lectures) {
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
        builder.create().show();
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
