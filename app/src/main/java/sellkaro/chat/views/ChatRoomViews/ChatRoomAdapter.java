package sellkaro.chat.views.ChatRoomViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sellkaro.chat.R;
import sellkaro.chat.activites.BaseFragmentActivity;
import sellkaro.chat.entites.ChatRoom;

public class ChatRoomAdapter extends RecyclerView.Adapter {

    private BaseFragmentActivity mActivity;
    private List<ChatRoom> mChatRooms;
    private LayoutInflater mInflator;
    private ChatRoomListener mListener;
    private String mCurrentUserEmailString;

    public ChatRoomAdapter(BaseFragmentActivity mActivity, ChatRoomListener mListener, String mCurrentUserEmailString) {
        this.mActivity = mActivity;
        this.mListener = mListener;
        this.mCurrentUserEmailString = mCurrentUserEmailString;
        mInflator = mActivity.getLayoutInflater();
        mChatRooms = new ArrayList<>();
    }

    public void setmChatRooms(List<ChatRoom> chatRooms) {
        mChatRooms.clear();
        mChatRooms.addAll(chatRooms);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.list_chat_room,parent,false);
        final ChatRoomViewHolder chatRoomViewHolder = new ChatRoomViewHolder(view);
        chatRoomViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatRoom chatRoom =(ChatRoom) chatRoomViewHolder.itemView.getTag();
                mListener.OnChatRoomClicked(chatRoom);
            }
        });
        return chatRoomViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ChatRoomViewHolder) holder).populate(mActivity,mChatRooms.get(position),mCurrentUserEmailString);
    }

    @Override
    public int getItemCount() {
        return mChatRooms.size();
    }

    public interface ChatRoomListener{
        void OnChatRoomClicked(ChatRoom chatRoom);
    }
}
