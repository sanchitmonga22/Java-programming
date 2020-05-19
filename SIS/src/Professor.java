/**
 *Creating a new professor
 * author:Sanchit Monga
 */
public class Professor extends User {
    /**
     * Calling the parent call contructor and creating a new user
     * @param username: username of the professor
     */
    public Professor(String username){
        super(username,UserType.PROFESSOR,new ProfessorComparator());
    }
}
