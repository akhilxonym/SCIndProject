package sellkaro.chat.activites;


import android.support.v4.app.Fragment;

import sellkaro.chat.fragments.LoginFragment;

public class LoginActivity extends BaseFragmentActivity {

    @Override
    Fragment createFragment() {
        return LoginFragment.newInstance();
    }
}
