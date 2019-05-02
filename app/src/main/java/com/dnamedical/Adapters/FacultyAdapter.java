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

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.dnamedical.Models.faculties.Faculty;
import com.dnamedical.R;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.ViewHolder> {

    private Context context;
    private List<Faculty> facultyDetailList;


    public FacultyAdapter(Context context) {
        this.context = context;
    }

    public void setFacultyDetailList(List<Faculty> facultyDetailList) {
        this.facultyDetailList = facultyDetailList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_faculty_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Faculty faculty = facultyDetailList.get(i);
        holder.facultyName.setText(faculty.getFName());
        holder.facultyProfile.setText(faculty.getFDeg());
        holder.facultySubtitile.setText(faculty.getFDesc());
        holder.facultyQuotes.setText(faculty.getFQuote());
        /*Picasso.with(context)
                .load(faculty.getFImage())
                .into(holder.facultyImage);
*/
        Picasso.with(context)
                .load(faculty.getFImage())
                .error(R.drawable.profile_pictures)
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
//ghjgjhgj   vkbdskbfkjdhfkjhhdajhjkhdfdfdsfsfd
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView facultyImage;
        private ProgressBar imageLoader;
        private TextView facultyName, facultyProfile, facultyQuotes, facultySubtitile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            facultyQuotes = itemView.findViewById(R.id.faculty_quotes);
            facultyProfile = itemView.findViewById(R.id.faculty_prfile);
            imageLoader = itemView.findViewById(R.id.imageloader);
            facultyName = itemView.findViewById(R.id.faculty_name);
            facultySubtitile = itemView.findViewById(R.id.faculty_sub_title);
            facultyImage = itemView.findViewById(R.id.faculty_image);


        }
    }
}