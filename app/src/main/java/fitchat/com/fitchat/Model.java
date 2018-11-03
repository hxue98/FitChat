package fitchat.com.fitchat;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model instance = new Model();

    private List<User> accounts;
    private User currentUser;

    private Model() {
        accounts = new ArrayList<>();
    }

    public Model getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<User> getAccounts() {
        return accounts;
    }
}
