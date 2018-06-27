package sellkaro.chat.activites;

import android.support.v4.app.Fragment;

import sellkaro.chat.fragments.ProfileFragment;

public class ProfileActivity extends BaseFragmentActivity {
    @Override
    Fragment createFragment() {
        return ProfileFragment.newInstance();
    }
}
