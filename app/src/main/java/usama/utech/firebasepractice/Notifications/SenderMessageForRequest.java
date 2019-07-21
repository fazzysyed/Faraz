package usama.utech.firebasepractice.Notifications;

import java.util.ArrayList;
import java.util.HashMap;

public class SenderMessageForRequest {
HashMap<String,String> data;
    public String title;

    public SenderMessageForRequest() {
    }

    public SenderMessageForRequest(HashMap<String, String> data, String title) {
        this.data = data;
        this.title = title;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
