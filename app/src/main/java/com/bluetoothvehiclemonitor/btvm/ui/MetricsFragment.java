package com.bluetoothvehiclemonitor.btvm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.util.TestingUtil;
import com.bluetoothvehiclemonitor.btvm.viewmodels.MetricsViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MetricsFragment extends Fragment {
    private static final String TAG = "MetricsFragment";

    View mView;
    RecyclerView mRecyclerView;
    MetricsAdapter mMetricsAdapter;

    List<Trip> mTripList = new ArrayList<>();
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
        subscribeObservers();
        return mView;
    }

    private void initRecycler() {
        mRecyclerView = mView.findViewById(R.id.metrics_recycler_view);
        mNoMetrics = mView.findViewById(R.id.metrics_none);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(mMetricsAdapter == null) {
            mMetricsAdapter = new MetricsAdapter(mTripList, getContext(), mMetricsViewModel.isMetric());
            mRecyclerView.setAdapter(mMetricsAdapter);
        }
        mMetricsAdapter.notifyDataSetChanged();
        if(mTripList.isEmpty()) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mNoMetrics.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoMetrics.setVisibility(View.INVISIBLE);
        }
    }

    private void subscribeObservers() {
        mMetricsViewModel.getAllTrips().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                mTripList = TestingUtil.getListWithInvalidTripsMixedIn();//trips;
                if(checkIfMetricsAreValid()) {
                    initRecycler();
                    mMetricsAdapter.setTrips(mTripList);
                }
            }
        });
    }

    private boolean checkIfMetricsAreValid() {
        List<Trip> temps = new ArrayList<>();
        if(mTripList.size() <= 0) {
            return false;
        } else {
            for(Trip t:mTripList) {
                if(t.getMetrics().getDistance() != null &&
                        t.getMetrics().getVehicleSpeed() != null &&
                        t.getMetrics().getEngineRPM() != null &&
                        t.getMetrics().getCoolantTemp() != null &&
                        t.getMetrics().getAirFlow() != null) {
                    temps.add(t);
                }
            }
            if(temps.size() > 0) {
                mTripList = temps;
                return true;
            } else {
                return false;
            }
        }
    }
}
