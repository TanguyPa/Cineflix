package fr.etu.miage.projet_android;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import fr.etu.miage.projet_android.model.PopularCollection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void fetchMetromobiliteData() {
        TmdbService tmdbService = RetrofitClient.getInstance().create(TmdbService.class);
        tmdbService.getPopular("1abe855bc465dce9287da07b08a664eb", null, null, null).enqueue(new Callback<PopularCollection>() {

            @Override
            public void onResponse(Call<PopularCollection> call, Response<PopularCollection> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    PopularCollection collection = response.body();
                    System.out.println(collection.getTotalResults());

                    System.out.println(collection.getResults().get(0).getTitle());
                    //recyclerAdapter.addFeatureList(collection.getFeatureList());
                } else {
                    Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PopularCollection> call, Throwable t) {
                //Manage errors
            }
        });
    }
}
