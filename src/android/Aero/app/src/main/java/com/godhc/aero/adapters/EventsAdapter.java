package com.godhc.aero.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.godhc.aero.R;
import com.godhc.aero.models.NetworkStateInfo;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private Context context;
    List<NetworkStateInfo> networkStateInfoList;
    RemoveEventClickListener removeEventClickListener;

    public EventsAdapter(Context context) {
        super();
        this.context = context;
        networkStateInfoList = new ArrayList<>();
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.activity_main_rv_events_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, int position) {
        final NetworkStateInfo currentInfo = this.networkStateInfoList.get(position);

        if (currentInfo != null) {
            //holder.status.setText(currentInfo.isConnected() ? "Online" : "Offline");
            if (currentInfo.isConnected()) {
                holder.primaryText.setText(String.format("On %s", currentInfo.getConnectionType()));
                holder.secondaryText.setText(String.format("Using %s network",
                        (currentInfo.getNetworkConnectionName() != null && !currentInfo.getNetworkConnectionName().isEmpty()) ? currentInfo.getNetworkConnectionName() : "-"));

                holder.icon.setImageResource(R.drawable.ic_action_check_circle);
                holder.icon.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
            } else {
                holder.primaryText.setText("Offline");
                holder.secondaryText.setText("");
                holder.icon.setImageResource(R.drawable.ic_action_error);
                holder.icon.setColorFilter(context.getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            holder.removeEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (removeEventClickListener != null) {
                        removeEventClickListener.onRemoveEvent(currentInfo.getId());
                    }
                }
            });

            holder.eventRaisedOn.setText(new PrettyTime().format(currentInfo.getEventRaisedDate()));
        }
    }

    @Override
    public int getItemCount() {
        return this.networkStateInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView primaryText;
        TextView secondaryText;
        ImageButton removeEvent;
        TextView eventRaisedOn;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            primaryText = (TextView) itemView.findViewById(R.id.activity_main_rv_events_row_tv_primaryText);
            secondaryText = (TextView) itemView.findViewById(R.id.activity_main_rv_events_row_tv_secondaryText);
            removeEvent = (ImageButton) itemView.findViewById(R.id.activity_main_rv_events_row_bt_removeEvent);
            eventRaisedOn = (TextView) itemView.findViewById(R.id.activity_main_rv_events_row_tv_eventRaisedOn);
            icon = (ImageView) itemView.findViewById(R.id.activity_main_rv_events_row_iv_icon);
        }
    }

    public void loadData() {
        this.networkStateInfoList = NetworkStateInfo.listAll(NetworkStateInfo.class, "event_Raised_Date DESC");
        this.notifyDataSetChanged();
    }

    public interface RemoveEventClickListener {
        void onRemoveEvent(long id);
    }

    public void setRemoveEventClickListener(RemoveEventClickListener removeEventClickListener) {
        this.removeEventClickListener = removeEventClickListener;
    }
}
