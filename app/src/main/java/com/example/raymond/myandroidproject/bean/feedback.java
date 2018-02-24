package com.example.raymond.myandroidproject.bean;

/**
 * Created by Raymond 陈徐锋 on 2018/2/24.
 * Email: raymond@hinteen.com
 * Description: 返回内容类
 */

public class feedback {
    private String account;
    private String feedbackContext;
    private String feedbackTime;

    public feedback(String account, String feedbackContext, String feedbackTime) {
        this.account = account;
        this.feedbackContext = feedbackContext;
        this.feedbackTime = feedbackTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getFeedbackContext() {
        return feedbackContext;
    }

    public void setFeedbackContext(String feedbackContext) {
        this.feedbackContext = feedbackContext;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
}
