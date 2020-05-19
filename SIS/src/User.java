import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;
/**
 *  The user class that has access to all the elements of a user that can be professor or a student
 *  author: Sanchit Monga
 */
public class User implements Comparable<User>{
    private String username;
    private User.UserType type;
    private TreeSet<Course> courses;
    /**
     * Enumerator nested class for the usertype
     */
    public enum UserType{
        PROFESSOR,
        STUDENT
    }
    /**
     *
     * @param username: username of the user
     * @param type: type of the user which can be professor or student
     * @param comp: comparator of the user which is used to arrange the courses of the user in the given order
     */
    public User(String username, User.UserType type, Comparator<Course> comp){
        this.username=username;
        this.type=type;
        this.courses=new TreeSet<Course>(comp);
    }
    /**
     *
     * @return: username of the user
     */
    public String getUsername(){
        return this.username;
    }
    /**
     *
     * @return: type of the user
     */
    public User.UserType getType(){
        return this.type;
    }
    /**
     *
     * @param course Course to be added in the user
     * @return: whether the course was added or not
     */
    public boolean addCourse(Course course){
        if(this.courses.contains(course)){
            return false;
        }
        this.courses.add(course);
        return true;
    }
    /**
     *
     * @param course: course to be removed from the user
     * @return: whether the course was removed or not
     */
    public boolean removeCourse(Course course){
        if(this.courses.contains(course)){
            this.courses.remove(course);
            return true;
        }
        return false;
    }
    /**
     *
     * @return: the collection of the courses that the user is enrolled in
     */
    public Collection<Course> getCourses(){
        return this.courses;
    }
    /**
     *
     * @param other: other object to be checked
     * @return: whether the other object is equal or not
     */
    @Override
    public boolean equals(Object other){
        boolean result=false;
        if(other instanceof Student){
            Student s1=(Student) other;
            result =this.username.equals(s1.getUsername());
        }
         else{
            Professor p1=(Professor) other;
            result =this.username.equals(p1.getUsername());
        }
         return result;
    }
    /**
     *
     * @return : the hashcode of the username
     */
    @Override
    public int hashCode(){
        return this.username.hashCode();
    }
    /**
     * @param course: Courses that are to be printed
     * @return: the formatted string for the name of the courses
     */
    private String printCourses(TreeSet<Course> course){
        ArrayList<String> courseName=new ArrayList<>();
        int i=0;
        for(Course c:course){
            courseName.add(c.getName());
        }
        String courseNames="";
        for(String name:courseName){
            courseNames=courseNames+", "+name;
        }
        return "[" +courseNames.substring(2,courseNames.length())+"]";
    }
    /**
     *
     * @return: the string format in which the information will be printed
     */
    @Override
    public String toString(){
        return "User{username="+this.username+", type="+this.type+", courses="+printCourses(this.courses)+"}";
    }
    //@Override
    /**
     *
     * @param other: other user to be ocmpared
     * @return: an integer which indicates whether the user is less than or greater than or equal to the other user
     */
    public int compareTo(User other){
        return this.username.compareTo(other.username);
    }
}