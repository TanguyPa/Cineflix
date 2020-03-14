package fr.etu.miage.projet_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import fr.etu.miage.projet_android.R;
import fr.etu.miage.projet_android.model.Movie;
import fr.etu.miage.projet_android.service.FavoriteService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.ViewHolder> {

    private List<Movie> movies;
    private OnItemClickListener detailsListener;
    private OnItemClickListener favListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Movie movie);
    }

    public MovieViewAdapter(List<Movie> movies, OnItemClickListener detailsListener, OnItemClickListener favListener){
        this.detailsListener = detailsListener;
        this.favListener = favListener;
        this.movies = movies;
    }

    public void addMovieList(List<Movie> featureList) {
        this.movies.addAll(featureList);
        notifyDataSetChanged();
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
        notifyDataSetChanged();
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);
        return new MovieViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        //Debug
        //Picasso.get().setLoggingEnabled(true);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.getPosterPath())
                .placeholder( R.drawable.progress_animation)
                .error(R.drawable.ic_home_black_24dp).into(holder.img);
        holder.txt.setText(movie.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsListener.onItemClick(v, movie);
            }
        });
        boolean isFavorite = FavoriteService.isMovieFavorite(holder.favImg.getContext(), movie.getId());
        if(isFavorite) {
            holder.favImg.setImageResource(R.drawable.ic_favorite_red_24dp);
        } else {
            holder.favImg.setImageResource(R.drawable.ic_add_black_24dp);
        }

        holder.favImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favListener.onItemClick(v, movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt;
        ImageView img;
        ImageView favImg;

        public ViewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
            img = itemView.findViewById(R.id.img);
            favImg = itemView.findViewById(R.id.favImg);
        }

    }
}