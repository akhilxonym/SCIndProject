package sellkaro.chat.views.FindFriendsViews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import sellkaro.chat.R;
import sellkaro.chat.R2;
import sellkaro.chat.entites.User;
import sellkaro.chat.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FindFriendsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R2.id.list_user_userPicture)
    ImageView mUserPicture;

    @BindView(R2.id.list_user_addFriend)
    public ImageView mAddFriend;

    @BindView(R2.id.list_user_userName)
    TextView mUserName;

    @BindView(R2.id.list_user_userStatus)
    TextView mUserStatus;


    public FindFriendsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void populate(Context context, User user,
                         HashMap<String,User> friendRequestSentMap,
                         HashMap<String,User> friendRequestRecievedMap,
                         HashMap<String,User> currentUsersFriendMap){
        itemView.setTag(user);
        mUserName.setText(user.getUserName());

        Picasso.with(context)
                .load(user.getUserPicture())
                .into(mUserPicture);

        if (Constants.isIncludedInMap(friendRequestSentMap,user)){
            mUserStatus.setVisibility(View.VISIBLE);
            mUserStatus.setText("Friend Request Sent");
            mAddFriend.setImageResource(R.mipmap.ic_cancel_request);
            mAddFriend.setVisibility(View.VISIBLE);
        } else if (Constants.isIncludedInMap(friendRequestRecievedMap,user)){
            mAddFriend.setVisibility(View.GONE);
            mUserStatus.setVisibility(View.VISIBLE);
            mUserStatus.setText("This User Has Requested You");
        } else if(Constants.isIncludedInMap(currentUsersFriendMap,user)){
            mUserStatus.setVisibility(View.VISIBLE);
            mUserStatus.setText("User Added!");
            mAddFriend.setVisibility(View.GONE);
        } else{
            mAddFriend.setVisibility(View.VISIBLE);
            mUserStatus.setVisibility(View.GONE);
            mAddFriend.setImageResource(R.mipmap.ic_add);
        }

    }
}
