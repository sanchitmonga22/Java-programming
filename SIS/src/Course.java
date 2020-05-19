import java.util.*;

/**
 * Represents a course.
 *
 * @author Sean Strout @ RIT CS
 * @author Sanchit Monga
 */
public class Course implements Comparable<Course> {
    /** course id (unique) */
    private int id;
    /** course name */
    private String name;
    /** course level */
    private int level;
    /** professor teaching the course, null if none */
    private String professor;
    /** students enrolled in the course (unique), empty if none */
    private HashSet<String> students;

    /**
     * Create a course.  Initially there is no professor or student for the
     * course.
     *
     * @param id course id
     * @param name course name
     * @param level course level
     */
    public Course(int id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.professor = null;
        this.students = new HashSet<>();
    }

    /**
     * Get the course id.
     *
     * @return course id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the course name.
     *
     * @return course name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the course level.
     *
     * @return course level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Get the professor teaching the course.
     *
     * @return the professor, if none, null
     */
    public String getProfessor() {
        return this.professor;
    }

    /**
     * Get the students enrolled in the course.
     *
     * @return the students, organized by ascending hash codes. if there
     *  are no students enrolled the list should be empty.
     */
    public Collection<String> getStudents() {
        return this.students;
    }

    /**
     * Make a professor the instructor of this course.  If another professor
     * was teaching it, that information is lost.
     *
     * @param username the username of the professor
     */
    public void addProfessor(String username) {
        this.professor=username;
    }

    /**
     * Add a student to the course, if they are not enrolled,
     * <b>in constant time</b>.
     *
     * @param username the username of the student
     * @return whether the student was added or not
     */
    public boolean addStudent(String username) {
        if(this.students.contains(username)){
            return false;
        }
        this.students.add(username);
        return true;
    }

    /**
     * Remove a student from the course, if they are enrolled,
     * <b>in constant time</b>.
     *
     * @param username the username of the student to remove
     * @return true if the student was removed, false if the student was not in the course
     */
    public boolean removeStudent(String username) {
        if(this.students.contains(username)){
            this.students.remove(username);
            return true;
        }
        return false;
    }

    /**
     * Compare courses naturally by ascending course id.
     *
     * @param other the other course to compare to
     * @return a value less than 0 if this course has an id less the other course,
     * 0 if the two courses have the same id, or a value greater than 0 if this
     * course has an id greater than the other course.
     */
    @Override
    public int compareTo(Course other) {
        int diff=this.getId()-other.id;
        return diff;
    }

    /**
     * Two courses are equal if they have the same course id.
     *
     * @param other the other course
     * @return true if
     */
    @Override
    public boolean equals(Object other) {
        return this.compareTo((Course)other)==0;
    }

    /**
     * The hash code of a course is the course's id.
     *
     * @return the hash code
     */
    @Override
    public int hashCode(){
        return this.getId();
    }
    /**
     * Return a string representation of the course in the format:<br>
     * <br>
     * Course{id=ID, name=NAME, level=LEVEL, professor=PROF_USERNAME, students=STUDENT_LIST}<br>
     * <br>
     * Here, STUDENT_LIST should be a list of students username, displayed in traditional
     * bracketed list format, but organized by ascending hash code.  Note, in IntelliJ
     * you can have this automatically generated if you go to Code then Generate and select
     * toString.
     *
     * @return the formatted string
     */
    @Override
    public String toString() {
        return "Course{id="+this.id+", name='"+this.name+"', level="+this.level+", professor="+this.professor+", students="+this.students+"}";
    }

    /**
     * The main method for testing the Course class.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        // create an assortment of Course objects
        Course cs1 = new Course(1, "Computer_Science_1", 100);
        Course cs2 = new Course(2, "Computer_Science_2", 100);
        Course mop = new Course(3, "Mechanics_of_Programming", 200);
        Course algo = new Course(4, "Analysis_Of_Algorithms", 200);
        Course plc = new Course(5, "Programming_Language_Concepts", 300);

        // Activity 1: make the list contains() method work
        System.out.println("Activity 1: list contains");
        List<Course> courseList = new ArrayList<>();
        courseList.add(cs1);
        courseList.add(cs2);
        courseList.add(mop);
        courseList.add(algo);
        System.out.println("courseList.contains(mop)? " + (courseList.contains(mop) ? "OK" : "FAIL!"));
        System.out.println("courseList.contains(plc)? " + (!courseList.contains(plc) ? "OK" : "FAIL!"));

        // Activity 2: print tree of courses by ascending course id
        System.out.println("\nActivity 2: print tree of courses by ascending course id");
        Set<Course> courseTree1 = new TreeSet<>();
        courseTree1.add(algo);
        courseTree1.add(mop);
        courseTree1.add(cs2);
        courseTree1.add(plc);
        courseTree1.add(cs1);
        courseTree1.add(mop);  // intentionally add a duplicate
        for (Course course : courseTree1) {
            System.out.println(course);
        }

        // Activity 3: print the courses alphabetically by name
        System.out.println("\nActivity 3: print tree of courses alphabetically by name");
        Set<Course> courseTree2 = new TreeSet<>(new CourseComparator());
        courseTree2.add(cs1);
        courseTree2.add(cs2);
        courseTree2.add(mop);
        courseTree2.add(algo);
        courseTree2.add(plc);
        for (Course course : courseTree2) {
            System.out.println(course);
        }

        // Activity 4: print the course names in the map where a grade of A was received.
        System.out.println("\nActivity 4: print courses in map with a grade of A");
        Map<Course, String> courseMap = new HashMap<>();
        courseMap.put(cs1, "A-");
        courseMap.put(cs2, "A");
        courseMap.put(mop, "B-");
        courseMap.put(algo, "A");
        courseMap.put(plc, "B+");
        courseMap.put(new Course(1, "Computer_Science_1", 100), "A-"); // change cs1 grade to A-
        for(Course c: courseMap.keySet()){
            if(courseMap.get(c).equals("A")){
                System.out.println(c.name);
            }
        }
    }
}

/*
EXPECTED OUTPUT:
Activity 1: list contains
courseList.contains(mop)? OK
!courseList.contains(plc)? OK

Activity 2: print tree of courses by ascending course id
Course{id=1, name='Computer_Science_1', level=100, professor=null, students=[]}
Course{id=2, name='Computer_Science_2', level=100, professor=null, students=[]}
Course{id=3, name='Mechanics_of_Programming', level=200, professor=null, students=[]}
Course{id=4, name='Analysis_Of_Algorithms', level=200, professor=null, students=[]}
Course{id=5, name='Programming_Language_Concepts', level=300, professor=null, students=[]}

Activity 3: print tree of courses alphabetically by name
Course{id=4, name='Analysis_Of_Algorithms', level=200, professor=null, students=[]}
Course{id=1, name='Computer_Science_1', level=100, professor=null, students=[]}
Course{id=2, name='Computer_Science_2', level=100, professor=null, students=[]}
Course{id=3, name='Mechanics_of_Programming', level=200, professor=null, students=[]}
Course{id=5, name='Programming_Language_Concepts', level=300, professor=null, students=[]}

Activity 4: print courses in map with a grade of A
Computer_Science_2
Analysis_Of_Algorithms
*/
