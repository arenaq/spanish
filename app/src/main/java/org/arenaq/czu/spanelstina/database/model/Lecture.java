package org.arenaq.czu.spanelstina.database.model;

import java.util.List;

/**
 * Created by arenaq on 9.4.2015.
 */
public class Lecture {
    int id;
    String name;
    List<Word> words;

    public Lecture() {};

    public Lecture(int id, String name, List<Word> words) {
        this.id = id;
        this.name = name;
        this.words = words;
    }

    public Lecture(int id, String name) {
        this(id, name, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
