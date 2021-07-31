package com.example.mstdnpublic;

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


public class TweetListAdapter extends ListAdapter<Status, TweetListAdapter.ViewHolder> {
    public TweetListAdapter() {
        super(DIFF_CALLBACK);
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
        ImageView avatar = cardView.findViewById(R.id.avatar);
        Glide.with(holder.cardView).load(item.account.avatar).into(avatar);
        displayNameView.setText(Html.fromHtml(item.account.display_name));
        contentsView.setText(Html.fromHtml(item.content));
    }
}
