package sellkaro.chat.services;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sellkaro.chat.entites.ChatRoom;
import sellkaro.chat.entites.Message;
import sellkaro.chat.entites.User;
import sellkaro.chat.fragments.FindFriendsFragment;
import sellkaro.chat.utils.Constants;
import sellkaro.chat.views.ChatRoomViews.ChatRoomAdapter;
import sellkaro.chat.views.FindFriendsViews.FindFriendsAdapter;
import sellkaro.chat.views.FriendRequestViews.FriendRequestAdapter;
import sellkaro.chat.views.MessagesViews.MessagesAdapter;
import sellkaro.chat.views.UserFriendViews.UserFriendAdapter;
import io.socket.client.Socket;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LiveFriendsServices {
    public static LiveFriendsServices mLiveFriendsServices;



    private final int SERVER_SUCCESS = 6;
    private final int SERVER_FAILURE = 7;

    public static LiveFriendsServices getInstance(){
        if (mLiveFriendsServices ==null){
            return new LiveFriendsServices();
        } else{
            return mLiveFriendsServices;
        }
    }


    public ValueEventListener getAllNewMessages(final BottomBar bottomBar, final int tagId){
        final List<Message> messages = new ArrayList<>();
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Message message = snapshot.getValue(Message.class);
                    messages.add(message);
                }

                if (!messages.isEmpty()){
                    bottomBar.getTabWithId(tagId).setBadgeCount(messages.size());
                } else{
                    bottomBar.getTabWithId(tagId).removeBadge();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    public ValueEventListener getAllChatRooms(final RecyclerView recyclerView, final TextView textView,
                                              final ChatRoomAdapter adapter){
        final List<ChatRoom> chatRooms = new ArrayList<>();
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatRooms.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ChatRoom chatRoom = snapshot.getValue(ChatRoom.class);
                    chatRooms.add(chatRoom);
                }
                if (chatRooms.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                } else{
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    adapter.setmChatRooms(chatRooms);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    public ValueEventListener getAllMessages(final RecyclerView recyclerView, final TextView textView, final ImageView imageView,
                                             final MessagesAdapter adapter, final String userEmail){
        final List<Message> messages = new ArrayList<>();
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                DatabaseReference newMessagesReference = FirebaseDatabase.getInstance().getReference()
                        .child(Constants.FIRE_BASE_PATH_USER_NEW_MESSAGES).child(Constants.encodeEmail(userEmail));
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Message message = snapshot.getValue(Message.class);
                    newMessagesReference.child(message.getMessageId()).removeValue();
                    messages.add(message);
                }

                if (messages.isEmpty()){
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else{
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setmMessages(messages);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    public Subscription sendMessage(final Socket socket, String messageSenderEmail, String messageSenderPicture, String messageText,
                                    String friendEmail,String messageSenderName){
        List<String> details = new ArrayList<>();
        details.add(messageSenderEmail);
        details.add(messageSenderPicture);
        details.add(messageText);
        details.add(friendEmail);
        details.add(messageSenderName);
        Observable<List<String>> listObservable = Observable.just(details);

        return listObservable
                .subscribeOn(Schedulers.io())
                .map(new Func1<List<String>, Integer>() {
                    @Override
                    public Integer call(List<String> strings) {
                        JSONObject sendData = new JSONObject();

                        try {
                            sendData.put("senderEmail",strings.get(0));
                            sendData.put("senderPicture",strings.get(1));
                            sendData.put("messageText",strings.get(2));
                            sendData.put("friendEmail",strings.get(3));
                            sendData.put("senderName",strings.get(4));
                            socket.emit("details",sendData);
                            return SERVER_SUCCESS;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return SERVER_FAILURE;
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
    }


    public ValueEventListener getAllFriends(final RecyclerView recyclerView, final UserFriendAdapter adapter, final TextView textView){
        final List<User> users = new ArrayList<>();
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user =snapshot.getValue(User.class);
                    users.add(user);
                }

                if (users.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                } else{
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    adapter.setmUsers(users);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }




    public ValueEventListener getAllFriendRequests(final FriendRequestAdapter adapter, final RecyclerView recyclerView,

                                                   final TextView textView){

        final List<User> users = new ArrayList<>();

        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }

                if (users.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    adapter.setmUsers(users);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    public Subscription approveDeclineFriendRequest(final Socket socket, String userEmail, String friendEmail, String requestCode){
        List<String> details = new ArrayList<>();
        details.add(userEmail);
        details.add(friendEmail);
        details.add(requestCode);

        Observable<List<String>> listObservable = Observable.just(details);

        return listObservable
                .subscribeOn(Schedulers.io())
                .map(new Func1<List<String>, Integer>() {
                    @Override
                    public Integer call(List<String> strings) {
                        JSONObject sendData = new JSONObject();

                        try {
                            sendData.put("userEmail",strings.get(0));
                            sendData.put("friendEmail",strings.get(1));
                            sendData.put("requestCode",strings.get(2));
                            socket.emit("friendRequestResponse",sendData);
                            return SERVER_SUCCESS;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return SERVER_FAILURE;
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
    }

    public Subscription addOrRemoveFriendRequest(final Socket socket, String userEmail, String friendEmail, String requestCode){
        List<String> details = new ArrayList<>();
        details.add(userEmail);
        details.add(friendEmail);
        details.add(requestCode);

        Observable<List<String>> listObservable = Observable.just(details);

        return listObservable
                .subscribeOn(Schedulers.io())
                .map(new Func1<List<String>, Integer>() {
                    @Override
                    public Integer call(List<String> strings) {
                        JSONObject sendData = new JSONObject();
                        try {
                            sendData.put("email",strings.get(1));
                            sendData.put("userEmail",strings.get(0));
                            sendData.put("requestCode",strings.get(2));
                            socket.emit("friendRequest",sendData);
                            return SERVER_SUCCESS;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return SERVER_FAILURE;
                        }

                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
    }


    public ValueEventListener getAllCurrentUsersFriendMap(final FindFriendsAdapter adapter){
        final HashMap<String,User> userHashMap = new HashMap<>();
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userHashMap.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    userHashMap.put(user.getEmail(),user);
                }

                adapter.setmCurrentUserFriendsMap(userHashMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    public ValueEventListener getFriendRequestsSent(final FindFriendsAdapter adapter, final FindFriendsFragment fragment){
        final HashMap<String,User> userHashMap = new HashMap<>();

        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userHashMap.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    userHashMap.put(user.getEmail(),user);
                }

                adapter.setmFriendRequestSentMap(userHashMap);
                fragment.setmFriendRequestsSentMap(userHashMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    public ValueEventListener getFriendRequestsRecived(final FindFriendsAdapter adapter){
        final HashMap<String,User> userHashMap = new HashMap<>();

        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userHashMap.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    userHashMap.put(user.getEmail(),user);
                }

                adapter.setmFriendRequestRecivedMap(userHashMap);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    public ValueEventListener getFriendRequestBottom(final BottomBar bottomBar, final int tagId){
        final List<User> users = new ArrayList<>();
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }

                if (!users.isEmpty()){
                    bottomBar.getTabWithId(tagId).setBadgeCount(users.size());
                } else{
                    bottomBar.getTabWithId(tagId).removeBadge();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    public List<User> getMatchingUsers(List<User> users, String userEmail){
        if (userEmail.isEmpty()){
            return users;
        }

        List<User> usersFound = new ArrayList<>();

        for (User user:users){
            if (user.getEmail().toLowerCase().startsWith(userEmail.toLowerCase())){
                usersFound.add(user);
            }
        }

        return usersFound;
    }




}
