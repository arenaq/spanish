package org.arenaq.czu.spanelstina.database.model;

/**
 * Created by arenaq on 9.4.2015.
 */
public class Word {

    int _id;
    String _spanish;
    String _czech;

    public Word() {};

    public Word(int id, String spanish, String czech) {
        this._id = id;
        this._spanish = spanish;
        this._czech = czech;
    }

    public Word(String spanish, String czech) {
        this._spanish = spanish;
        this._czech = czech;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getSpanish() {
        return _spanish;
    }

    public void setSpanish(String spanish) {
        this._spanish = spanish;
    }

    public String getCzech() {
        return _czech;
    }

    public void setCzech(String czech) {
        this._czech = czech;
    }
}
