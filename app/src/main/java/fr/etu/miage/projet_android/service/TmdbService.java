package fr.etu.miage.projet_android.service;

import fr.etu.miage.projet_android.model.MovieDetails;
import fr.etu.miage.projet_android.model.MoviesCollection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TmdbService {
    @GET("movie/popular")
    Call<MoviesCollection> getPopular(@Query("api_key") String apiKey,@Query("language") String language, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/top_rated")
    Call<MoviesCollection> getTopRated(@Query("api_key") String apiKey,@Query("language") String language, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/upcoming")
    Call<MoviesCollection> getUpcoming(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/now_playing")
    Call<MoviesCollection> getNowPlaying(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("language") String language, @Query("append_to_response") String appendToResponse);
}