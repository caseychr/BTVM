package com.bluetoothvehiclemonitor.btvm.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.services.GPSService;
import com.bluetoothvehiclemonitor.btvm.viewmodels.MainViewModel;
import com.bluetoothvehiclemonitor.btvm.viewmodels.ViewModelProviderFactory;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        BottomSheetDialog.BottomSheetListener {
    private static final String TAG = "MainActivity";

    @Inject ViewModelProviderFactory mProviderFactory;

    private NavigationView mNavigationView;
    private AppBarConfiguration mAppBarConfiguration;

    IntentFilter mIntentFilter;

    private MainViewModel mMainViewModel;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.nav_view);
        initNavigation();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(GPSService.BROADCAST_ACTION);
        mIntentFilter.addCategory(GPSService.BROADCAST_CATEGORY);

        mMainViewModel = ViewModelProviders.of(this, mProviderFactory).get(MainViewModel.class);
        Log.i(TAG, mMainViewModel.getSharedPrefsString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        if(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getFragments().get(0)
                instanceof SettingsFragment) {
            menu.getItem(0).setTitle(getString(R.string.metrics_title));
        } else if(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getFragments().get(0)
                instanceof MetricsFragment) {
            menu.setGroupVisible(0, false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item: {
                if(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getFragments().get(0)
                 instanceof HomeFragment) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.settingsFragment);
                } else if(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getFragments().get(0)
                        instanceof SettingsFragment) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.metricsFragment);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initNavigation() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(mNavigationView, navController);
        mNavigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home: {
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.main, true).build();
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFragment, null, navOptions);
                break;
            }
            case R.id.nav_settings: {
                if(isValidDestination(R.id.settingsFragment)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.settingsFragment);
                }
                break;
            }
            case R.id.nav_metrics: {
                if(isValidDestination(R.id.metricsFragment)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.metricsFragment);
                }
                break;
            }
            case R.id.menu_item: {
                if(Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().equals(HomeFragment.class)) {
                    if(isValidDestination(R.id.menu_item)) {
                        Navigation.findNavController(this, R.id.nav_host_fragment)
                                .navigate(R.id.settingsFragment);
                    }
                } else if(Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination()
                        .equals(SettingsFragment.class)) {
                    if(isValidDestination(R.id.menu_item)) {
                        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.metricsFragment);
                    }
                }
                break;
            }
        }
        menuItem.setChecked(true);
        return true;
    }

    private boolean isValidDestination(int destination) {
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), mAppBarConfiguration);
    }

    @Override
    public String updateDialogText() {
        return "Unable to Connect with device.\nCheck that the device is within range.";
    }

    @Override
    public String updateButtonText() {
        return getString(R.string.perms_btn_bt_choose_device);
    }

    @Override
    public void onButtonClicked() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(intent);
    }
}
