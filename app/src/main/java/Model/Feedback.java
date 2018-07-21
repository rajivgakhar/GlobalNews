package Model;

/**
 * Created by 300269668 on 7/21/2018.
 */

public class Feedback {
    public String name;
    public String email;
    public String comment;
    public Feedback() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getComment() {
        return comment;
    }

    public Feedback(String name, String email, String comment) {
        this.name = name;
        this.email = email;

        this.comment = comment;
    }
}
