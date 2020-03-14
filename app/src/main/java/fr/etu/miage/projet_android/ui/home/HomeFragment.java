package fr.etu.miage.projet_android.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        //Initialize layoutManager with a LinearLayoutManager, by default vertical
        popularView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));

        //Create adapter with the OnItemClickListener implementation
        MovieViewAdapter.OnItemClickListener onItemClickListener = new MovieViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Movie movie) {
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra(MOVIE_NAME, movie.getId());
                startActivity(intent);
            }
        };
        popularAdapter = new MovieViewAdapter(new ArrayList<Movie>(),onItemClickListener);
        upcomingAdapter = new MovieViewAdapter(new ArrayList<Movie>(), onItemClickListener);
        topRatedAdapter = new MovieViewAdapter(new ArrayList<Movie>(), onItemClickListener);
        nowPlayingAdapter = new MovieViewAdapter(new ArrayList<Movie>(), onItemClickListener);

        //Set the adapter
        popularView.setAdapter(popularAdapter);
        upcomingView.setAdapter(upcomingAdapter);
        topRatedView.setAdapter(topRatedAdapter);
        nowPlayingView.setAdapter(nowPlayingAdapter);

        fetchPopularMoviesData();
        fetchUpComingMoviesData();
        fetchTopRatedMoviesData();
        fetchNowPlayingMoviesData();

        return  root;
    }



    private void fetchPopularMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getPopular("1abe855bc465dce9287da07b08a664eb", "fr-FR", null, null).enqueue(new Callback<MoviesCollection>() {

            @Override
            public void onResponse(Call<MoviesCollection> call, Response<MoviesCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MoviesCollection collection = response.body();
                    popularAdapter.addFeatureList(collection.getResults());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MoviesCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
                Log.d(TAG, t.getCause().getMessage());
//Manage errors
            }
        });
    }

    private void fetchUpComingMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getUpcoming("1abe855bc465dce9287da07b08a664eb", "fr-FR", null, null).enqueue(new Callback<MoviesCollection>() {

            @Override
            public void onResponse(Call<MoviesCollection> call, Response<MoviesCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MoviesCollection collection = response.body();
                    upcomingAdapter.addFeatureList(collection.getResults());

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MoviesCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
                Log.d(TAG, t.getCause().getMessage());
//Manage errors
            }
        });
    }

    private void fetchTopRatedMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getTopRated("1abe855bc465dce9287da07b08a664eb", "fr-FR", null, null).enqueue(new Callback<MoviesCollection>() {

            @Override
            public void onResponse(Call<MoviesCollection> call, Response<MoviesCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MoviesCollection collection = response.body();
                    topRatedAdapter.addFeatureList(collection.getResults());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MoviesCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
                Log.d(TAG, t.getCause().getMessage());
//Manage errors
            }
        });
    }

    private void fetchNowPlayingMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getNowPlaying("1abe855bc465dce9287da07b08a664eb", "fr-FR", null, null).enqueue(new Callback<MoviesCollection>() {

            @Override
            public void onResponse(Call<MoviesCollection> call, Response<MoviesCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MoviesCollection collection = response.body();
                    nowPlayingAdapter.addFeatureList(collection.getResults());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MoviesCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
                Log.d(TAG, t.getCause().getMessage());
//Manage errors
            }
        });
    }
}