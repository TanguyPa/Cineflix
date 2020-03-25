package fr.etu.miage.projet_android.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.etu.miage.projet_android.*;
import fr.etu.miage.projet_android.activity.MainActivity;
import fr.etu.miage.projet_android.activity.MovieDetailsActivity;
import fr.etu.miage.projet_android.adapter.MovieViewAdapter;
import fr.etu.miage.projet_android.model.Movie;
import fr.etu.miage.projet_android.model.MoviesCollection;
import fr.etu.miage.projet_android.service.FavoriteService;
import fr.etu.miage.projet_android.service.RetrofitClient;
import fr.etu.miage.projet_android.service.TmdbService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView popularView;
    private RecyclerView upcomingView;
    private RecyclerView topRatedView;
    private RecyclerView nowPlayingView;
    private MovieViewAdapter popularAdapter;
    private MovieViewAdapter upcomingAdapter;
    private MovieViewAdapter topRatedAdapter;
    private MovieViewAdapter nowPlayingAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE_NAME = "movie_name";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        popularView = root.findViewById(R.id.popular);
        upcomingView = root.findViewById(R.id.upcoming);
        topRatedView = root.findViewById(R.id.toprated);
        nowPlayingView = root.findViewById(R.id.nowplaying);
        //Initialize layoutManager with a LinearLayoutManager, by default horizontal
        popularView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));

        //Create adapter with the OnItemClickListener implementation to get movie details
        MovieViewAdapter.OnItemClickListener detailsClickListener = new MovieViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Movie movie) {
                    Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                    intent.putExtra(MOVIE_NAME, movie.getId());
                    startActivity(intent);
            }
        };
        //Create adapter with the OnItemClickListener implementation to add or remove a movie from favorite
        MovieViewAdapter.OnItemClickListener favOnClickListener = new MovieViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Movie movie) {
                boolean added = FavoriteService.clickOnMovie(view.getContext(), movie.getId());
                if(added) {
                    ((ImageView) view).setImageResource(R.drawable.ic_favorite_red_24dp);
                    //Need to refresh data of each adapter if we add a movie present in different adapter (if not only, one icon will be updated)
                    nowPlayingAdapter.notifyDataSetChanged();
                    popularAdapter.notifyDataSetChanged();
                    topRatedAdapter.notifyDataSetChanged();
                    upcomingAdapter.notifyDataSetChanged();
                } else {
                    //Need to refresh data of each adapter if we remmove a movie present in different adapter (if not only, one icon will be updated)
                    ((ImageView) view).setImageResource(R.drawable.ic_add_black_24dp);
                    nowPlayingAdapter.notifyDataSetChanged();
                    popularAdapter.notifyDataSetChanged();
                    topRatedAdapter.notifyDataSetChanged();
                    upcomingAdapter.notifyDataSetChanged();
                }
            }
        };
        //Create different adapter, with empty movie list, listener to display movie details, and listener to add/remove movie to favorite
        popularAdapter = new MovieViewAdapter(new ArrayList<Movie>(),detailsClickListener,favOnClickListener);
        upcomingAdapter = new MovieViewAdapter(new ArrayList<Movie>(), detailsClickListener,favOnClickListener);
        topRatedAdapter = new MovieViewAdapter(new ArrayList<Movie>(), detailsClickListener,favOnClickListener);
        nowPlayingAdapter = new MovieViewAdapter(new ArrayList<Movie>(), detailsClickListener,favOnClickListener);

        //Set the adapter
        popularView.setAdapter(popularAdapter);
        upcomingView.setAdapter(upcomingAdapter);
        topRatedView.setAdapter(topRatedAdapter);
        nowPlayingView.setAdapter(nowPlayingAdapter);

        //Call fetch method to get data of each category
        fetchPopularMoviesData();
        fetchUpComingMoviesData();
        fetchTopRatedMoviesData();
        fetchNowPlayingMoviesData();

        return  root;
    }



    private void fetchPopularMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getPopular(TmdbService.API_KEY, "fr-FR", null, null).enqueue(new Callback<MoviesCollection>() {

            @Override
            public void onResponse(Call<MoviesCollection> call, Response<MoviesCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MoviesCollection collection = response.body();
                    popularAdapter.addMovieList(collection.getResults());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MoviesCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
//Manage errors
            }
        });
    }

    private void fetchUpComingMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getUpcoming(TmdbService.API_KEY, "fr-FR", null, null).enqueue(new Callback<MoviesCollection>() {

            @Override
            public void onResponse(Call<MoviesCollection> call, Response<MoviesCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MoviesCollection collection = response.body();
                    upcomingAdapter.addMovieList(collection.getResults());

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MoviesCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());

                //Manage errors
            }
        });
    }

    private void fetchTopRatedMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getTopRated(TmdbService.API_KEY, "fr-FR", null, null).enqueue(new Callback<MoviesCollection>() {

            @Override
            public void onResponse(Call<MoviesCollection> call, Response<MoviesCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MoviesCollection collection = response.body();
                    topRatedAdapter.addMovieList(collection.getResults());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MoviesCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
//Manage errors
            }
        });
    }

    private void fetchNowPlayingMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getNowPlaying(TmdbService.API_KEY, "fr-FR", null, null).enqueue(new Callback<MoviesCollection>() {

            @Override
            public void onResponse(Call<MoviesCollection> call, Response<MoviesCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MoviesCollection collection = response.body();
                    nowPlayingAdapter.addMovieList(collection.getResults());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MoviesCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
//Manage errors
            }
        });
    }
}