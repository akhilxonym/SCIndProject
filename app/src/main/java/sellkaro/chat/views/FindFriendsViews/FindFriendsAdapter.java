package sellkaro.chat.views.FindFriendsViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sellkaro.chat.R;
import sellkaro.chat.activites.BaseFragmentActivity;
import sellkaro.chat.entites.User;

public class FindFriendsAdapter extends RecyclerView.Adapter {

    private BaseFragmentActivity mActivity;
    private List<User> mUsers;
    private LayoutInflater mInflater;
    private UserListener mListener;


    private HashMap<String,User> mFriendRequestSentMap;
    private HashMap<String,User> mFriendRequestRecivedMap;
    private HashMap<String,User> mCurrentUserFriendsMap;

    public FindFriendsAdapter(BaseFragmentActivity mActivity, UserListener mListener) {
        this.mActivity = mActivity;
        this.mListener = mListener;
        mInflater = mActivity.getLayoutInflater();
        mUsers = new ArrayList<>();
        mFriendRequestSentMap = new HashMap<>();
        mFriendRequestRecivedMap = new HashMap<>();
        mCurrentUserFriendsMap = new HashMap<>();
    }


    public void setmUsers(List<User> users) {
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

    public void setmFriendRequestSentMap(HashMap<String, User> friendRequestSentMap) {
        mFriendRequestSentMap.clear();
        mFriendRequestSentMap.putAll(friendRequestSentMap);
        notifyDataSetChanged();
    }

    public void setmCurrentUserFriendsMap(HashMap<String, User> currentUserFriendsMap) {
        mCurrentUserFriendsMap.clear();
        mCurrentUserFriendsMap.putAll(currentUserFriendsMap);
        notifyDataSetChanged();

    }

    public void setmFriendRequestRecivedMap(HashMap<String, User> friendRequestRecivedMap) {
        mFriendRequestRecivedMap.clear();
        mFriendRequestRecivedMap.putAll(friendRequestRecivedMap);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View userView = mInflater.inflate(R.layout.list_user,parent,false);
        final FindFriendsViewHolder findFriendsViewHolder = new FindFriendsViewHolder(userView);
        findFriendsViewHolder.mAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user  = (User) findFriendsViewHolder.itemView.getTag();
                mListener.OnUserClicked(user);
            }
        });
        return findFriendsViewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FindFriendsViewHolder) holder).populate(mActivity,mUsers.get(position)
                ,mFriendRequestSentMap,mFriendRequestRecivedMap,mCurrentUserFriendsMap);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public interface UserListener{
        void OnUserClicked(User user);
    }
}
