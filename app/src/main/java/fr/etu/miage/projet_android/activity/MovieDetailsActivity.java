package fr.etu.miage.projet_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import fr.etu.miage.projet_android.R;
import fr.etu.miage.projet_android.RetrofitClient;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieImage = findViewById(R.id.movieImage);
        movieTitle = findViewById(R.id.textMovieTitle);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            int name = bundle.getInt(MOVIE_NAME);
            fetchMovieDetailsData(name);
        }
    }

    private void fetchMovieDetailsData(int movieId) {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getMovieDetails(movieId,"1abe855bc465dce9287da07b08a664eb", "fr-FR", null).enqueue(new Callback<MovieDetails>() {

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
                .error(R.drawable.ic_home_black_24dp)
                .into(movieImage);
        movieTitle.setText(movieDetails.getTitle());

    }
}
