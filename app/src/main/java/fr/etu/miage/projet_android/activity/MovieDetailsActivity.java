package fr.etu.miage.projet_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.etu.miage.projet_android.R;
import fr.etu.miage.projet_android.service.RetrofitClient;
import fr.etu.miage.projet_android.adapter.CastingViewAdapter;
import fr.etu.miage.projet_android.adapter.CrewViewAdapter;
import fr.etu.miage.projet_android.model.Cast;
import fr.etu.miage.projet_android.model.Crew;
import fr.etu.miage.projet_android.service.TmdbService;
import fr.etu.miage.projet_android.model.MovieDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.etu.miage.projet_android.ui.home.HomeFragment.MOVIE_NAME;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private ImageView movieImage;
    private TextView movieTitle;
    private RecyclerView castingView;
    private RecyclerView crewView;
    private CastingViewAdapter castingAdapter;
    private CrewViewAdapter crewAdapter;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieImage = findViewById(R.id.movieImage);
        movieTitle = findViewById(R.id.textMovieTitle);
        ratingBar = findViewById(R.id.ratingBar);

        castingView = this.findViewById(R.id.recyclerViewCasting);
        crewView = this.findViewById(R.id.recyclerViewCrew);
        //Initialize layoutManager with a LinearLayoutManager, by default horizontal
        castingView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        crewView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //Create adapter
        castingAdapter = new CastingViewAdapter(new ArrayList<Cast>());
        crewAdapter = new CrewViewAdapter(new ArrayList<Crew>());

        castingView.setAdapter(castingAdapter);
        crewView.setAdapter(crewAdapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            //Get movie id pass to intent from last activity or fragment
            int name = bundle.getInt(MOVIE_NAME);
            fetchMovieDetailsData(name);
        }
    }

    private void fetchMovieDetailsData(int movieId) {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getMovieDetails(movieId,TmdbService.API_KEY, "fr-FR", "credits").enqueue(new Callback<MovieDetails>() {

            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MovieDetails movieDetails = response.body();
                    displayData(movieDetails);
                } else {
                    Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(), TAG +"Error on failure", Toast.LENGTH_LONG).show();
//Manage errors
            }
        });
    }

    private void displayData(MovieDetails movieDetails) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movieDetails.getBackdropPath())
                .placeholder( R.drawable.progress_animation)
                .error(R.drawable.ic_account_box_black_24dp)
                .into(movieImage);
        movieTitle.setText(movieDetails.getTitle());
        ratingBar.setRating((float) (movieDetails.getVoteAverage()/2));
        castingAdapter.addCastList(movieDetails.getCredits().getCast());
        crewAdapter.addCastList(movieDetails.getCredits().getCrew());
    }
}
