package com.example.raymond.myandroidproject.base;

/**
 * Created by Raymond 陈徐锋 on 2018/2/23.
 * Email: raymond@hinteen.com
 * Description：用于存放临时对话的用户信息
 */

public class Session {
    private int userID;
    private String userAccount;
    private int userPassword;

    public Session(String userAccount, int userPassword) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(int userPassword) {
        this.userPassword = userPassword;
    }
}
