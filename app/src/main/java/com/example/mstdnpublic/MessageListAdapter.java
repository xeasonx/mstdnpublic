package com.example.mstdnpublic;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    private String[] avatarUrls;
    private String[] displayNames;
    private String[] contents;

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

    public MessageListAdapter(String[] avatarUrls, String[] displayNames, String[] contents) {
        this.avatarUrls = avatarUrls;
        this.displayNames = displayNames;
        this.contents = contents;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageListAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.getCardView();
        TextView displayNameView = cardView.findViewById(R.id.display_name);
        TextView contentsView = cardView.findViewById(R.id.twitter_content);
        ImageView avatar = cardView.findViewById(R.id.avatar);
        Glide.with(holder.cardView).load(avatarUrls[position]).into(avatar);
        displayNameView.setText(Html.fromHtml(displayNames[position]));
        contentsView.setText(Html.fromHtml(contents[position]));
    }

    @Override
    public int getItemCount() {
        return this.avatarUrls.length;
    }
}
