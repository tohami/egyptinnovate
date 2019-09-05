package com.tohami.egyptinnovate.ui.news.list.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tohami.egyptinnovate.R;
import com.tohami.egyptinnovate.data.model.DataModel;
import com.tohami.egyptinnovate.data.model.NewsItem;
import com.tohami.egyptinnovate.ui.base.BaseFragment;
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModel;
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModelFactory;
import com.tohami.egyptinnovate.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class NewsListFragment extends BaseFragment implements NewsListAdapter.OnItemClickedListener {
    private RecyclerView rvNews;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private TextView tvMsg;
    private CardView msgContainer;
    private NewsListAdapter newsListAdapter;

    private NewsListViewModel mViewModel;
    @Inject
    NewsListViewModelFactory viewModelFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_filter) {
            showSimpleSnack(getString(R.string.filter_clicked));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory)
                .get(NewsListViewModel.class);

        mViewModel.getNewsSubject().subscribe(getNewsConsumer);
    }

    @Override
    protected View initializeViews(int layoutId, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate( layoutId, container, false);
        
        refreshLayout = rootView.findViewById(R.id.refresh_layout) ;
        progressBar = rootView.findViewById(R.id.progress_layout) ;
        rvNews = rootView.findViewById(R.id.rv_news) ;
        tvMsg = rootView.findViewById(R.id.tv_msg) ;
        msgContainer = rootView.findViewById(R.id.msg_container) ;

        newsListAdapter = new NewsListAdapter(new ArrayList<>() , this);
        rvNews.setAdapter(newsListAdapter);
        
        return rootView;
    }

    @Override
    protected void setListeners() {
        refreshLayout.setOnRefreshListener(() -> mViewModel.refreshNewsList());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }


    @Override
    public void onItemClicked(View view , NewsItem item) {
        Bundle args = new Bundle() ;
        args.putString(Constants.News.ARGS_NEWS_ID, item.getNid());
        Navigation
                .findNavController(view)
                .navigate(R.id.action_fragmentNewsList_to_fragmentNewsDetails , args);
    }


    private Consumer<DataModel<List<NewsItem>>> getNewsConsumer = new Consumer<DataModel<List<NewsItem>>>() {
        @Override
        public void accept(DataModel<List<NewsItem>> listDataModel) {
            switch (listDataModel.getStatus()) {
                case SUCCESS:
                    rvNews.setVisibility(View.VISIBLE);
                    msgContainer.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    newsListAdapter.replaceItems(listDataModel.getResponseBody());
                    break;
                case FAIL:
                    rvNews.setVisibility(View.GONE);
                    msgContainer.setVisibility(View.VISIBLE);
                    refreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    tvMsg.setText(listDataModel.getErrorMsg());
                    break;
                case NO_NETWORK:
                    rvNews.setVisibility(View.GONE);
                    msgContainer.setVisibility(View.VISIBLE);
                    refreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    tvMsg.setText(R.string.error_no_internet_connection);
                    break;
                case LOADING:
                    if(!refreshLayout.isRefreshing()){
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    msgContainer.setVisibility(View.GONE);
                    newsListAdapter.replaceItems(new ArrayList<>());
                    break;
                default:
                    newsListAdapter.replaceItems(new ArrayList<>());
            }
        }
    } ;

}
