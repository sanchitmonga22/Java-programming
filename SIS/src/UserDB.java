import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
/**
 * Creating the user database
 * author:Sanchit Monga
 */
public class UserDB implements DB<String, User> {
    private HashMap<String, User> users;
    /**
     * Initializing the user database
     */
    public UserDB()
    {
        this.users=new HashMap<String,User>();
    }
    /**
     *
     * @param user: adding the user to the user database
     * @return: if there was already a user then returns the user
     */
    public User addValue(User user){
        String key=user.getUsername();
        if(this.users.containsKey(key)){
            User previous=this.users.get(key);
            this.users.put(key,user);
            return previous;
        }
        this.users.put(key,user);
        return null;
    }
    /**
     * @param username: username of the user
     * @return: the user associated with the provided username
     */
    public User getValue(String username){
        return this.users.get(username);
    }
    /**
     * @param username: username of the user
     * @return: whether or not the key exists in the database
     */
    public boolean hasKey(String username){
        return this.users.containsKey(username);
    }
    /**
     *
     * @return: collection of all the users
     */
    public Collection<User> getAllValues(){
        Collection<User> all=new TreeSet<User>();
        all.addAll(this.users.values());
        return all;
    }
}
