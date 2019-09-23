package com.bluetoothvehiclemonitor.btvm.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.util.TestingUtil;
import com.bluetoothvehiclemonitor.btvm.viewmodels.MetricsViewModel;
import com.bluetoothvehiclemonitor.btvm.viewmodels.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

public class MetricsFragment extends DaggerFragment {
    private static final String TAG = "MetricsFragment";

    @Inject ViewModelProviderFactory mProviderFactory;
    @Inject MetricsAdapter mMetricsAdapter;

    View mView;
    RecyclerView mRecyclerView;

    List<Trip> mTripList = new ArrayList<>();
    TextView mNoMetrics;

    MetricsViewModel mMetricsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMetricsViewModel = ViewModelProviders.of(this, mProviderFactory).get(MetricsViewModel.class);
        Log.i(TAG, mMetricsViewModel.getSharedPrefsString());
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

        mMetricsAdapter.setTrips(mTripList, mMetricsViewModel.isMetric());
        mRecyclerView.setAdapter(mMetricsAdapter);
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
                Log.i(TAG, "init subscribe");
                mTripList = trips;//TestingUtil.getListWithInvalidTripsMixedIn();
                if(checkIfMetricsAreValid()) {
                    initRecycler();
                    mMetricsAdapter.setTrips(mTripList, mMetricsViewModel.isMetric());
                }
            }
        });
    }

    private boolean checkIfMetricsAreValid() {
        Log.i(TAG+" check", mTripList.toString());
        List<Trip> temps = new ArrayList<>();
        if(mTripList.size() <= 0) {
            return false;
        } else {
            Log.i(TAG+" size", String.valueOf(mTripList.size()));
            for(Trip t:mTripList) {
                if(t.getMetrics().getDistance() != null &&
                        t.getMetrics().getVehicleSpeed() != null &&
                        t.getMetrics().getEngineRPM() != null &&
                        t.getMetrics().getCoolantTemp() != null &&
                        t.getMetrics().getAirFlow() != null) {
                    temps.add(t);
                    Log.i(TAG+" add", t.toString());
                }
            }
            if(temps.size() > 0) {
                mTripList = temps;
                Log.i(TAG+" true", String.valueOf(mTripList.size()));
                return true;
            } else {
                Log.i(TAG+" false", String.valueOf(mTripList.size()));
                return false;
            }
        }
    }
}
