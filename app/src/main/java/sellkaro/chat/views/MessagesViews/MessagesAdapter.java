package sellkaro.chat.views.MessagesViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sellkaro.chat.R;
import sellkaro.chat.activites.BaseFragmentActivity;
import sellkaro.chat.entites.Message;

public class MessagesAdapter extends RecyclerView.Adapter {

    private BaseFragmentActivity mActivity;
    private List<Message> mMessages;
    private LayoutInflater mInflator;
    private String mCurrentUserEmail;

    public MessagesAdapter(BaseFragmentActivity mActivity, String mCurrentUserEmail) {
        this.mActivity = mActivity;
        this.mCurrentUserEmail = mCurrentUserEmail;
        mInflator = mActivity.getLayoutInflater();
        mMessages = new ArrayList<>();
    }

    public void setmMessages(List<Message> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }

    public List<Message> getmMessages() {
        return mMessages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.list_messages,parent,false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessagesViewHolder) holder).populate(mActivity,mMessages.get(position),mCurrentUserEmail);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}
