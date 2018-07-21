package Model;

/**
 * Created by 300269668 on 5/29/2018.
 */

public class ListItem {
    private String title;
    private String description;
    private String image;
    private String url;
    private String publishedAt;

    public ListItem(String title, String description, String image, String url, String publishedAt) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }
}
