package sellkaro.chat.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sellkaro.chat.fragments.FindFriendsFragment;
import sellkaro.chat.fragments.FriendRequestsFragment;
import sellkaro.chat.fragments.UserFriendsFragment;

public class FriendsViewPagerAdapter extends FragmentStatePagerAdapter {

    public FriendsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment returnFragment;

        switch (position){
            case 0:
                returnFragment = UserFriendsFragment.newInstance();
                break;
            case 1:
                returnFragment = FriendRequestsFragment.newInstance();
                break;
            case 2:
                returnFragment = FindFriendsFragment.newInstance();
                break;

            default:
                return null;
        }
        return returnFragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;

        switch (position){
            case 0:
                title = "Friends";
                break;
            case 1:
                title = "Requests";
                break;
            case 2:
                title = "Find Friends";
                break;
            default:
                return null;
        }

        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
