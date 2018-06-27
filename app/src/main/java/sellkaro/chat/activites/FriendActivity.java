package sellkaro.chat.activites;

import android.support.v4.app.Fragment;

import sellkaro.chat.fragments.FriendFragment;

public class FriendActivity extends BaseFragmentActivity {
    @Override
    Fragment createFragment() {
        return FriendFragment.newInstance();
    }
}
