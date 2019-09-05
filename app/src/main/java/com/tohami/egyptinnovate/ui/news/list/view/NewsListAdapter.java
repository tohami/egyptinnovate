package com.tohami.egyptinnovate.ui.news.list.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tohami.egyptinnovate.R;
import com.tohami.egyptinnovate.data.model.NewsItem;
import com.tohami.egyptinnovate.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsItemHolder> {

    private final List<NewsItem> newsItemList;
    private final OnItemClickedListener onItemClickListener;

    public NewsListAdapter(List<NewsItem> list, OnItemClickedListener clickListener) {
        newsItemList = new ArrayList<>(list);
        this.onItemClickListener = clickListener;
    }

    public void addItems(List<NewsItem> list) {
        newsItemList.addAll(list);
        notifyDataSetChanged();
    }

    public void replaceItems(List<NewsItem> list) {
        newsItemList.clear();
        newsItemList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        newsItemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    @NonNull
    @Override
    public NewsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsItemHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemHolder holder, int position) {
        holder.bind(newsItemList.size() > position ? newsItemList.get(position) : null);
    }

    /**
     * View Holder for a {@link NewsItem} RecyclerView list item.
     */
    public class NewsItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView ivNewsImage;
        ImageView ivNewsTypeLabel ;
        TextView tvNewsTitle ;
        TextView tvNewsDate ;
        TextView tvNewsPublishLikes ;
        TextView tvNewsPublishViews ;

        NewsItemHolder(View rootView) {
            super(rootView);
            itemView.setVisibility(View.VISIBLE);
            ivNewsImage = rootView.findViewById(R.id.iv_news_image);
            ivNewsTypeLabel = rootView.findViewById(R.id.iv_news_type_label);
            tvNewsTitle = rootView.findViewById(R.id.tv_news_title);
            tvNewsDate = rootView.findViewById(R.id.tv_news_date);
            tvNewsPublishLikes = rootView.findViewById(R.id.tv_news_likes);
            tvNewsPublishViews = rootView.findViewById(R.id.tv_news_views);

            itemView.setOnClickListener(this);
        }

        private void bind(NewsItem item) {

            if (item != null) {
                tvNewsTitle.setText(item.getNewsTitle());
                tvNewsDate.setText(item.getPostDate());
                tvNewsPublishLikes.setText(itemView.getContext().getString(R.string.likes ,item.getLikes()));
                tvNewsPublishViews.setText(itemView.getContext().getString(R.string.views ,item.getNumofViews()));

                if(item.getImageUrl() == null || item.getImageUrl().isEmpty())
                    ivNewsImage.setImageResource(R.drawable.news_temp_image);
                else {
                    Picasso.get().load(item.getImageUrl())
                            .error(R.drawable.news_temp_image)
                            .placeholder(R.drawable.news_temp_image)
                            .into(ivNewsImage);
                }

                if(item.getNewsType().equals(Constants.News.TYPE_ARTICLE)){
                    ivNewsTypeLabel.setImageResource(R.drawable.news_article_label);
                }else{
                    ivNewsTypeLabel.setImageResource(R.drawable.news_video_label);
                }
                animateViewHolder();
            }
        }

        private void animateViewHolder() {
            LayoutAnimationController itemAnimator = AnimationUtils.loadLayoutAnimation(itemView.getContext(), R.anim.partial_in_from_left_animation);
            Animation labelAnimator = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.partial_in_from_right);
            itemView.startAnimation(itemAnimator.getAnimation()) ;
            ivNewsTypeLabel.startAnimation(labelAnimator) ;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            if (getAdapterPosition() > RecyclerView.NO_POSITION && getAdapterPosition() < newsItemList.size()) {
                NewsItem newsItem = newsItemList.get(getAdapterPosition());
                onItemClickListener.onItemClicked(view , newsItem);
            }
        }
    }


    public interface OnItemClickedListener {
        void onItemClicked(View view , NewsItem item);
    }
}