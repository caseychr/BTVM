package com.bluetoothvehiclemonitor.btvm.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.viewmodels.SettingsViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";

    /**
     * 1) Animation from SettingsFragment to HomeFragment
     * 2) Preset already chosen BT Device
     * 4) Don't show doubles
     * 5) Block device choice when already started
     */

    View mView;
    DeviceAdapter mDeviceAdapter;
    Switch mIsMetric;
    RecyclerView mRecyclerView;
    Button mUpdateBtn;

    private boolean f = false;

    public SettingsViewModel mSettingsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        mIsMetric = mView.findViewById(R.id.switch_metric);
        BaseActivity.setTitle(getActivity(), R.string.settings_title);
        initWidgets();
        return mView;
    }

    private void initWidgets() {
        mIsMetric.setChecked(mSettingsViewModel.isMetric());
        mIsMetric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                b = mSettingsViewModel.isMetric();
            }
        });
        mUpdateBtn = mView.findViewById(R.id.btn_update);
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSettingsViewModel.setIsMetric(mIsMetric.isChecked());
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.homeFragment);
            }
        });
        initRecyclerView();
    }



    private void initRecyclerView() {
        mRecyclerView = mView.findViewById(R.id.recycler_view_devices);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(mDeviceAdapter == null) {
            mDeviceAdapter = new DeviceAdapter(mSettingsViewModel.getDevices(), getContext());
            mRecyclerView.setAdapter(mDeviceAdapter);
        }
        mDeviceAdapter.notifyDataSetChanged();
        mDeviceAdapter.setOnItemClickListener(new DeviceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if(BaseActivity.mStartPressed) {
                    BaseActivity.sBluetoothDevice = mSettingsViewModel.getDevices().get(position);
                    mSettingsViewModel.setDevice(BaseActivity.sBluetoothDevice.getName(), BaseActivity.sBluetoothDevice.getAddress());
                    Toast.makeText(getActivity(), "Connecting to "+BaseActivity.sBluetoothDevice.getName(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
