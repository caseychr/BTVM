package com.bluetoothvehiclemonitor.btvm.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.util.ConverterUtil;
import com.bluetoothvehiclemonitor.btvm.util.DateUtil;
import com.bluetoothvehiclemonitor.btvm.util.MetricsUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MetricsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MetricsAdapter";

    public static final int OVERALL = 0;
    public static final int METRIC = 1;

    boolean isMetric;
    List<Trip> mTripList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == OVERALL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.metrics_total_list_item, parent, false);
            return new OverallMetricsViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.metrics_list_item_no_map, parent, false);
            return new MetricsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Trip trip = mTripList.get(position);
        if(getItemViewType(position) == OVERALL) {
            if(isMetric) {
                ((OverallMetricsViewHolder) holder).mTvDistanceNum.setText(trip.getMetrics().getDistance());
                ((OverallMetricsViewHolder) holder).mTvAirflowNum.setText(trip.getMetrics().getAirFlow());
                ((OverallMetricsViewHolder) holder).mTvCoolantNum.setText(trip.getMetrics().getCoolantTemp());
                ((OverallMetricsViewHolder) holder).mTvRPMNum.setText(trip.getMetrics().getEngineRPM());
                ((OverallMetricsViewHolder) holder).mTvSpeedNum.setText(trip.getMetrics().getVehicleSpeed());
                ((OverallMetricsViewHolder) holder).mTvDistanceUnit.setText(R.string.metric_distance);
                ((OverallMetricsViewHolder) holder).mTvSpeedUnit.setText(R.string.metric_speed);
                ((OverallMetricsViewHolder) holder).mTvCoolantUnit.setText(R.string.metric_coolant);
                ((OverallMetricsViewHolder) holder).mTvAirflowUnit.setText(R.string.metric_airflow);
            } else {
                ((OverallMetricsViewHolder) holder).mTvDistanceNum.setText(ConverterUtil
                        .convertKMtoMiles(trip.getMetrics().getDistance()));
                ((OverallMetricsViewHolder) holder).mTvAirflowNum.setText(ConverterUtil
                        .convertGramsToOunces(trip.getMetrics().getAirFlow()));
                ((OverallMetricsViewHolder) holder).mTvCoolantNum.setText(ConverterUtil
                        .convertCelsiusToFahrenheit(trip.getMetrics().getCoolantTemp()));
                ((OverallMetricsViewHolder) holder).mTvRPMNum.setText(trip.getMetrics().getEngineRPM());
                ((OverallMetricsViewHolder) holder).mTvSpeedNum.setText(ConverterUtil
                        .convertKMtoMiles(trip.getMetrics().getVehicleSpeed()));
                ((OverallMetricsViewHolder) holder).mTvDistanceUnit.setText(R.string.imperial_distance);
                ((OverallMetricsViewHolder) holder).mTvSpeedUnit.setText(R.string.imperial_speed);
                ((OverallMetricsViewHolder) holder).mTvCoolantUnit.setText(R.string.imperial_coolant);
                ((OverallMetricsViewHolder) holder).mTvAirflowUnit.setText(R.string.imperial_airflow);
            }
        } else {
            if(isMetric) {
                ((MetricsViewHolder) holder).mTvDistanceNum.setText(trip.getMetrics().getDistance());
                ((MetricsViewHolder) holder).mTvAirflowNum.setText(trip.getMetrics().getAirFlow());
                ((MetricsViewHolder) holder).mTvCoolantNum.setText(trip.getMetrics().getCoolantTemp());
                ((MetricsViewHolder) holder).mTvRPMNum.setText(trip.getMetrics().getEngineRPM());
                ((MetricsViewHolder) holder).mTvSpeedNum.setText(trip.getMetrics().getVehicleSpeed());
                ((MetricsViewHolder) holder).mTvDistanceUnit.setText(R.string.metric_distance);
                ((MetricsViewHolder) holder).mTvSpeedUnit.setText(R.string.metric_speed);
                ((MetricsViewHolder) holder).mTvCoolantUnit.setText(R.string.metric_coolant);
                ((MetricsViewHolder) holder).mTvAirflowUnit.setText(R.string.metric_airflow);
            } else {
                ((MetricsViewHolder) holder).mTvDistanceNum.setText(ConverterUtil
                        .convertKMtoMiles(trip.getMetrics().getDistance()));
                ((MetricsViewHolder) holder).mTvAirflowNum.setText(ConverterUtil
                        .convertGramsToOunces(trip.getMetrics().getAirFlow()));
                ((MetricsViewHolder) holder).mTvCoolantNum.setText(ConverterUtil
                        .convertCelsiusToFahrenheit(trip.getMetrics().getCoolantTemp()));
                ((MetricsViewHolder) holder).mTvRPMNum.setText(trip.getMetrics().getEngineRPM());
                ((MetricsViewHolder) holder).mTvSpeedNum.setText(ConverterUtil
                        .convertKMtoMiles(trip.getMetrics().getVehicleSpeed()));
                ((MetricsViewHolder) holder).mTvDistanceUnit.setText(R.string.imperial_distance);
                ((MetricsViewHolder) holder).mTvSpeedUnit.setText(R.string.imperial_speed);
                ((MetricsViewHolder) holder).mTvCoolantUnit.setText(R.string.imperial_coolant);
                ((MetricsViewHolder) holder).mTvAirflowUnit.setText(R.string.imperial_airflow);
            }

            ((MetricsViewHolder) holder).mTimeStamp.setText("Trip at "+trip.getTimeStamp());

            /*RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_btvm);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load("")
                    .into(((MetricsViewHolder)holder).mStaticMap);*/

        }
    }

    @Override
    public int getItemCount() {
        return mTripList.size();
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

    public void setTrips(List<Trip> trips, boolean isMetric) {
        this.isMetric = isMetric;
        mTripList = trips;
        setOverallMetric();
        notifyDataSetChanged();
    }

    public void setOverallMetric() {
        Metrics metrics = MetricsUtil.getOverallMetrics(mTripList);
        Trip trip = new Trip(DateUtil.getStringFromCurrentDate());
        trip.setMetrics(metrics);
        mTripList.add(0, trip);
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
        //ImageView mStaticMap;

        TextView mTvDistanceUnit;
        TextView mTvAirflowUnit;
        TextView mTvCoolantUnit;
        TextView mTvRPMUnit;
        TextView mTvSpeedUnit;

        TextView mTimeStamp;

        public MetricsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDistanceNum = itemView.findViewById(R.id.tv_metric_distance_num);
            mTvAirflowNum = itemView.findViewById(R.id.tv_metric_airflow_num);
            mTvCoolantNum = itemView.findViewById(R.id.tv_metric_coolant_num);
            mTvRPMNum = itemView.findViewById(R.id.tv_metric_rpm_num);
            mTvSpeedNum = itemView.findViewById(R.id.tv_metric_speed_num);
            //mStaticMap = itemView.findViewById(R.id.img_metric_static_map);

            mTvDistanceUnit = itemView.findViewById(R.id.tv_metric_distance_unit);
            mTvAirflowUnit = itemView.findViewById(R.id.tv_metric_airflow_unit);
            mTvCoolantUnit = itemView.findViewById(R.id.tv_metric_coolant_unit);
            mTvRPMUnit = itemView.findViewById(R.id.tv_metric_rpm_unit);
            mTvSpeedUnit = itemView.findViewById(R.id.tv_metric_speed_unit);

            mTimeStamp = itemView.findViewById(R.id.tv_metric_date);
        }
    }
}
