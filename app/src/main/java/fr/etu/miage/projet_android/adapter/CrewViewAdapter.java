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
import fr.etu.miage.projet_android.model.Crew;

public class CrewViewAdapter extends RecyclerView.Adapter<CrewViewAdapter.ViewHolder> {

    private List<Crew> crew;

    public CrewViewAdapter(List<Crew> crew){
        this.crew = crew;
    }

    public void addCastList(List<Crew> crew) {
        this.crew.addAll(crew);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crew_card, parent, false);
        return new CrewViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Crew crewMember = crew.get(position);
        holder.memberName.setText(crewMember.getName());
        holder.memberJob.setText(crewMember.getJob());
        //Debug
        //Picasso.get().setLoggingEnabled(true);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+crewMember.getProfilePath())
                .placeholder( R.drawable.progress_animation)
                .error(R.drawable.ic_account_box_black_24dp)
                .into(holder.memberImg);
    }

    @Override
    public int getItemCount() {
        return crew.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView memberName;
        TextView memberJob;
        ImageView memberImg;

        public ViewHolder(View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.txtName);
            memberJob = itemView.findViewById(R.id.txtJob);
            memberImg = itemView.findViewById(R.id.imgMember);
        }

    }
}