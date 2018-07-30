package Model;

/**
 * Created by 300269668 on 7/21/2018.
 */

public class Feedback {
    public String name;
    public String email;
    public String comment;
    public String date;
    public Feedback listFeed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public Feedback() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public String getComment() {
        return comment;
    }

    public Feedback(String name, String email, String comment, String strDate) {
        this.name = name;
        this.email = email;
this.date=strDate;
        this.comment = comment;
    }

    public Feedback getListFeed() {
        return listFeed;
    }

    public void setListFeed(Feedback listFeed) {
        this.listFeed = listFeed;
    }
}
