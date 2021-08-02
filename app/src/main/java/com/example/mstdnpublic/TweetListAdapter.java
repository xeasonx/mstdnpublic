package com.example.mstdnpublic;

import android.os.ParcelFormatException;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.mstdnResponseEntities.Status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TweetListAdapter extends ListAdapter<Status, TweetListAdapter.ViewHolder> {
    private Locale locale;

    public TweetListAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.message_card);
        }

        public CardView getCardView() {
            return cardView;
        }
    }

    public static final DiffUtil.ItemCallback<Status> DIFF_CALLBACK = new DiffUtil.ItemCallback<Status>() {
        @Override
        public boolean areItemsTheSame(Status oldItem, Status newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(Status oldItem, Status newItem) {
            return oldItem.id.equals(newItem.id);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetListAdapter.ViewHolder holder, int position) {
        Status item = getItem(position);
        CardView cardView = holder.getCardView();
        TextView displayNameView = cardView.findViewById(R.id.display_name);
        TextView contentsView = cardView.findViewById(R.id.twitter_content);
        TextView createdAt = cardView.findViewById(R.id.created_at);
        ImageView avatar = cardView.findViewById(R.id.avatar);
        Glide.with(holder.cardView).load(item.account.avatar).into(avatar);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", locale);
        try {
            Date date = dateFormat.parse(item.created_at);
            createdAt.setText(date.toString());
        } catch (ParseException e) {
            createdAt.setText(item.created_at);
        }
        displayNameView.setText(Html.fromHtml(item.account.display_name));
        contentsView.setText(Html.fromHtml(item.content));
    }
}
