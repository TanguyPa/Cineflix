package fr.etu.miage.projet_android;

import fr.etu.miage.projet_android.model.PopularCollection;
import fr.etu.miage.projet_android.model.UpcomingCollection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TmdbService {
    @GET("movie/popular")
    Call<PopularCollection> getPopular(@Query("api_key") String apiKey,@Query("language") String language, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/upcoming")
    Call<UpcomingCollection> getUpcoming(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page, @Query("region") String region);
}