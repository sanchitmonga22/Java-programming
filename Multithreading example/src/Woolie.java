import java.util.Optional;

/**
 * The Woolie class is a thread that runs and repeatedly attempts to cross
 * the obstacle course until they can finish it without them, or anyone
 * else on the course, falling.
 *
 * @author Sean Strout @ RIT CS
 * @author Sanchit Monga
 */
public class Woolie extends Thread {
    /** The minimum amount of time it takes a woolie to cross the obstacle
     * course */
    private final static int MIN_CROSSING_TIME = 1;

    /** The maximum amount of time it takes a woolie to cross the obstacle
     * course */
    private final static int MAX_CROSSING_TIME = 10;

    /** The time a woolie must wait when crossing the course for each
     *  unit of time */
    private final static int SLEEP_TIME = 500;

    /** For a single time unit, the chance a woolie has to fall of the course */
    private final static int CHANCE_TO_FALL = 5;

    /** The unique ID for this Woolie */
    private int id;

    /** A reference to the obstacle course */
    private ObstacleCourse obstacleCourse;

    /** A reference to The Kraken */
    private Kraken kraken;

    /** The total amount of time it takes this woolie to cross the course */
    private int totalCrossingTime;

    /** The current amount of crossing time the woolie has to cross the course */
    private int crossingTimeRemaining;

    /** The woolie's life preserver */
    private Optional<Preserver> preserver;   // <3 Jim

    /** The number of times this woolie fell off the obstacle course */
    private int timesFallen;

    /**
     * Create a new Woolie.  They get a unique ID and randomly generate
     * their crossing time between 1 (MIN_CROSSING_TIME) and
     * 10 (MAX_CROSSING_TIME), using the WoolieWipeout.nextInt() method.
     * To begin with, they have no life preserver, denoted by the value
     * of Optional.empty().  To start with, the Woolie has not fallen yet.
     *
     * @param id the unique ID of the Woolie
     * @param obstacleCourse a reference to the obstacle course
     * @param kraken a reference to the Kraken
     */
    public Woolie(int id, ObstacleCourse obstacleCourse, Kraken kraken) {
        preserver=Optional.empty();
        this.id=id;
        this.obstacleCourse=obstacleCourse;
        this.kraken=kraken;
        totalCrossingTime=WoolieWipeout.nextInt(MIN_CROSSING_TIME,MAX_CROSSING_TIME);
        timesFallen=0;
        crossingTimeRemaining=this.totalCrossingTime;
    }
    /**
     * Get the name of the woolie crossing the course, using their ID,
     * e.g. <tt>Woolie_1</tt>
     *
     * @return the string name of the woolie
     */
    public String getWoolieName() { return "Woolie_" + this.id; }

    /**
     * Get the total amount of time it takes the Woolie to cross the course.
     *
     * @return total time
     */
    public int getTotalCrossingTime() { return this.totalCrossingTime; }

    /**
     * Get the preserver that is assigned to the Woolie.  If the Woolie
     * has no preserver, its value will be Optional.empty().
     *
     * @return the life preserver wrapped in an Optional
     */
    public Optional<Preserver> getPreserver() {
        return this.preserver;
    }

    /**
     * Find out how many times the Woolie has fallen off the course.
     *
     * @return the number of times the Woolie fell
     */
    public int getTimesFallen() { return this.timesFallen; }

    /**
     * The Woolie's run method.  There are three steps to take: <br>
     * <br>
     * 1. wait for permission to enter the course<br>
     * 2. attempt to cross the course<br>
     * 3. exit the course<br>
     * <br>
     * <br>
     * In the first step, the Woolie must gain permission to enter the course.
     * Once granted, they retrieve a life preserver from the Kraken.
     * <br><br>
     * The second step is more involved.  While the Woolie has not completed
     * crossing,  they must first check that the course is running.  If it
     * isn't running, it means another Woolie has fallen and they must exit
     * and re-enter the course.  If the course is running, they must attempt
     * to cross for one unit of their crossing time and use one unit of the
     * air in their life preserver.  The Woolie has a 5% chance of dropping
     * each time (CHANCE_TO_FALL).  If they don't fall, they are one step
     * closer to completing the course.  Otherwise, they have fallen into the
     * river.  Once they reach the shore (automatically happens) they must
     * return to the end of the line and return their preserver to the Kraken.
     * There are two possible messages to display when crossing.  If they
     * fall:<br>
     *     <tt>WOOLIE: {Woolie} fell off the course</tt><br>
     * If they don't fall:<br>
     *     <tt>WOOLIE: {Woolie} crossing course</tt><br>
     * <br><br>
     * In the final step the Woolie must leave the course and return their
     * life preserver to the Kraken.
     */
    public void run() {
        try {
            this.obstacleCourse.enter(this);// checking and entering the course
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        preserver = Optional.of(this.kraken.getPreserver(this));// taking the preserver from the krakan
        while (this.crossingTimeRemaining>0 && this.isAlive()) {
            int random = WoolieWipeout.nextInt(1, 100);// random number between 1 and 100
            if (random <= CHANCE_TO_FALL) {// if the woolie fails
                System.out.println("\tWOOLIE:" + this + " fell off the course!");
                obstacleCourse.fallOff();// fell of the course
                timesFallen++;// increasing everytime the woolie falls
                this.crossingTimeRemaining = this.totalCrossingTime;// resetting the time
                obstacleCourse.leave(this);// leaving the obstacle course
                preserver.get().use();
                this.kraken.returnPreserver(this);// returning the woolie's preserver
                try {
                    this.obstacleCourse.enter(this);// entering the course again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                preserver = Optional.of(this.kraken.getPreserver(this));// getting the preserver for the woolie
            } else {
                if (!obstacleCourse.isRunning()) {// if the course has stopped
                    this.crossingTimeRemaining = this.totalCrossingTime;
                    obstacleCourse.leave(this);// leaving the course
                    this.kraken.returnPreserver(this);// returnign the preserver
                    try {
                        this.obstacleCourse.enter(this);// entering the course again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.kraken.getPreserver(this);
                }
                else {
                    System.out.println("\tWOOLIE:" + this + " crossing course!");
                    this.crossingTimeRemaining -= MIN_CROSSING_TIME;// decreasing the time
                    this.getPreserver().get().use();
                    try {
                        sleep(SLEEP_TIME);// pausing for the given time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        kraken.returnPreserver(this);// returning the preserver
        obstacleCourse.leave(this);// leaving the course
        System.out.println("\tWOOLIE:" + this + " finishes course!");
    }

    @Override
    public String toString() {
        return "Woolie_" + this.id + "(total=" + this.totalCrossingTime +
                ", remaining=" + this.crossingTimeRemaining +
                ", fallen=" + this.timesFallen +
                ", preserver=" + this.preserver +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Woolie woolie = (Woolie) o;

        return id == woolie.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
