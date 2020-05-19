import java.util.*;

/**
 * The Kraken is the salty host of the Woolie Wiepeout game show.  His job is
 * to maintain the life preservers that are handed to and back from the
 * Woolies.  He also makes sure that when a Woolie falls, that the course
 * is temporarily stopped from running.    During this time, it is up to the
 * Woolies that are currently crossing to back up off the course and return
 * to the end of the line.  The Kraken will not turn the course back on until
 * there are no Woolies left on the course.
 *
 * @author Sean Strout @ RIT CS
 */
public class Kraken extends Thread{
    /** The amount of time the kraken sleeps between checking if
     * a woolie has fallen off the course */
    private final static int SLEEP_TIME = 250;

    /** The Kraken has a reference to the obstacle course so that it can
     * turn it on/off when a Woolie falls */
    private ObstacleCourse obstacleCourse;

    /** The life preservers that the Woolies can use upon request are
     * in a stack */
    private Stack<Preserver> preservers;

    /** A life preserver is destroyed when it runs out of air, or doesn't
     * have enough air for the Woolie who is requesting it */
    private List<Preserver> destroyedPreservers;

    /** the number of preservers created */
    private int numPreservers;

    /**
     * Create The Kraken!
     *
     * @param obstacleCourse a reference to the obstacle course
     */
    public Kraken(ObstacleCourse obstacleCourse) {
        this.obstacleCourse = obstacleCourse;
        this.preservers = new Stack<>();
        this.destroyedPreservers = new LinkedList<>();
        this.numPreservers = 0;
    }

    /**
     * Get the total number of life preservers dispensed.
     *
     * @return total dispensers dispensed
     */
    public int getPreserversDispensed() { return this.numPreservers; }

    /**
     * Find out how many preservers are left in the stack.
     *
     * @return dispensers left in stack
     */
    public Collection<Preserver> getRemainingPreservers() {
        return this.preservers;
    }

    /**
     * Find out how many preservers were destroyed.  The Kraken destroys them
     * when they are returned by the Woolie and have no left in them.
     *
     * @return number of preservers destroyed
     */
    public Collection<Preserver> getDestroyedPreservers() {
        return this.destroyedPreservers;
    }

    /**
     * Get a life preserver from the dispenser for the Woolie.  If there are no
     * preservers in the dispenser, or the preserver in the front does not
     * have enough air for the Woolie to cross, the Kraken creates and returns
     * a new one guaranteed to have enough air for them.  Otherwise, the
     * preserver in the front of the dispenser is returned.
     *
     * @param woolie the Woolie requesting the preserver
     * @return A preserver (used/new)
     */
    public synchronized Preserver getPreserver(Woolie woolie) {
        Preserver preserver = null;
        if (this.preservers.size() == 0) {
            ++this.numPreservers;
            preserver = new Preserver(woolie.getTotalCrossingTime());
        } else if (woolie.getTotalCrossingTime() > this.preservers.get(0).getBuoyancy()) {
            ++this.numPreservers;
            System.out.println("KRAKEN: " + woolie.getWoolieName() + " can't use " + this.preservers.get(0));
            preserver = new Preserver(woolie.getTotalCrossingTime());
        } else {
            preserver = this.preservers.remove(0);
        }
        System.out.println("KRAKEN: " + woolie.getWoolieName() + " gets " + preserver);
        return preserver;
    }

    /**
     * Return a preserver to the dispenser.  This is called when a Woolie
     * completes the course, or after falling in the water and returning
     * to the end of the line.  If the preserver still has air, it is
     * added to the front of the dispenser, otherwise it is destroyed.
     *
     * @param woolie the woolie returning the preserver
     */
    public synchronized void returnPreserver(Woolie woolie) {
        // get preserver back from woolie.  it is a problem if they don't have one
        Optional<Preserver> preserver = woolie.getPreserver();
        if (!preserver.isPresent()) {
            System.err.println("KRAKEN:  Error, " + woolie.getWoolieName() +
            " did not return a preserver!");
            System.exit(-1);
        }

        System.out.println("KRAKEN: " + woolie.getWoolieName() + " returns " + preserver.get());
        if (preserver.get().isBuoyant()) {
            this.preservers.add(0, preserver.get());
        } else {
            System.out.println("KRAKEN: " + woolie.getName() + " destroys " + preserver);
            this.destroyedPreservers.add(preserver.get());
        }
    }

    /**
     * The Kraken runs this method repeatedly throughout the simulation to check
     * if any Woolies have fallen off the obstacle course.  If a Woolie falls,
     * the obstacle course is temporarily turned off so that any Woolies
     * currently crossing it can get off it and return to the back of the line.
     * Once all the Woolies on the course have left, the course is turned
     * back on and normal execution continues.
     */
    private void checkObstacleCourse() {
        if (this.obstacleCourse.hasWoolieFallen()) {
            System.out.println("KRAKEN: A Woolie has fallen!  Turning off course...");
            this.obstacleCourse.turnOff();
            System.out.println(this.obstacleCourse);
            while (this.obstacleCourse.isOccupied()) {
                System.out.println("KRAKEN: A Woolie has fallen!  Waiting for Woolies to exit course...");
                System.out.println("KRAKEN: " + this.obstacleCourse);
                try {
                    sleep(SLEEP_TIME);
                } catch (InterruptedException ie) {
                    System.err.println(ie.getMessage());
                }
            }
            System.out.println("KRAKEN: All Woolies have exited course!  Restarting course...");
            this.obstacleCourse.turnOn();
        }
    }

    /**
     * The run method for the Kraken thread has three parts.  First, the
     * obstacle course is turned on.  Second, the Kraken enters a check/sleep
     * loop to handle any case where a Woolie falls off the course.  Third,
     * once all Woolies have crossed, the Kraken turns off the course
     * and retires for the day.
     */
    public void run() {
        System.out.println("KRAKEN: let the show begin!");
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        System.out.println("KRAKEN: turning on course...");
        this.obstacleCourse.turnOn();
        System.out.println("KRAKEN: " + this.obstacleCourse);
        while (this.obstacleCourse.isOpen()) {
            checkObstacleCourse();
            try {
                sleep(SLEEP_TIME);
            } catch (InterruptedException ie) {
                System.err.println(ie.getMessage());
            }
        }
        System.out.println("KRAKEN: turning off course...");
        this.obstacleCourse.turnOff();
        System.out.println("KRAKEN: " + this.obstacleCourse);
        System.out.println("KRAKEN: that's all folks!");
    }
}
