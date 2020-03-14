package fr.etu.miage.projet_android.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import fr.etu.miage.projet_android.R;

public class FavoriteService {

    private static SharedPreferences getFavoriteSharedPref(Context context) {
       return context.getSharedPreferences("favorite_pref",Context.MODE_PRIVATE);
    }

    public static ArrayList<Integer> getFavoritesMovies(Context context) {
        SharedPreferences sharedPref = getFavoriteSharedPref(context);
        String favMovies = sharedPref.getString("favMovies",null);
        if(favMovies == null) {
            return new ArrayList<>();
        } else {
            StringTokenizer st = new StringTokenizer(favMovies, ",");
            ArrayList<Integer> moviesId = new ArrayList<>();
            while(st.hasMoreTokens()) {
                moviesId.add(Integer.parseInt(st.nextToken()));
            }
            return moviesId;
        }
    }

    public static boolean isMovieFavorite(Context context,int movieId) {
        return getFavoritesMovies(context).contains(movieId);
    }

    private static void addMovieToFavorites(Context context, int movieId) {
        ArrayList<Integer> favMovies = getFavoritesMovies(context);
        favMovies.add(movieId);
        StringBuilder str = new StringBuilder();
        for(int fav: favMovies) {
            str.append(fav).append(",");
        }
        SharedPreferences.Editor sharedPrefEditor = getFavoriteSharedPref(context).edit();
        sharedPrefEditor.putString("favMovies",str.toString());
        sharedPrefEditor.apply();
    }

    private static void removeMovieToFavorites(Context context, int movieId) {
        ArrayList<Integer> favMovies = getFavoritesMovies(context);
        if(favMovies.isEmpty()) return;
        if(favMovies.contains(movieId)) {
            favMovies.remove(favMovies.indexOf(movieId));
        }
        StringBuilder str = new StringBuilder();
        for(int fav: favMovies) {
            str.append(fav).append(",");
        }
        SharedPreferences.Editor sharedPrefEditor = getFavoriteSharedPref(context).edit();
        sharedPrefEditor.putString("favMovies",str.toString());
        sharedPrefEditor.apply();
    }

    /**
     *
     * @param movieId
     * @return true if the movie was added of fav, false if the movie was removed of fav
     */
    public static boolean clickOnMovie(Context context, int movieId) {
        if(isMovieFavorite(context, movieId))  {
            removeMovieToFavorites(context, movieId);
            return false;
        } else {
            addMovieToFavorites(context, movieId);
            return true;
        }
    }
}
