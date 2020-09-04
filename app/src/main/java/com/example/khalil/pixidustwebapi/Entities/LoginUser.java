package com.example.khalil.pixidustwebapi.Entities;

import com.example.khalil.pixidustwebapi.Entities.Interfaces.BaseClass;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Khalil on 1/23/2018.
 */

public class LoginUser implements BaseClass<LoginUser> {
    @SerializedName("ID")
    public int ID;
    @SerializedName("FirstName")
    public String FirstName;
    @SerializedName("LastName")
    public String LastName;
    @SerializedName("LoginEmail")
    public String LoginEmail;
    @SerializedName("Password")
    public String Password;
    @SerializedName("DisplayName")
    public String DisplayName;
    @SerializedName("DOB")
    public String DOB;
    @SerializedName("AddressLine1")
    public String AddressLine1;
    @SerializedName("AddressLine2")
    public String AddressLine2;
    @SerializedName("City")
    public String City;
    @SerializedName("County")
    public String County;
    @SerializedName("Mobile")
    public String Mobile;
    @SerializedName("ImagePath")
    public String ImagePath;
    @SerializedName("IsActive")
    public boolean IsActive;
    @SerializedName("LastLogin")
    public String LastLogin;
/*    @SerializedName("DateCreated")
    public Date DateCreated;*/
    @SerializedName("userType")
    public Enums.UserType userType;
    @SerializedName("Phone")
    public String Phone;
    @SerializedName("PostCode")
    public String PostCode;
    @SerializedName("EmailPreference")
    public int EmailPreference;
    @SerializedName("FK_UserType")
    public int FK_UserType;
    @SerializedName("UserTypeName")
    public String UserTypeName;
    @SerializedName("Brief")
    public String Brief;
    @SerializedName("VideoPath")
    public String VideoPath;
    @SerializedName("StoreUserToken")
    public String StoreUserToken;
    public int GetId(){
      return ID;
    }
    public LoginUser[] SetList(LoginUser[] lstgeneric){
        return null;
    }
    public LoginUser[] GetList(){
        return null;
    }

    @Override
    public void SetId(int i) {

    }

    public LoginUser GetSpecificProperties(LoginUser detail){
        return  null;
    }

}
