package sellkaro.chat.views.ChatRoomViews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sellkaro.chat.R2;
import sellkaro.chat.entites.ChatRoom;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRoomViewHolder extends RecyclerView.ViewHolder{

    @BindView(R2.id.list_chat_room_lastMessage)
    TextView mLastMessage;

    @BindView(R2.id.list_chat_room_newMessageIndicator)
    ImageView mLastMessageIndicator;

    @BindView(R2.id.list_chat_room_userName)
    TextView mUserName;

    @BindView(R2.id.list_chat_room_userPicture)
    ImageView mUserPicture;

    public ChatRoomViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void populate(Context context, ChatRoom chatRoom, String currentUserEmail){
        itemView.setTag(chatRoom);

        Picasso.with(context)
                .load(chatRoom.getFriendPicture())
                .into(mUserPicture);

        mUserName.setText(chatRoom.getFriendName());

        String lastMessageSent = chatRoom.getLastMessage();

        if (lastMessageSent.length()>40){
            lastMessageSent = lastMessageSent.substring(0,40) + " ...";
        }

        if (!chatRoom.isSentLastMessage()){
            lastMessageSent = lastMessageSent + " (Draft)";
        }

        if (chatRoom.getLastMessageSenderEmail().equals(currentUserEmail)){
            lastMessageSent = "Me: " + lastMessageSent;
        }

        if (!chatRoom.isLastMessageRead()){
            mLastMessageIndicator.setVisibility(View.VISIBLE);
        } else{
            mLastMessageIndicator.setVisibility(View.GONE);
        }



        mLastMessage.setText(lastMessageSent);



    }
}
