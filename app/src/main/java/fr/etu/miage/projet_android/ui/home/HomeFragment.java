package fr.etu.miage.projet_android.ui.home;

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
import fr.etu.miage.projet_android.model.Movie;
import fr.etu.miage.projet_android.model.PopularCollection;
import fr.etu.miage.projet_android.model.UpcomingCollection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView popularView;
    private RecyclerView upcomingView;
    private MovieViewAdapter popularAdapter;
    private MovieViewAdapter upcomingAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        popularView = root.findViewById(R.id.popular);
        upcomingView = root.findViewById(R.id.upcoming);
        //Initialize layoutManager with a LinearLayoutManager, by default vertical
        popularView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));

        //Create adapter with the OnItemClickListener implementation
        popularAdapter = new MovieViewAdapter(new ArrayList<Movie>(), null);
        upcomingAdapter = new MovieViewAdapter(new ArrayList<Movie>(), null);
        //Set the adapter
        popularView.setAdapter(popularAdapter);
        upcomingView.setAdapter(upcomingAdapter);

        fetchPopularMoviesData();
        fetchUpComingMoviesData();

        return  root;
    }



    private void fetchPopularMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getPopular("1abe855bc465dce9287da07b08a664eb", "fr-FR", null, null).enqueue(new Callback<PopularCollection>() {

            @Override
            public void onResponse(Call<PopularCollection> call, Response<PopularCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    PopularCollection collection = response.body();
                    popularAdapter.addFeatureList(collection.getResults());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PopularCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
                Log.d(TAG, t.getCause().getMessage());
//Manage errors
            }
        });
    }

    private void fetchUpComingMoviesData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getUpcoming("1abe855bc465dce9287da07b08a664eb", "fr-FR", null, null).enqueue(new Callback<UpcomingCollection>() {

            @Override
            public void onResponse(Call<UpcomingCollection> call, Response<UpcomingCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    UpcomingCollection collection = response.body();
                    upcomingAdapter.addFeatureList(collection.getResults());

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpcomingCollection> call, Throwable t) {
                Toast.makeText(getContext(), TAG,Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
                Log.d(TAG, t.getCause().getMessage());
//Manage errors
            }
        });
    }
}