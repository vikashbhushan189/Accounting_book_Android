package vikashbhushan.example.accountingbook;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {

    private Context mCtx;
    private List<Artist> artistList;

    public ArtistsAdapter(Context mCtx, List<Artist> artistList) {
        this.mCtx = mCtx;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_artists, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artistList.get(position);
        holder.textViewName.setText(artist.name);
        holder.textViewGenre.setText("UserId: " + artist.id);
        holder.textViewAge.setText("Debit: " + artist.debit);
        holder.textViewCountry.setText("Credit: " + artist.credit);
        holder.textViewDate.setText("Date: " + artist.timestamp);
        Picasso.get().load(artist.image).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewGenre, textViewAge, textViewCountry,textViewDate;
        PhotoView photo;
        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewGenre = itemView.findViewById(R.id.text_view_genre);
            textViewAge = itemView.findViewById(R.id.text_view_age);
            textViewCountry = itemView.findViewById(R.id.text_view_country);
            photo= itemView.findViewById(R.id.userImage);
            textViewDate= itemView.findViewById(R.id.text_view_date);

        }
    }
}
