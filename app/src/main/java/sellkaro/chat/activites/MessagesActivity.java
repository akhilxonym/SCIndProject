package sellkaro.chat.activites;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import sellkaro.chat.fragments.MessageFragment;

public class MessagesActivity extends BaseFragmentActivity {

    public static final String EXTRA_FRIEND_DETAILS = "EXTRA_FRIENDS_DETAILS";

    @Override
    Fragment createFragment() {
        ArrayList<String> friendDetails = getIntent().getStringArrayListExtra(EXTRA_FRIEND_DETAILS);

        getSupportActionBar().setTitle(friendDetails.get(2));

        return MessageFragment.newInstance(friendDetails);
    }

    public static Intent newInstance(Context context,ArrayList<String>friendDetails){
        Intent intent = new Intent(context,MessagesActivity.class);
        intent.putStringArrayListExtra(EXTRA_FRIEND_DETAILS,friendDetails);
        return intent;
    }
}
