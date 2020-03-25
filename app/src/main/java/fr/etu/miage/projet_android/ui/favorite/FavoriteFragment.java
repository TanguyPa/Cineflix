package fr.etu.miage.projet_android.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.etu.miage.projet_android.R;
import fr.etu.miage.projet_android.service.RetrofitClient;
import fr.etu.miage.projet_android.activity.MovieDetailsActivity;
import fr.etu.miage.projet_android.adapter.MovieViewAdapter;
import fr.etu.miage.projet_android.model.Movie;
import fr.etu.miage.projet_android.model.MovieDetails;
import fr.etu.miage.projet_android.service.FavoriteService;
import fr.etu.miage.projet_android.service.TmdbService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.etu.miage.projet_android.ui.home.HomeFragment.MOVIE_NAME;

public class FavoriteFragment extends Fragment {
    private RecyclerView view;
    private MovieViewAdapter adapter;
    private GridLayout gridLayout;

    private static String TAG = FavoriteFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        gridLayout = root.findViewById(R.id.gridLayout);
        view = root.findViewById(R.id.favorites);

        //Initialize layoutManager, grid layout with 3 movie cards per line
        view.setLayoutManager(new GridLayoutManager(root.getContext(),3));

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
                    adapter.removeMovie(movie);
                }
            }
        };
        adapter = new MovieViewAdapter(new ArrayList<Movie>(),onItemClickListener,favOnClickListener);

        //Set the adapter
        view.setAdapter(adapter);

        fetchFavoritesMoviesData();

        return root;
    }

    private void fetchFavoritesMoviesData() {
        final ArrayList<Integer> favs = FavoriteService.getFavoritesMovies(this.getContext());
        //final ArrayList<Movie> moviesFav = new ArrayList<>();
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        for(final Integer fav: favs) {
            tmdbService.getMovieDetails(fav,TmdbService.API_KEY, "fr-FR", null).enqueue(new Callback<MovieDetails>() {
                @Override
                public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        //Manage data
                        MovieDetails movieDetails = response.body();
                        Movie movie = new Movie(movieDetails.getPopularity(),movieDetails.getVoteCount(),movieDetails.getVideo(),movieDetails.getPosterPath(),movieDetails.getId(),movieDetails.getAdult(),movieDetails.getBackdropPath(),
                                movieDetails.getOriginalLanguage(),movieDetails.getOriginalTitle(),null, movieDetails.getTitle(),movieDetails.getVoteAverage(),movieDetails.getOverview(),movieDetails.getReleaseDate());
                        adapter.addMovie(movie);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MovieDetails> call, Throwable t) {
                    Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                    Log.d(TAG, t.getMessage());
                //Manage errors
                }
            });
        }
    }
}