package com.bluetoothvehiclemonitor.btvm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.util.TestingUtil;
import com.bluetoothvehiclemonitor.btvm.viewmodels.MetricsViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MetricsFragment extends Fragment {
    private static final String TAG = "MetricsFragment";

    /**
     * 1) Get metric units and display correctly
     * 2) get all Metrics from Room
     * 3) Use ViewModel to get Metrics from Room and display
     * 4) Retrofit -> Grab static map and display polyline trip on image -> Glide
     */

    View mView;
    RecyclerView mRecyclerView;
    MetricsAdapter mMetricsAdapter;

    List<Metrics> mMetricsList;
    TextView mNoMetrics;

    MetricsViewModel mMetricsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMetricsViewModel = ViewModelProviders.of(this).get(MetricsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_metrics, container, false);
        BaseActivity.setTitle(getActivity(), R.string.metrics_title);
        mMetricsList = TestingUtil.getMockMetrics();
        initRecycler();
        return mView;
    }

    private void initRecycler() {
        mRecyclerView = mView.findViewById(R.id.metrics_recycler_view);
        mNoMetrics = mView.findViewById(R.id.metrics_none);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMetricsAdapter = new MetricsAdapter(mMetricsList, getContext());
        mRecyclerView.setAdapter(mMetricsAdapter);
        mMetricsAdapter.notifyDataSetChanged();
        if(mMetricsList.isEmpty()) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mNoMetrics.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoMetrics.setVisibility(View.INVISIBLE);
        }
    }
}
