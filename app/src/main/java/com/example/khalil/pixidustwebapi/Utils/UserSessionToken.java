package com.example.khalil.pixidustwebapi.Utils;

/**
 * Created by Khalil on 2/12/2018.
 */

public class UserSessionToken {
    public static String UserToken="";
    public static void SetUserToken(String paramUserToken){
        UserToken=paramUserToken;
    }
    public static String GetUserToken(){
        return UserToken;
    }
}
