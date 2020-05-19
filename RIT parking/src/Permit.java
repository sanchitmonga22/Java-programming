/**
 * A class to represent a parking permit.
 *
 * @author Sean Strout @ RIT CS
 */
public class Permit {
    public enum Type {
        HANDICAPPED,
        RESERVED,
        GENERAL,
    }

    /** the unique id for this permit */
    private int id;

    /** permit type */
    private Type type;

    /**
     * Create a new permit.
     *
     * @param id permit id
     * @param type type of permit
     */
    public Permit(int id, Type type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Get the permit id.
     *
     * @return permit id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the permit type.
     *
     * @return permit type
     */
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Permit{" +
                "id=" + this.id +
                ", type=" + this.type +
                '}';
    }

    /**
     * Verify a permit has the correct id and type.
     *
     * @param permitVar the name of the Permit variable
     * @param p the Permit to test
     * @param id the expected id of the permit
     * @param type the expected type of the permit
     */
    private static void verifyPermit(String permitVar, Permit p, int id, Type type) {
        System.out.println(permitVar + ": id=" + id + "? " + (p.getId() == id ?
                "OK" : "FAIL, got: " + p.getId()));
        System.out.println(permitVar + ": type=" + type + "? " + (p.getType() == type ?
                "OK" : "FAIL, got: " + p.getType()));
        System.out.println("visually verify " + permitVar + "'s toString: " + p);
        System.out.println();
    }

    /**
     * The main test function for the Permit class.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        // create a a permit, p1, whose id is 1 and is handicapped
        Permit p1 = new Permit(1, Type.HANDICAPPED);
        verifyPermit("p1", p1, 1, Type.HANDICAPPED);

        // create a a permit, p2, whose id is 2 and is reserved
        Permit p2 = new Permit(2, Type.RESERVED);
        verifyPermit("p2", p2, 2, Type.RESERVED);

        // create a a permit, p3, whose id is 3 and is general
        Permit p3 = new Permit(3, Type.GENERAL);
        verifyPermit("p3", p3, 3, Type.GENERAL);
    }
}
