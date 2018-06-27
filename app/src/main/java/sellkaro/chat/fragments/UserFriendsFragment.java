package sellkaro.chat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sellkaro.chat.R;
import sellkaro.chat.R2;
import sellkaro.chat.activites.BaseFragmentActivity;
import sellkaro.chat.activites.MessagesActivity;
import sellkaro.chat.entites.User;
import sellkaro.chat.services.LiveFriendsServices;
import sellkaro.chat.utils.Constants;
import sellkaro.chat.views.UserFriendViews.UserFriendAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserFriendsFragment extends BaseFragment implements UserFriendAdapter.UserClickedListener {

    @BindView(R2.id.fragment_user_friends_reyclerView)
    RecyclerView mRecyclerView;

    @BindView(R2.id.fragment_user_friends_message)
    TextView mTextView;

    private LiveFriendsServices mLiveFriendServices;
    private String mUserEmailString;

    private DatabaseReference mGetAllCurrenUsersFriendsReference;
    private ValueEventListener mGetAllCurrentUsersFriendsListener;

    private Unbinder mUnbinder;

    public static UserFriendsFragment newInstance(){
        return new UserFriendsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLiveFriendServices = LiveFriendsServices.getInstance();
        mUserEmailString = mSharedPreferences.getString(Constants.USER_EMAIL,"");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_friends,container,false);
        mUnbinder = ButterKnife.bind(this,rootView);


        UserFriendAdapter adapter = new UserFriendAdapter((BaseFragmentActivity) getActivity(),this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mGetAllCurrenUsersFriendsReference = FirebaseDatabase.getInstance().getReference()
                .child(Constants.FIRE_BASE_PATH_USER_FRIENDS).child(Constants.encodeEmail(mUserEmailString));

        mGetAllCurrentUsersFriendsListener = mLiveFriendServices.getAllFriends(mRecyclerView,adapter,mTextView);

        mGetAllCurrenUsersFriendsReference.addValueEventListener(mGetAllCurrentUsersFriendsListener);

        mRecyclerView.setAdapter(adapter);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

        if (mGetAllCurrentUsersFriendsListener!=null){
            mGetAllCurrenUsersFriendsReference.removeEventListener(mGetAllCurrentUsersFriendsListener);
        }
    }

    @Override
    public void OnUserClicked(User user) {
        ArrayList<String> friendDetails = new ArrayList<>();
        friendDetails.add(user.getEmail());
        friendDetails.add(user.getUserPicture());
        friendDetails.add(user.getUserName());
        Intent intent = MessagesActivity.newInstance(getActivity(),friendDetails);

        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);

    }
}
