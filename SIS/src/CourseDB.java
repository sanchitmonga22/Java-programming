import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
/**
 * Creating the course database
 * author: Sanchit Monga
 */
public class CourseDB implements DB<Integer,Course> {
    private HashMap<Integer,Course> courses;
    /**
     * The course database which is a hashmap and can perform operations in constant time
     */
    public CourseDB(){
        this.courses = new HashMap<Integer, Course>();
    }
    /**
     *
     * @param course: Course to be added to the database
     * @return: the previous course if there was any
     */
    public Course addValue(Course course){
        int key=course.hashCode();
        if(this.courses.containsKey(key)){
            Course previous=this.courses.get(key);
            this.courses.put(key,course);
            return previous;
        }
        this.courses.put(key,course);
        return null;
    }
    /**
     *
     * @return: Collection of all the values in the natural ordering
     */
    public Collection<Course> getAllValues(){
        Collection<Course> all= new TreeSet<Course>();
        all.addAll(this.courses.values());
        return all;
    }
    /**
     *
     * @param id: id which is the key to access the value in the course DB
     * @return
     */
    public Course getValue(Integer id){
        return this.courses.get(id);
    }
    /**
     *
     * @param id: id which is the key to access the value in the course DB
     * @return: whether the key exists or not
     */
    public boolean hasKey(Integer id){
        return this.courses.containsKey(id);
    }
}
