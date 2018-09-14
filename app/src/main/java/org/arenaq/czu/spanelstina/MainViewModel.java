package org.arenaq.czu.spanelstina;

import android.content.Context;
import android.support.annotation.NonNull;

import org.arenaq.czu.spanelstina.database.Database;
import org.arenaq.czu.spanelstina.database.model.Lecture;

import java.util.List;

import io.reactivex.Observable;

public class MainViewModel {

    public Observable<List<Lecture>> requestLessonsList(@NonNull Context context) {
        Database db = new Database(context);
        return Observable.just(db.getAllLectures());
    }
}
