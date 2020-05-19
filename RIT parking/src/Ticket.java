import java.util.Date;

/**
 * A class to represent a single parking ticket.
 *
 * @author Sean Strout @ RIT CS
 */
public class Ticket {
    /** the next ticket id to issue (starting at 1) */
    private static int ticketId = 1;

    /** the unique id for this ticket */
    private int id;

    /** the time this ticket was issued */
    private Date time;

    /** the vehicle plate number the ticket was issued to */
    private int plate;

    /** the fine associated with the ticket */
    private Fine fine;

    /**
     * Create a new ticket.  It should get the next id available and then
     * increment the next id.  The time should be set to the current Date.
     * The plate should be the vehicles plate number and the fine should
     * be set to what is passed in.
     *
     * @param plate the vehicle's plate number
     * @param fine the fine associated with the ticket
     */
    public Ticket(int plate, Fine fine) {
        this.id = ticketId++;
        this.time = new Date();
        this.plate = plate;
        this.fine = fine;
    }

    /**
     * Get the ticket id.
     *
     * @return ticket id
     */
    public int getId() { return this.id; }

    /**
     * Get the time of the ticket
     *
     * @return time of ticket
     */
    public Date getDate() { return this.time; }

    /**
     * Get the vehicle plate associated with the ticket
     *
     * @return plate number of vehicle
     */
    public int getPlate() { return this.plate; }

    /**
     * Get the fine associated with a ticket
     *
     * @return the fine
     */
    public Fine getFine() { return this.fine; }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + this.id +
                ", time=" + this.time +
                ", plate=" + this.plate +
                ", fine=" + this.fine +
                ", amount=" + this.fine.amount() +
                '}';
    }

    /**
     * Two tickets are equal if they have the same ticket number.
     *
     * @param other the other object to compare true
     * @return whether they are equal or not
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Ticket) {
            Ticket t = (Ticket)other;
            result = this.id == t.id;
        }
        return result;
    }

    /**
     * The main test method.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        // create a ticket, t1, associated with vehicle 10 and a
        // fine of parking in a reserved spot
        Ticket t1 = new Ticket(10, Fine.PARKING_RESERVED);

        // verify each of the accessors:
        //      getId() -> 1
        //      getDate() -> {some timestamp}
        //      getPlate() -> 10
        //      getFine() -> fine: PARKING_RESERVED
        System.out.println("t1 id is 1? " + (1 == t1.getId() ? "OK" : "FAIL, got: " + t1.getId()));
        Date date = new Date();
        System.out.println("t1 date is ok? " + (date.getTime() >= t1.getDate().getTime() ?
                        "OK" : "FAIL, got:" + date.getTime()));
        System.out.println("t1 plate is 10? " + (10 == t1.getPlate() ? "OK" : "FAIL, got: " + t1.getPlate()));
        System.out.println("t1 fine is parking in reserved? " + (Fine.PARKING_RESERVED == t1.getFine() ?
                "OK" : "FAIL, got: " + t1.getFine()));

        // test t1 toString() - note because the ticket time is going to be different it has to
        // be visually verified, expect:
        //  t1: Ticket{id=1, time={some timestamp}, plate=10, fine=PARKING_RESERVED, amount=50}
        System.out.println("visually verify t1.toString (id=1, plate=10, fine=PARKING_RESERVED, amount=50: " + t1);

        // create a second ticket t2, associated with vehicle 10 and a fine of
        // no permit.
        Ticket t2 = new Ticket(20, Fine.NO_PERMIT);

        // test t2.toString() - should get:
        //  t2: Ticket{id=2, time={some timestamp}, plate=20, fine=NO_PERMIT, amount=30}
        System.out.println("visually verify t2.toString (id=2, plate=20, fine=NO_PERMIT, amount=30): " + t2);

        // check that t1 equals t2, but does not equal t3 or the string "t1":
        //      t1 equals t1? true
        //      t1 equals t2? false
        //      t1 equals "t1"? false
        System.out.println("t1 equals t1? " + (t1.equals(t1) ? "OK" : "FAIL"));
        System.out.println("t1 does not equal t2? " + (!t1.equals(t2) ? "OK" : "FAIL"));
        System.out.println("t1 does not equal \"t1\"? " + (!t1.equals("t1") ? "OK" : "FAIL"));
    }
}
