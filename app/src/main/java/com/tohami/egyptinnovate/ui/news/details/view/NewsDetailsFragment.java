package com.tohami.egyptinnovate.ui.news.details.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;
import com.tohami.egyptinnovate.R;
import com.tohami.egyptinnovate.data.model.DataModel;
import com.tohami.egyptinnovate.data.model.NewsDetails;
import com.tohami.egyptinnovate.ui.base.BaseFragment;
import com.tohami.egyptinnovate.ui.news.details.viewModel.NewsDetailsViewModel;
import com.tohami.egyptinnovate.ui.news.details.viewModel.NewsDetailsViewModelFactory;
import com.tohami.egyptinnovate.utilities.Constants;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class NewsDetailsFragment extends BaseFragment {

    private ImageView ivNewsImage;
    private TextView tvNewsLikes;
    private TextView tvNewsPublishDate;
    private TextView tvNewsViews;
    private TextView tvNewsDescription;
    private View detailsContainer;
    private ProgressBar progressBar;
    private TextView tvMsg;
    private CardView msgContainer;
    @Inject
    NewsDetailsViewModelFactory newsDetailsViewModelFactory;

    ShareCompat.IntentBuilder shareIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            if (shareIntent != null)
                shareIntent.startChooser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String newsId = getNewsIdFromBundle(getArguments());

        NewsDetailsViewModel mViewModel = ViewModelProviders.of(this, newsDetailsViewModelFactory)
                .get(NewsDetailsViewModel.class);

        mViewModel.getNewsDetails(newsId).subscribe(getNewsDetailsConsumer);
    }

    private String getNewsIdFromBundle(@Nullable Bundle args) {
        if (args != null && args.containsKey(Constants.News.ARGS_NEWS_ID)) {
            return args.getString(Constants.News.ARGS_NEWS_ID);
        } else {
            return "-1";
        }
    }

    private void updateUi(NewsDetails details) {
        if (details != null) {
            tvNewsDescription.setText(details.getItemDescription());
            tvNewsPublishDate.setText(details.getPostDate());
            tvNewsLikes.setText(getString(R.string.likes, details.getLikes()));
            tvNewsViews.setText(getString(R.string.likes, details.getNumofViews()));
            if (details.getImageUrl() == null || details.getImageUrl().isEmpty())
                ivNewsImage.setImageResource(R.drawable.details_placeholder);
            else {
                Picasso.get().load(details.getImageUrl())
                        .error(R.drawable.news_temp_image)
                        .placeholder(R.drawable.news_temp_image)
                        .into(ivNewsImage);
            }
            shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                    .setType("text/plain")
                    .setChooserTitle(getString(R.string.share_article_chooser_header))
                    .setText(details.getShareURL());
        } else {
            detailsContainer.setVisibility(View.GONE);
            msgContainer.setVisibility(View.VISIBLE);
            tvMsg.setText(R.string.somethingWrong);
        }
    }

    @Override
    protected View initializeViews(int layoutId, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(layoutId, container, false);

        detailsContainer = rootView.findViewById(R.id.news_details_container);
        tvMsg = rootView.findViewById(R.id.tv_msg);
        msgContainer = rootView.findViewById(R.id.msg_container);
        progressBar = rootView.findViewById(R.id.progress_layout);
        ivNewsImage = rootView.findViewById(R.id.iv_news_image);
        tvNewsPublishDate = rootView.findViewById(R.id.tv_news_date);
        tvNewsLikes = rootView.findViewById(R.id.tv_news_likes);
        tvNewsViews = rootView.findViewById(R.id.tv_news_views);
        tvNewsDescription = rootView.findViewById(R.id.tv_news_description);
        return rootView;
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_details;
    }

    private Consumer<DataModel<NewsDetails>> getNewsDetailsConsumer = new Consumer<DataModel<NewsDetails>>() {
        @Override
        public void accept(DataModel<NewsDetails> detailsDataModel) {
            switch (detailsDataModel.getStatus()) {
                case SUCCESS:
                    detailsContainer.setVisibility(View.VISIBLE);
                    msgContainer.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    updateUi(detailsDataModel.getResponseBody());
                    break;
                case FAIL:
                    detailsContainer.setVisibility(View.GONE);
                    msgContainer.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    tvMsg.setText(detailsDataModel.getErrorMsg());
                    break;
                case NO_NETWORK:
                    detailsContainer.setVisibility(View.GONE);
                    msgContainer.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    tvMsg.setText(R.string.error_no_internet_connection);
                    break;
                case LOADING:
                    msgContainer.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

}
