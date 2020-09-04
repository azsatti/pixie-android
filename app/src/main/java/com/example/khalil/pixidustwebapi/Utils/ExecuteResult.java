package com.example.khalil.pixidustwebapi.Utils;

/**
 * Created by Khalil on 1/22/2018.
 */

public class ExecuteResult {
    public String ErrorMessage  = "";
    public int ResponseCode  = 400;
    public String Message  = "";
    public String ResponseMessage = "";
    public ExecuteResult()
    {
    }
    public ExecuteResult ( String ErrorMessage,int ResponseCode,String Message,String ResponseMessage)
    {
        this.ErrorMessage = ErrorMessage;
        this.ResponseCode  = ResponseCode;
        this.Message = Message;
        this.ResponseMessage = ResponseMessage;
    }
}
