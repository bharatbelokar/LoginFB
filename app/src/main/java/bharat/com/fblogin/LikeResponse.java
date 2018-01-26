package bharat.com.fblogin;

/**
 * Created by Shrikant on 26-01-2018.
 */

public class LikeResponse {
    public LikeResponse(String data, String date) {
        this.data = data;
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String data ;
    String date;
}
