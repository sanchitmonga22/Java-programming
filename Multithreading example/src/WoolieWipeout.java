import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Woolie Wipeout is a prime time game show televised on WTV (Woolie TV).  The
 * show is hosted and MC'd by the infamous octopus, The Kraken, who is now
 * a washed up alcoholic deadbeat in desperate need of money.<br>
 * <br><br>
 * The stars of the show are the adorably irresistible Woolies of Woolieville.
 * The contestants must attempt to cross an obstacle course that divides two
 * cliffs separated by a raging river below.  Each Woolie takes a different
 * time to cross the course.  The course is a rolling log that is very old and can
 * only support a certain number of Woolie's before falling apart.<br>
 * <br><br>
 * The Woolies are notably terrible swimmers.  To protect them from the chance
 * they fall into the water, each Woolie must have a life preserver when
 * crossing the course.  Unfortunately, the quality of the life preservers is
 * very bad.  Once blown up to full, they constantly run out of air over time.
 * Because of this, The Kraken is in charge of maintaining and distributing the
 * life preservers to the Woolies.  The Kraken must make sure to never give a
 * Woolie a life preserver that doesn't have enough air to last their
 * crossing time.<br>
 * <br><br>
 * The simulation is run on the command line with two arguments, the number
 * of Woolies and the maximum number of Woolies that can be on the course at
 * the same time:<br>
 * <br>
 *     <tt>java WoolieWipeout numWoolies maxOnCourse</tt><br>
 * <br><br>
 * When run, the program uses standard output to indicate the progress of the
 * simulation.
 *
 * @author Sean Strout @ RIT CS
 */
public class WoolieWipeout {
    /** Random number generator used by all classes */
    private static Random rand = new Random();

    /** The beloved obstacle course */
    private ObstacleCourse obstacleCourse;

    /** the collection of brave woolies */
    private List<Woolie> woolies;

    /** The legendary and fearsome host and MC of Woolie Wipeout */
    private Kraken kraken;

    /**
     * Generate a random integer between min and max inclusive.  For example: <br>
     * <br>
     * <tt>WoolieWipeout.nextInt(1, 5): A random number, 1-5</tt><br>
     * <br>
     *
     * @param min the smallest value allowed.
     * @param max the largest value allowed.
     * @return A random integer
     */
    public static int nextInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * Create the Woolie Wipeout simulation.  This involves creating all the Woolies,
     * the obstacle course and of course, the Kraken!
     *
     * @param numWoolies the total number of Woolies in the simulation
     * @param maxOnCourse the maximum number of Woolies allowed to cross the course
     *      at the same time
     */
    public WoolieWipeout(int numWoolies, int maxOnCourse) {
        this.obstacleCourse = new ObstacleCourse(maxOnCourse);
        this.kraken = new Kraken(this.obstacleCourse);
        this.woolies = new ArrayList<>();
        for (int id=1; id<=numWoolies; ++id) {
            this.woolies.add(new Woolie(id, this.obstacleCourse, this.kraken));
        }
    }

    /**
     * Run the entire simulation.
     */
    public void go() {
        System.out.println("SIM: Woolie Wipeout has begun with " +
                this.woolies.size() + " woolie/s and a maximum of " +
                this.obstacleCourse.getMaxOnCourse() + " woolie/s on the course");

        // launch the kraken
        this.kraken.start();

        // launch the woolie threads
        for (Woolie woolies : this.woolies) {
            woolies.start();
        }

        // wait for them all the woolies to cross the course
        for (Woolie woolies : this.woolies) {
            try {
                woolies.join();
            } catch (InterruptedException ie) {
                System.err.println(ie.getMessage());
            }
        }

        // close the obstacle course and wait for the kraken to finish
        this.obstacleCourse.setClosed();
        try {
            this.kraken.join();
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }

        System.out.println("SIM: Woolie Wipeout has ended...");
    }

    /**
     * Display results about the woolies who just ran the simulation
     */
    public void displayWoolies() {
        System.out.println("SIM: Woolie results:");
        this.woolies.stream()
                .forEach(w -> System.out.println("\t" + w));
    }

    /**
     * Displays the clumsiest Woolies - the ones who fell off the course the
     * most most.
     */
    private void displayClumsiest() {
        Optional<Integer> maxDrops = this.woolies.stream()
                .map(Woolie::getTimesFallen)
                .max((s1, s2) -> s1 - s2);
        if ( maxDrops.isPresent() ) {
            int max = maxDrops.get();
            System.out.println("SIM: Woolie/s who fell the most, "
                    + max + " time" + ( (max!=1)?"s":"" ) + ":");
            this.woolies.stream()
                    .filter(p -> p.getTimesFallen() == max)
                    .forEach(p -> System.out.println("\t"+p) );
        }
        else {
            System.out.println("SIM: No Woolies fell off the course this run.");
        }
    }

    /**
     * Displays the status of all preservers dispensed
     */
    private void displayPreservers() {
        System.out.println("SIM: Number of preservers dispensed: " + this.kraken.getPreserversDispensed());
        System.out.println("SIM: Remaining preservers:");
        for (Preserver preserver : this.kraken.getRemainingPreservers()) {
            System.out.println("\t" + preserver);
        }
        System.out.println("SIM: Destroyed preservers:");
        for (Preserver preserver : this.kraken.getDestroyedPreservers()) {
            System.out.println("\t" + preserver);
        }
    }

    /**
     * Display the results of the current simulation run
     */
    public void displayResults() {
        displayWoolies();
        displayClumsiest();
        displayPreservers();
    }

    /**
     * The main method.  If the arguments are supplied, the simulation is
     * run and the results are displayed at the end.
     *
     * @param args The command line arguments: numWoolies maxOnCourse
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java WoolieWipeout numWoolies maxOnCourse");
        } else {
            WoolieWipeout sim = new WoolieWipeout(Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]));
            sim.go();
            sim.displayResults();
        }
    }
}
