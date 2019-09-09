package com.bluetoothvehiclemonitor.btvm.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.util.ConverterUtil;
import com.bluetoothvehiclemonitor.btvm.util.MetricsUtil;
import com.bluetoothvehiclemonitor.btvm.util.TestingUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MetricsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MetricsAdapter";

    public static final int OVERALL = 0;
    public static final int METRIC = 1;

    List<Metrics> mMetricsList;
    Context mContext;

    public MetricsAdapter(List<Metrics> metrics, Context context) {
        mContext = context;
        mMetricsList = metrics;
        setOverallMetric();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == OVERALL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.metrics_total_list_item, parent, false);
            return new OverallMetricsViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.metrics_list_item, parent, false);
            return new MetricsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Metrics metrics = mMetricsList.get(position);
        if(getItemViewType(position) == OVERALL) {
            ((OverallMetricsViewHolder) holder).mTvDistanceNum.setText(metrics.getDistance());
            ((OverallMetricsViewHolder) holder).mTvAirflowNum.setText(metrics.getAirFlow());
            ((OverallMetricsViewHolder) holder).mTvCoolantNum.setText(metrics.getCoolantTemp());
            ((OverallMetricsViewHolder) holder).mTvRPMNum.setText(metrics.getEngineRPM());
            ((OverallMetricsViewHolder) holder).mTvSpeedNum.setText(metrics.getVehicleSpeed());
        } else {
            ((MetricsViewHolder) holder).mTvDistanceNum.setText(metrics.getDistance());
            ((MetricsViewHolder) holder).mTvAirflowNum.setText(metrics.getAirFlow());
            ((MetricsViewHolder) holder).mTvCoolantNum.setText(metrics.getCoolantTemp());
            ((MetricsViewHolder) holder).mTvRPMNum.setText(metrics.getEngineRPM());
            ((MetricsViewHolder) holder).mTvSpeedNum.setText(metrics.getVehicleSpeed());

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_btvm);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mMetricsList.get(position).getMapImage())
                    .into(((MetricsViewHolder)holder).mStaticMap);

            /**
             *
             * Testing for Byte to Bitmap & Bitmap to Byte
             *
             * Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_btvm);
             *             byte[] bytes = ConverterUtil.getBytesFromBitmap(b);
             *             Log.i(TAG, bytes.toString());
             *             Bitmap bt = ConverterUtil.getBitmapFromBytes(bytes);
             *             ((MetricsViewHolder) holder).mStaticMap.setImageBitmap(bt);
             */
        }
    }

    @Override
    public int getItemCount() {
        return mMetricsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return OVERALL;
        }
        else {
            return METRIC;
        }
    }

    public void setOverallMetric() {
        Metrics metrics = MetricsUtil.getOverallMetrics(TestingUtil.getMockMetrics());
        mMetricsList.add(0, metrics);
    }

    private class OverallMetricsViewHolder extends RecyclerView.ViewHolder {

        TextView mTvDistanceNum;
        TextView mTvAirflowNum;
        TextView mTvCoolantNum;
        TextView mTvRPMNum;
        TextView mTvSpeedNum;

        TextView mTvDistanceUnit;
        TextView mTvAirflowUnit;
        TextView mTvCoolantUnit;
        TextView mTvRPMUnit;
        TextView mTvSpeedUnit;

        public OverallMetricsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDistanceNum = itemView.findViewById(R.id.tv_metric_distance_overall_num);
            mTvAirflowNum = itemView.findViewById(R.id.tv_metric_airflow_overall_num);
            mTvCoolantNum = itemView.findViewById(R.id.tv_metric_coolant_overall_num);
            mTvRPMNum = itemView.findViewById(R.id.tv_metric_rpm_overall_num);
            mTvSpeedNum = itemView.findViewById(R.id.tv_metric_speed_overall_num);

            mTvDistanceUnit = itemView.findViewById(R.id.tv_metric_distance_overall_unit);
            mTvAirflowUnit = itemView.findViewById(R.id.tv_metric_airflow_overall_unit);
            mTvCoolantUnit = itemView.findViewById(R.id.tv_metric_coolant_overall_unit);
            mTvRPMUnit = itemView.findViewById(R.id.tv_metric_rpm_overall_unit);
            mTvSpeedUnit = itemView.findViewById(R.id.tv_metric_speed_overall_unit);
        }
    }

    private class MetricsViewHolder extends RecyclerView.ViewHolder {

        TextView mTvDistanceNum;
        TextView mTvAirflowNum;
        TextView mTvCoolantNum;
        TextView mTvRPMNum;
        TextView mTvSpeedNum;
        ImageView mStaticMap;

        TextView mTvDistanceUnit;
        TextView mTvAirflowUnit;
        TextView mTvCoolantUnit;
        TextView mTvRPMUnit;
        TextView mTvSpeedUnit;

        public MetricsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDistanceNum = itemView.findViewById(R.id.tv_metric_distance_num);
            mTvAirflowNum = itemView.findViewById(R.id.tv_metric_airflow_num);
            mTvCoolantNum = itemView.findViewById(R.id.tv_metric_coolant_num);
            mTvRPMNum = itemView.findViewById(R.id.tv_metric_rpm_num);
            mTvSpeedNum = itemView.findViewById(R.id.tv_metric_speed_num);
            mStaticMap = itemView.findViewById(R.id.img_metric_static_map);

            mTvDistanceUnit = itemView.findViewById(R.id.tv_metric_distance_unit);
            mTvAirflowUnit = itemView.findViewById(R.id.tv_metric_airflow_unit);
            mTvCoolantUnit = itemView.findViewById(R.id.tv_metric_coolant_unit);
            mTvRPMUnit = itemView.findViewById(R.id.tv_metric_rpm_unit);
            mTvSpeedUnit = itemView.findViewById(R.id.tv_metric_speed_unit);
        }
    }
}
