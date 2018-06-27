package sellkaro.chat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;

import sellkaro.chat.R;
import sellkaro.chat.R2;
import sellkaro.chat.services.LiveFriendsServices;
import sellkaro.chat.utils.Constants;
import sellkaro.chat.views.FriendsViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FriendFragment extends BaseFragment {
    @BindView(R2.id.bottomBar)
    BottomBar mBottombar;

    @BindView(R2.id.fragment_friends_tabLayout)
    TabLayout mTabLayout;

    @BindView(R2.id.fragment_friends_viewPager)
    ViewPager mViewpager;



    private LiveFriendsServices mLiveFriendsService;
    private DatabaseReference mAllFriendRequestsReference;
    private ValueEventListener mAllFriendRequestsListener;

    private String mUserEmailString;

    private DatabaseReference mUsersNewMessagesReference;
    private ValueEventListener mUsersNewMessagesListener;



    public static FriendFragment newInstance(){
        return new FriendFragment();
    }

    private Unbinder mUnbinder;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLiveFriendsService = LiveFriendsServices.getInstance();
        mUserEmailString = mSharedPreferences.getString(Constants.USER_EMAIL,"");
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends,container,false);
        mUnbinder = ButterKnife.bind(this,rootView);
        mBottombar.selectTabWithId(R.id.tab_friends);
        setUpBottomBar(mBottombar,2);

        mUsersNewMessagesReference = FirebaseDatabase.getInstance().getReference()
                .child(Constants.FIRE_BASE_PATH_USER_NEW_MESSAGES).child(Constants.encodeEmail(mUserEmailString));
        mUsersNewMessagesListener = mLiveFriendsService.getAllNewMessages(mBottombar,R.id.tab_messages);

        mUsersNewMessagesReference.addValueEventListener(mUsersNewMessagesListener);

        FriendsViewPagerAdapter friendsViewPagerAdapter = new FriendsViewPagerAdapter(getActivity().getSupportFragmentManager());
        mViewpager.setAdapter(friendsViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewpager);

        mAllFriendRequestsReference = FirebaseDatabase.getInstance().getReference()
                .child(Constants.FIRE_BASE_PATH_FRIEND_REQUEST_RECIEVED).child(Constants.encodeEmail(mUserEmailString));
        mAllFriendRequestsListener = mLiveFriendsService.getFriendRequestBottom(mBottombar,R.id.tab_friends);
        mAllFriendRequestsReference.addValueEventListener(mAllFriendRequestsListener);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

        if (mAllFriendRequestsListener!=null){
            mAllFriendRequestsReference.removeEventListener(mAllFriendRequestsListener);
        }

        if (mUsersNewMessagesListener!=null){
            mUsersNewMessagesReference.removeEventListener(mUsersNewMessagesListener);
        }
    }
}
