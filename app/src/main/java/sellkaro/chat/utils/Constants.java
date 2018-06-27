package sellkaro.chat.utils;

import java.util.HashMap;

import sellkaro.chat.entites.User;

public class Constants {

    public static final String IP_LOCAL_HOST = "http://172.20.10.2:3000";

    public static final String USER_INFO_PREFERENCE = "USER_INFO_PREFERENCE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PICTURE = "USER_PICTURE";


    public static final String FIRE_BASE_PATH_USERS = "users";
    public static final String FIRE_BASE_PATH_FRIEND_REQUEST_SENT ="friendRequestsSent";
    public static final String FIRE_BASE_PATH_FRIEND_REQUEST_RECIEVED = "friendRequestRecieved";
    public static final String FIRE_BASE_PATH_USER_FRIENDS = "userFriends";
    public static final String FIRE_BASE_PATH_USER_TOKEN = "userToken";
    public static final String FIRE_BASE_PATH_USER_MESSAGES = "userMessages";
    public static final String FIRE_BASE_PATH_USER_NEW_MESSAGES = "newUserMessages";
    public static final String FIRE_BASE_PATH_USER_CHAT_ROOMS = "userChatRooms";


    public static String encodeEmail(String email){
        return email.replace(".",",");
    }

    public static boolean isIncludedInMap(HashMap<String,User> userHashMap,User user){
     return userHashMap!=null && userHashMap.size() !=0 &&
             userHashMap.containsKey(user.getEmail());
    }




}
