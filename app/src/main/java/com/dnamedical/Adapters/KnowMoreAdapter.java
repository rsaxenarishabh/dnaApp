package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dnamedical.Models.Faculty;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.dnamedical.R;

public class KnowMoreAdapter extends RecyclerView.Adapter<KnowMoreAdapter.ViewHolder> {
    private Context context;
    private List<Faculty> facultyDetailList;


    public KnowMoreAdapter(Context context) {
        this.context = context;
    }

    public void setFacultyDetailList(List<Faculty> facultyDetailList) {
        this.facultyDetailList = facultyDetailList;
    }


    @NonNull
    @Override
    public KnowMoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_knowmore_item, viewGroup, false);
        return new KnowMoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Faculty faculty = facultyDetailList.get(position);
       holder.facultyName.setText(faculty.getFName());
       holder.facultySubtitile.setText(faculty.getFDeg());

        Picasso.with(context)
                .load(faculty.getFImage())
                .error(R.drawable.profile_image_know_more)
                .into(holder.facultyImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.imageLoader.setVisibility(View.GONE);
                    }
                });


    }


    @Override
    public int getItemCount() {

        if (facultyDetailList != null) {
            return facultyDetailList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView facultyImage;
        private ProgressBar imageLoader;
        private TextView facultyName, facultySubtitile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            facultyImage = itemView.findViewById(R.id.facultyprofile);
            facultyName = itemView.findViewById(R.id.facultyname);
            imageLoader = itemView.findViewById(R.id.imageloader);
            facultySubtitile = itemView.findViewById(R.id.medicine);

        }
    }
}