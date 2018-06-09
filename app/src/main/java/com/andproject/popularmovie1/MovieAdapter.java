package com.andproject.popularmovie1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.andproject.popularmovie1.model.mdMovie;
import com.squareup.picasso.Picasso;
import android.support.v7.widget.RecyclerView;

/**
 * Created on 20180513.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private mdMovie[] mvMovies;

    private final MovieAdapterOnClickHandler clickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(mdMovie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void setMovies(mdMovie[] mvMovies) {
        this.mvMovies = mvMovies;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_items, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        mdMovie movie = mvMovies[position];
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (mvMovies == null) return 0;
        return mvMovies.length;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        ImageView ivMovieImg;
        TextView tvTitleTxt;

        public MovieViewHolder(View view) {
            super(view);
            context = view.getContext();
            ivMovieImg = (ImageView) view.findViewById(R.id.image_movie);
            tvTitleTxt = (TextView) view.findViewById(R.id.txtTitle);
            view.setOnClickListener(this);
        }

        void bind(mdMovie movie) {
            Picasso.with(context)
                    .load(movie.getPosterPath())
                    .into(ivMovieImg);
            tvTitleTxt.setText(movie.getTitle());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mdMovie movie = mvMovies[position];
            clickHandler.onClick(movie);
        }
    }
}
