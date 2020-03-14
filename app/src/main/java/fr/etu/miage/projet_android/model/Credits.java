package fr.etu.miage.projet_android.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Credits {
    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private ArrayList<Cast> cast;
    @SerializedName("crew")
    private ArrayList<Crew> crew;

    public Credits(int id, ArrayList<Cast> cast, ArrayList<Crew> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Crew> crew) {
        this.crew = crew;
    }
}
