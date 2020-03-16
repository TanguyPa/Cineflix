package fr.etu.miage.projet_android.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.etu.miage.projet_android.R;
import fr.etu.miage.projet_android.RetrofitClient;
import fr.etu.miage.projet_android.activity.MovieDetailsActivity;
import fr.etu.miage.projet_android.adapter.MovieViewAdapter;
import fr.etu.miage.projet_android.model.Movie;
import fr.etu.miage.projet_android.model.MovieDetails;
import fr.etu.miage.projet_android.model.MoviesCollection;
import fr.etu.miage.projet_android.service.FavoriteService;
import fr.etu.miage.projet_android.service.TmdbService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.etu.miage.projet_android.ui.home.HomeFragment.MOVIE_NAME;

public class SearchFragment extends Fragment {

    private static String TAG = SearchFragment.class.getSimpleName();

    private RecyclerView moviesSearchView;
    private SearchView searchView;
    private MovieViewAdapter moviesSearchAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = root.findViewById(R.id.searchView);
        moviesSearchView = root.findViewById(R.id.searchMovies);

        //Initialize layoutManager, grid layout with 3 movie cards per line
        moviesSearchView.setLayoutManager(new GridLayoutManager(root.getContext(),3));

        //Create adapter with the OnItemClickListener implementation to get movie details
        MovieViewAdapter.OnItemClickListener onItemClickListener = new MovieViewAdapter.OnItemClickListener() {
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
                } else {
                    ((ImageView) view).setImageResource(R.drawable.ic_add_black_24dp);
                }
            }
        };
        moviesSearchAdapter = new MovieViewAdapter(new ArrayList<Movie>(),onItemClickListener,favOnClickListener);

        //Set the adapter
        moviesSearchView.setAdapter(moviesSearchAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null && !query.isEmpty() && query.trim() != "") {
                    fetchMoviesWithKeysWordData(query);
                };
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty() && newText.trim() != "") {
                    fetchMoviesWithKeysWordData(newText);
                };
                return false;
            }
        });
        return root;
    }

    private void fetchMoviesWithKeysWordData(final String query) {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.searchMovies(TmdbService.API_KEY, "fr-FR",  query, null, null, null, null, null).enqueue(new Callback<MoviesCollection>() {
            @Override
            public void onResponse(Call<MoviesCollection> call, Response<MoviesCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MoviesCollection moviesSearch = response.body();
                    moviesSearchAdapter.setMovieList(moviesSearch.getResults());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur query : |"+query+ "| ", Toast.LENGTH_LONG).show();
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