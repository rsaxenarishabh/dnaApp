package com.dnamedical.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.dnamedical.Models.video.Free;
import com.dnamedical.R;

public class VideoListFreeAdapter extends RecyclerView.Adapter<VideoListFreeAdapter.ViewHolder> {
    /**
     * Created by rbpatel on 9/29/2017.
     */

    private Context applicationContext;
    private List<Free> freeList;
    VideoListFreeAdapter.OnCategoryClick onUserClickCallback;

    public VideoListFreeAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public VideoListFreeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_items, viewGroup, false);
        return new VideoListFreeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoListFreeAdapter.ViewHolder holder, final int position) {
        holder.title.setText(freeList.get(holder.getAdapterPosition()).getTitle());
        holder.index.setText("" + (holder.getAdapterPosition() + 1));
        Picasso.with(applicationContext)
                .load(freeList.get(holder.getAdapterPosition()).getDr_img())
                .error(R.drawable.profile_image_know_more)
                .into(holder.imageDoctor);
        holder.doctorName.setText(freeList.get(holder.getAdapterPosition()).getSubTitle());


        holder.row_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickCallback != null) {
                    onUserClickCallback.onCateClick(freeList.get(holder.getAdapterPosition()));
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        if (freeList != null) {
            return freeList.size();
        } else {
            return 0;
        }
    }

    public void setData(List<Free> CourseLists) {
        this.freeList = CourseLists;
    }

    public void setListener(VideoListFreeAdapter.OnCategoryClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.index)
        TextView index;
        @BindView(R.id.row_view)
        LinearLayout row_view;
        @BindView(R.id.vid_title)
        TextView title;
        @BindView(R.id.vid_doctor_name)
        TextView doctorName;

        @BindView(R.id.image_doctor)
        ImageView imageDoctor;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface OnCategoryClick {
        public void onCateClick(Free free);
    }

}
