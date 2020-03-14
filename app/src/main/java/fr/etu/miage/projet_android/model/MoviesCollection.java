package fr.etu.miage.projet_android.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesCollection {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private ArrayList<Movie> results;
    @SerializedName("dates")
    private DateUpcoming date;

    public MoviesCollection(int page, int totalResults, int totalPages, ArrayList<Movie> results, DateUpcoming date) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
        this.date = date;
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

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    public DateUpcoming getDate() {
        return date;
    }

    public void setDate(DateUpcoming date) {
        this.date = date;
    }
}