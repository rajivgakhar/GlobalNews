package Model;

public class SavedNews {
    public String userId;
    public ListItem savedItem;
    public SavedNews() {
    }
    public SavedNews(String userId, ListItem savedItem) {
        this.userId = userId;
        this.savedItem = savedItem;
    }
    public SavedNews( ListItem savedItem) {

        this.savedItem = savedItem;
    }
    public SavedNews( String userId) {

        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;

    }

    public ListItem getSavedItem() {
        return savedItem;
    }
}
