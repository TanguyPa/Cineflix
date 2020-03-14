package fr.etu.miage.projet_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.etu.miage.projet_android.R;
import fr.etu.miage.projet_android.model.Cast;
import fr.etu.miage.projet_android.model.Movie;

public class CastingViewAdapter extends RecyclerView.Adapter<CastingViewAdapter.ViewHolder> {

    private List<Cast> casting;

    public CastingViewAdapter(List<Cast> movies){
        this.casting = movies;
    }

    public void addCastList(List<Cast> castList) {
        this.casting.addAll(castList);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_card, parent, false);
        return new CastingViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Cast cast = casting.get(position);
        holder.actorName.setText(cast.getName());
        holder.actorCharacter.setText(cast.getCharacter());
        //Debug
        //Picasso.get().setLoggingEnabled(true);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+cast.getProfilePath())
                .placeholder( R.drawable.progress_animation)
                .error(R.drawable.ic_account_box_black_24dp)
                .into(holder.actorImg);
    }

    @Override
    public int getItemCount() {
        return casting.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView actorCharacter;
        TextView actorName;
        ImageView actorImg;

        public ViewHolder(View itemView) {
            super(itemView);
            actorCharacter = itemView.findViewById(R.id.txtCharacter);
            actorName = itemView.findViewById(R.id.txtName);
            actorImg = itemView.findViewById(R.id.imgActor);
        }

    }
}