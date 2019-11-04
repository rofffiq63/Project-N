package com.need.unknown.component.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.need.unknown.R;
import com.need.unknown.component.model.DataNotification;
import com.need.unknown.view.InboxView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anurr on 12/9/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DataNotification> dataNotificationList;
    private InboxView notificationPresenter;

    public NotificationAdapter(Context applicationContext) {
        this.context = applicationContext;
    }

    public NotificationAdapter(Context context, InboxView notificationPresenter) {
        this.context = context;
        this.notificationPresenter = notificationPresenter;
        dataNotificationList = new ArrayList<>();
//        dataNotificationList.add(new DataNotification());
//        dataNotificationList.add(new DataNotification());
//        dataNotificationList.add(new DataNotification());
//        dataNotificationList.add(new DataNotification());
//        dataNotificationList.add(new DataNotification());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notif_info, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final DataNotification dataNotification = dataNotificationList.get(position);
        ((InfoViewHolder) holder).bind(dataNotification);
    }

    @Override
    public int getItemCount() {
        return (dataNotificationList != null ? dataNotificationList.size() : 0);
    }

    public void remove(DataNotification dataNotification) {
        int position = dataNotificationList.indexOf(dataNotification);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date resultdate = new Date(dataNotification.getDate());
        String datecurrent = sdf.format(resultdate);

        if (position != dataNotificationList.size() - 1 && position - 2 != -1) {
            Date headerDate = new Date(dataNotificationList.get(position - 2).getDate());
            Date belowDate = new Date(dataNotificationList.get(position + 1).getDate());

            if (!datecurrent.equals(sdf.format(headerDate)) && !datecurrent.equals(sdf.format(belowDate))) {
                dataNotificationList.remove(position - 1);
                notifyItemRemoved(position - 1);
            }

        } else if (position - 2 == -1 && position != dataNotificationList.size() - 1) {
            Date belowDate = new Date(dataNotificationList.get(position + 1).getDate());

            if (!datecurrent.equals(sdf.format(belowDate))) {
                dataNotificationList.remove(position - 1);
                notifyItemRemoved(position - 1);
            }

        } else if (position - 2 != -1 && position == dataNotificationList.size() - 1) {
            Date headerDate = new Date(dataNotificationList.get(position - 2).getDate());

            if (!datecurrent.equals(sdf.format(headerDate))) {
                dataNotificationList.remove(position - 1);
                notifyItemRemoved(position - 1);
            }

        } else if (position == dataNotificationList.size() - 1 && position - 2 == -1) {
            dataNotificationList.remove(position - 1);
            notifyItemRemoved(position - 1);
        }

        if (dataNotificationList.indexOf(dataNotification) > -1) {
            dataNotificationList.remove(dataNotificationList.indexOf(dataNotification));
            notifyItemRemoved(position);
        }
    }

    public void addData(List<DataNotification> dataNotificationList) {
        this.dataNotificationList.clear();
        this.dataNotificationList.addAll(dataNotificationList);
        notifyDataSetChanged();
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notif_title)
        TextView notifTitle;
        @BindView(R.id.notif_content)
        TextView notifContent;
        @BindView(R.id.notif_date)
        TextView notifDate;
        @BindView(R.id.unread_indicator)
        ImageView unreadIndicator;
        @BindView(R.id.more)
        ImageView more;
        @BindView(R.id.imginfo)
        ImageView imginfo;
        @BindView(R.id.container)
        LinearLayout container;

        public InfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(DataNotification dataNotification) {

        }
    }
}
