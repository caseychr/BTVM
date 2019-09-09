package com.bluetoothvehiclemonitor.btvm.ui;

import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluetoothvehiclemonitor.btvm.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private static final String TAG = "DeviceAdapter";

    public interface OnItemClickListener{
        void onClick(int position);
    }

    List<BluetoothDevice> mDevices;
    OnItemClickListener mOnItemClickListener;

    int lastCheckedPosition = -1;

    public DeviceAdapter(List<BluetoothDevice> devices) {
        mDevices = devices;
        Log.i(TAG, mDevices.toString());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setConnectedBTDevice(String name, String address) {
        if(name != null && address != null) {
            for(int i=0;i<mDevices.size();i++) {
                if(mDevices.get(i).getName().equals(name) && mDevices.get(i).getAddress().equals(address)) {
                    lastCheckedPosition = i;
                    return;
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BluetoothDevice bluetoothDevice = mDevices.get(i);
        viewHolder.mDeviceName.setText(bluetoothDevice.getName());
        viewHolder.mMacAddress.setText(bluetoothDevice.getAddress());
        viewHolder.mChecked.setVisibility((lastCheckedPosition == viewHolder.getAdapterPosition()) ? View.VISIBLE: View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mDeviceName;
        TextView mMacAddress;
        ImageView mChecked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDeviceName = itemView.findViewById(R.id.list_device_name);
            mMacAddress = itemView.findViewById(R.id.list_mac_address);
            mChecked = itemView.findViewById(R.id.list_device_checked);
        }


        @Override
        public void onClick(View view) {
            lastCheckedPosition = getAdapterPosition();
            mOnItemClickListener.onClick(lastCheckedPosition);
            notifyDataSetChanged();
        }

    }
}
