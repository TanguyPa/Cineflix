package fr.etu.miage.projet_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import fr.etu.miage.projet_android.model.Movie;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.ViewHolder> {

    private List<Movie> movies;
    private AdapterView.OnItemClickListener listener;

    public MovieViewAdapter(List<Movie> movies, AdapterView.OnItemClickListener listener){
        this.listener = listener;
        this.movies = movies;
    }

    public void addFeatureList(List<Movie> featureList) {
        this.movies.addAll(featureList);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popular, parent, false);
        return new MovieViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        //Debug
        //Picasso.get().setLoggingEnabled(true);
        Picasso.get().load("https://image.tmdb.org/t/p/w185"+movie.getPosterPath())
                .placeholder(R.drawable.ic_dashboard_black_24dp)
                .error(R.drawable.ic_home_black_24dp).into(holder.img);
        holder.txt.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
            img = itemView.findViewById(R.id.img);
        }

    }
}