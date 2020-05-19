/**
 *Creating a new student
 * author:Sanchit Monga
 */
public class Student extends User{
    /**
     * Calling the parent call constructor and creating a new user
     * @param username: username of the student
     */
    public Student(String username){
        super(username,UserType.STUDENT,new CourseComparator());
    }
}
