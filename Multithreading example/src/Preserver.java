/**
 * Represents a life preserver that the Woolies must use when crossing the
 * obstacle course.  The Woolies are terrible swimmers and need them
 * in case they fall off the obstacle course into the river below.
 *
 * @author Sean Strout @ RIT CS
 */
public class Preserver {
    /** the id of the next preserver to be created */
    private static int PRESERVER_ID = 1;

    /** The maximum amount to increase the buoyancy of a preserver */
    private static final int MAX_BUOYANCY_INCREASE = 10;

    /** the id for this preserver*/
    private int id;

    /** the buoyancy of this preserver */
    private int buoyancy;

    /**
     * Construct a new preserver with a random lifetime.
     *
     * @param minBuoyancy the minimum buoyancy
     */
    public Preserver(int minBuoyancy) {
        this.id = PRESERVER_ID++;
        this.buoyancy = minBuoyancy;
        this.buoyancy += WoolieWipeout.nextInt(0, MAX_BUOYANCY_INCREASE);
    }

    /**
     * Each time unit a preserver is out, it loses 1 unit of air
     */
    public void use() { this.buoyancy -= 1; }

    /**
     * Is the preserver still buoyant?
     * @return true if the preserver air left, otherwise false
     */
    public boolean isBuoyant() { return this.buoyancy > 0; }

    /**
     * Get the current buoyancy of the preserver
     *
     * @return the preserver buoyancy
     */
    public int getBuoyancy() { return this.buoyancy; }

    @Override
    /**
     * Return a string representation of the preserver:<br>
     *     <br>
     *      Preserver(id=#, buoyancy=#)<br>
     */
    public String toString() {
        return "Preserver(id=" + this.id + ", buoyancy=" + this.buoyancy + ")";
    }
}
