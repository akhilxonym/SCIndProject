package sellkaro.chat.activites;

import android.support.v4.app.Fragment;

import sellkaro.chat.fragments.RegisterFragment;

public class RegisterActivity extends BaseFragmentActivity {
    @Override
    Fragment createFragment() {
        return RegisterFragment.newInstance();
    }
}
