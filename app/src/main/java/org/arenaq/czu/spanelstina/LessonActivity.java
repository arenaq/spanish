package org.arenaq.czu.spanelstina;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.arenaq.czu.spanelstina.database.Database;
import org.arenaq.czu.spanelstina.database.model.Lecture;
import org.arenaq.czu.spanelstina.database.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by arenaq on 30.3.2015.
 */
public class LessonActivity extends Activity implements View.OnClickListener {
    private Lecture lecture;
    private int progress;
    private int mistakes;
    private List<Word> words;
    private List<Word> usedWords;
    private List<Word> currentWordOptions;
    private boolean answerFound = false;
    private Word current;
    Database db;

    private TextView tvWord;
    private TextView tvScore;
    private Button bOpt1;
    private Button bOpt2;
    private Button bOpt3;
    private Button bOpt4;
    private Button bOpt5;
    private Button bNext;

    private SharedPreferences sharedPreferences;
    private boolean revertTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        revertTranslation = sharedPreferences.getBoolean("pref_key_revert", false);

        this.progress = 0;
        this.mistakes = 0;
        this.tvWord = findViewById(R.id.word);
        this.tvScore = findViewById(R.id.score);
        this.bOpt1 = findViewById(R.id.opt1);
        this.bOpt2 = findViewById(R.id.opt2);
        this.bOpt3 = findViewById(R.id.opt3);
        this.bOpt4 = findViewById(R.id.opt4);
        this.bOpt5 = findViewById(R.id.opt5);
        this.bNext = findViewById(R.id.next);

        bOpt1.setOnClickListener(this);
        bOpt2.setOnClickListener(this);
        bOpt3.setOnClickListener(this);
        bOpt4.setOnClickListener(this);
        bOpt5.setOnClickListener(this);


        usedWords = new ArrayList<Word>();
        currentWordOptions = new ArrayList<Word>();

        db = new Database(this);

        int lectureId = getIntent().getIntExtra("lesson_id", -1);

        if (lectureId > 0) {
            lecture = db.getLecture(lectureId);
            words = lecture.getWords();
        }

        nextRound();

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerFound) nextRound();
            }
        });

        redrawScore();
    }

    private Word pickRandomWord(List<Word> list) {
        int i = 0;
        Word word;
        do {
            word = words.get(new Random().nextInt(words.size()));
            i++;
        } while (isWordInList(word, list) && i < 10000);
        return word;
    }

    private boolean isWordInList(Word word, List<Word> list) {
        for (Word w : list) {
            if (word.getId() == w.getId()) return true;
        }
        return false;
    }

    private void nextRound() {
        currentWordOptions.clear();

        current = pickRandomWord(usedWords);
        usedWords.add(current);

        tvWord.setText(revertTranslation ? current.getSpanish() : current.getCzech());

        List<Button> shuffledList = new ArrayList<Button>();
        shuffledList.add(bOpt1);
        shuffledList.add(bOpt2);
        shuffledList.add(bOpt3);
        shuffledList.add(bOpt4);
        shuffledList.add(bOpt5);

        long seed = System.nanoTime();
        Collections.shuffle(shuffledList, new Random(seed));

        Button right_answer = shuffledList.get(0);
        right_answer.setBackgroundResource(android.R.drawable.btn_default);
        right_answer.setText(revertTranslation ? current.getCzech() : current.getSpanish());
        right_answer.setEnabled(true);

        currentWordOptions.add(current);

        for (int i = 1; i < shuffledList.size(); i++) {
            Word word = pickRandomWord(currentWordOptions);
            currentWordOptions.add(word);
            Button button = shuffledList.get(i);
            button.setBackgroundResource(android.R.drawable.btn_default);
            button.setText(revertTranslation ? word.getCzech() : word.getSpanish());
            button.setEnabled(true);
        }

        answerFound = false;
        progress++;

        redrawScore();
    }

    private void redrawScore() {
        tvScore.setText(progress + "/" + words.size() + "\nM: " + mistakes);
    }

    private void disableAll() {
        bOpt1.setEnabled(false);
        bOpt2.setEnabled(false);
        bOpt3.setEnabled(false);
        bOpt4.setEnabled(false);
        bOpt5.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        String textToCompare = revertTranslation ? current.getCzech() : current.getSpanish();
        if (textToCompare.compareTo(((Button) v).getText().toString()) == 0) {
            v.setBackgroundColor(Color.GREEN);
            answerFound = true;
            disableAll();
        } else {
            v.setBackgroundColor(Color.RED);
            v.setEnabled(false);
            mistakes++;
            redrawScore();
        }
    }
}
