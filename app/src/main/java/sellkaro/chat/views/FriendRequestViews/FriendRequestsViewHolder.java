package sellkaro.chat.views.FriendRequestViews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sellkaro.chat.R2;
import sellkaro.chat.entites.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendRequestsViewHolder extends RecyclerView.ViewHolder {

   @BindView(R2.id.list_friend_request_userPicture)
    ImageView userPicture;

    @BindView(R2.id.list_friend_request_userName)
    TextView userName;

    @BindView(R2.id.list_friend_request_acceptRequest)
    ImageView approveImageView;

    @BindView(R2.id.list_friend_request_rejectRequest)
    ImageView rejectImageView;

    public FriendRequestsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void populate(Context context, User user){
        itemView.setTag(user);

        userName.setText(user.getUserName());

        Picasso.with(context)
                .load(user.getUserPicture())
                .into(userPicture);
    }
}
