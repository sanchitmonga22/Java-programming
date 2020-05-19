import java.util.ArrayList;

/**
 * Represents a vehicle in the parking simulator.
 *
 * @author Sean Strout @ RIT CS
 * @author Sanchit Monga
 * email-sm3468@rit.edu
 */
public class Vehicle {

    /**
     * The main method runs a suite of test cases for the Vehicle class.
     *      <br>
     *      Step 1.  Stub out all the methods of the Vehicle class.  Use the
     *          javadocs to get method signatures.  For methods that return
     *          objects, return null.  For methods that return a boolean,
     *          return false.  For methods that return an integer, return 0.<br>
     *      <br><br>
     *      Step 2.  Run the program and it should fail every test.
     *      <br><br>
     *      Step 3.  Implement the Vehicle methods in the following order.  Use the
     *          UML diagram and javadocs to assist you.  After writing each method you
     *          should rerun the test method to make sure it produces the correct output.<br>
     *      <br><br>
     *              1. Add the private state and implement the constructor.<br>
     *              2. Implement getPlate()<br>
     *              3. Implement isParked()<br>
     *              4. Implement hasPermit()<br>
     *              5. Implement getPermit()<br>
     *              6. Implement getTickets()<br>
     *              7. Implement toString()<br>
     *              8. Implement setParked()<br>
     *              9. Implement equals()<br>
     *              10. Implement setPermit()<br>
     *              11. Implement giveTicket()<br>
     *      <br><br>
     *           It is expected that each test should produce "OK" for output.
     * @param args command line arguments (unused)
     */
    private int plate;
    private boolean parked;
    private Permit permit;
    private ArrayList<Ticket> ticket;

    /**
     * Constructor
     * @param plate The number plate of the vehicle
     */
    public Vehicle(int plate){
        this.plate=plate;
        this.ticket= new ArrayList<Ticket>();
        this.parked=false;
        this.permit=null;
    }

    /**
     * Used by vehicle to get the plate number
     * @return Returns the plate number
     */
    public int getPlate(){
        return this.plate;
    }

    /**
     *Checks whether the vehicle is parked or not
     * @return returns whether or not the vehicle is parked
     */
    public boolean isParked() {
        return this.parked;
    }
    /**
     * Checks whether the vehicle has the permit or not
     * @return true/false
     */
    public boolean hasPermit() {
        if (this.permit == null) {
            return false;
        }
        return true;
    }
    /**
     *
     * @return Returns the permit object of the vehicle
     */
    public Permit getPermit(){
        return this.permit;
    }
    /**
     *
     * @return Returns the current tickets issued by the parking officer
     */
    public ArrayList<Ticket> getTickets(){
        return this.ticket;
    }
    /**
     *
     * @return Returns the String which prints the detail of the vehicle
     */
    @Override
    public String toString(){
        return "Vehicle{"+
                "plate="+this.plate+
                ", permit="+this.permit+
                ", parked="+this.parked+
                ", tickets="+this.ticket+
                "}";
    }
    /**
     *
     * @param parked Tells whether the vehicle was parked or not
     */
    public void setParked(boolean parked){
        this.parked=parked;
    }

    /**
     *
     * @param other The vehicle object that has to be checked
     * @return Whether or not the passed object of a vehicle is equal to the given vehicle
     */
    @Override
    public boolean equals(Object other) {
        boolean result=false;
        if(other instanceof Vehicle) {
            Vehicle f=(Vehicle) other;
            result=this.plate==f.plate;
        }
        return result;
    }
    /**
     * Sets the permit of the vehicle
     * @param permit Returns the permit
     */
    public void setPermit(Permit permit){
        this.permit=permit;
    }
    /**
     * @param ticket returns the ticket object
     */
    public void giveTicket(Ticket ticket) {
        this.ticket.add(ticket);
    }
    public static void main(String[] args) {
        // create a Vehicle, v1, whose plate number is 10, is unparked, with no
        // permit and no tickets (an empty list).
        Vehicle v1 = new Vehicle(10);

        // call the following operations on v1 and print each result:
        //      getPlate() -> 10
        //      isParked() -> false
        //      hasPermit() -> false
        //      getPermit() -> null
        //      getTickets() -> []
        System.out.println("v1 plate is 10? " + (10 == v1.getPlate() ? "OK" :
                "FAIL, got: " + v1.getPlate()));
        System.out.println("v1 is not parked? " + (!v1.isParked() ? "OK" : "FAIL"));
        System.out.println("v1 has no permit? " + (!v1.hasPermit() ? "OK" : "FAIL"));
        System.out.println("v1 permit is null? " + (v1.getPermit() == null ? "OK" :
                "FAIL, got: " + v1.getPermit()));
        System.out.println("v1 has no tickets? " + (v1.getTickets() != null && v1.getTickets().size() == 0 ?
                "OK" : "FAIL, got: " + v1.getTickets()));

        // check's Vehicle's toString(), you should get:
        //      Vehicle{plate=10, permit=null, parked=false, tickets=[]}
        System.out.println("v1 toString?: " +
            (v1.toString() != null && v1.toString().equals("Vehicle{plate=10, permit=null, parked=false, tickets=[]}") ?
                    "OK" : "FAIL, got: " + v1.toString()));

        // create a second Vehicle v2, whose plate number is 20, park it, test
        // it is parked and then its toString(), you should get:
        //      v2: Vehicle{plate=20, permit=null, parked=true, tickets=[]}
        Vehicle v2 = new Vehicle(20);
        v2.setParked(true);
        System.out.println("v2 toString?: " +
                (v2.toString() != null && v2.toString().equals("Vehicle{plate=20, permit=null, parked=true, tickets=[]}") ?
                        "OK" : "FAIL, got: " + v2.toString()));

        // create a third Vehicle v3, whose plate number is also 20 test it's
        // toString() is:
        //      v3: Vehicle{plate=20, permit=null, parked=false, tickets=[]}
        Vehicle v3 = new Vehicle(20);
        System.out.println("v3 toString?: " +
                (v3.toString() != null && v3.toString().equals("Vehicle{plate=20, permit=null, parked=false, tickets=[]}") ?
                        "OK" : "FAIL, got: " + v3.toString()));

        // verify that v2 and v3 are equal because they have the same plate number:
        System.out.println("v2 equals v3? " + (v2.equals(v3) ? "OK" : "FAIL"));

        // verify that v1 and v3 are not equal:
        System.out.println("v1 does not equal v3? " + (!v1.equals(v3) ? "OK" : "FAIL"));

        // verify that v1 is not equal to the string, "v1":
        System.out.println("v1 does not equal \"v1\"? " + (!v1.equals("v1") ? "OK" : "FAIL"));

        // issue a handicapped permit, p1, to v1 and make sure v1 has the permit
        Permit p1 = new Permit(1, Permit.Type.HANDICAPPED);
        v1.setPermit(p1);
        System.out.println("v1 with permit p1? " + (v1.getPermit() != null && v1.getPermit().equals(p1) ? "OK" : "FAIL"));

        // give a ticket, t2, to v2 for parking without a permit and verify it has it
        Ticket t1 = new Ticket(v2.getPlate(), Fine.NO_PERMIT);
        v2.giveTicket(t1);
        System.out.println("v2 with ticket t1? " + (v2.getTickets() != null &&
                v2.getTickets().get(0).equals(t1) ? "OK" : "FAIL"));

        // give another ticket, t3, to v2 and verify it has it as well
        Ticket t2 = new Ticket(v2.getPlate(), Fine.PARKING_HANDICAPPED);
        v2.giveTicket(t2);
        System.out.println("v2 with another ticket t2? " + (v2.getTickets() != null && v2.getTickets().get(1).equals(t2) ?
                "OK" : "FAIL"));
    }
}
