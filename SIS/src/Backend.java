import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
/**
 * The backend for the SIS which calls all the other classes and performs the operations
 * author: Sanchit Monga
 */
public class Backend extends Object{

    private CourseDB courseDB;
    private UserDB userDB;

    /**
     * Constructor to initialize the data members
     * @param courseFile: name of the course file
     * @param professorFile: name of the professor file
     * @param studentFile: name of the professor file
     * @throws FileNotFoundException: If the file is not found by the compiler than it throws an exception
     */
    public Backend(String courseFile, String professorFile, String studentFile) throws FileNotFoundException {
        courseDB=new CourseDB();
        userDB=new UserDB();
        initializeCourseDB(courseFile);// for initializing the course database
        initializeUserDB(professorFile,studentFile);// for initializing the user database
    }
    /**
     * For adding the courses in the course database
     * @param user: User which can be a student or a professor has all the information relateed to it
     * @param courseIds: Id of the ocurse that has to be used
     */
    private void addCourses(User user, String[] courseIds) {
        if(user.getType()== User.UserType.PROFESSOR){
            for(String s1:courseIds){
                Course c=getCourse(Integer.parseInt(s1));
                c.addProfessor(user.getUsername());
            }
        }
        if (user.getType() == User.UserType.STUDENT) {
            for (String s1 : courseIds) {
                Course c = getCourse(Integer.parseInt(s1));
                c.addStudent(user.getUsername());
            }
        }
        for(String s1:courseIds){
            int id=Integer.parseInt(s1);
            Course c1=getCourse(id);
            user.addCourse(c1);
        }
        this.userDB.addValue(user);
    }
    /**
     *
     * @param id: Id of the course
     * @return: whether or not the course exists
     */
    public boolean courseExists(int id){
        return courseDB.hasKey(id);
    }
    /**
     * @return: the collection of all the courses that exists in the course database
     */
    public Collection<Course> getAllCourses(){
        return this.courseDB.getAllValues();
    }
    /**
     *
     * @return: returns all the users that exists in the user database
     */
    public Collection<User> getAllUsers(){
        return this.userDB.getAllValues();
    }
    /**
     *
     * @param id: ID of the couse
     * @return: the course with the particular id
     */
    public Course getCourse(int id){
        return this.courseDB.getValue(id);
    }
    /**
     *
     * @param username: Username of the user which can be a student or a professor
     * @return: the collection of courses that user is associated with
     */
    public Collection<Course> getCoursesUser(String username){
        return this.userDB.getValue(username).getCourses();
    }
    /**
     * Used to initialize the course database
     * @param courseFile: Name of the course txt file
     * @throws FileNotFoundException: Exception thrown when the file is not found
     */
    private void initializeCourseDB(String courseFile) throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(courseFile))) {
            while (in.hasNext()) {
                String[] fields = in.nextLine().split(",");
                int id=Integer.parseInt(fields[0]);
                int level=Integer.parseInt(fields[2]);
                Course c1=new Course(id,fields[1],level);
                this.courseDB.addValue(c1);
            }
        }
    }
    /**
     * Used to initialize the user database
     * @param professorFile: name of the professor file
     * @param studentFile: name of the student file
     * @throws FileNotFoundException: Exception thrown when the file is not found
     */
    private void initializeUserDB(String professorFile, String studentFile) throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(professorFile))) {
            while (in.hasNext()) {
                String[] fields = in.nextLine().split(",");
                String name=fields[0];
                Professor prof=new Professor(name);
                addCourses(prof, Arrays.copyOfRange(fields,1,fields.length));
            }
        }
        try (Scanner in = new Scanner(new File(studentFile))) {
            while (in.hasNext()) {
                String[] fields = in.nextLine().split(",");
                String name=fields[0];
                Student stud=new Student(name);
                addCourses(stud, Arrays.copyOfRange(fields,1,fields.length));
            }
        }
    }
    /**
     *
     * @param username: username of the user
     * @return: whether or not the given user is a student
     */
    public boolean isStudent(String username) {
        return userDB.getValue(username).getType()== User.UserType.STUDENT;
    }
    /**
     *
     * @param username: username of the user
     * @param courseId: course id of the course
     * @return: whether or not the user was unenrolled from the course or not
     */
    public boolean unenrollStudent(String username, int courseId) {
        User student =userDB.getValue(username);
        Course c1=this.getCourse(courseId);
        student.removeCourse(c1);
        return c1.removeStudent(username);
    }
    /**
     *
     * @param courseId: id of the course
     * @return : name of the professor teachign that particular course
     */
    private String getProfName(int courseId){
        return this.courseDB.getValue(courseId).getProfessor();
    }
    /**
     *
     * @param courseId: id of the course
     * @return: the collection of students that are taking that particular course
     */
    private Collection<String> getstudents(int courseId)
    {
        return this.courseDB.getValue(courseId).getStudents();
    }
    /**
     * Enrolling the student with the given id in the course
     * @param username : username of the user
     * @param courseId: id of the course
     * @return: whether or not the user was enrolled from the course
     */
    public boolean enrollStudent(String username, int courseId){
        Course c1=courseDB.getValue(courseId);
        User student=userDB.getValue(username);
        student.addCourse(c1);
        return c1.addStudent(username);
    }
    /**
     *
     * @param username: username of the user
     * @return: checks whether the user exists with the particular username or not
     */
    boolean userExists(String username) {
        return userDB.getValue(username) != null;
    }
}