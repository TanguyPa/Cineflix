package fr.etu.miage.projet_android.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PopularCollection {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private ArrayList<Movie> results;

    public PopularCollection(int page, int totalResults, ArrayList<Movie> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
