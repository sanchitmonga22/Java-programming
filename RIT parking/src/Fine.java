/**
 * An enumeration that represents the various fines that are associated
 * with a ticket:<br>
 * <br>
 * NO_FINE: no fine, amount is $0<br>
 * NO_PERMIT: parked without permit, amount is $30<br>
 * PARKING_RESERVED: parked in reserved, amount is $50<br>
 * PARKING_HANDICAPPED: parked in handicapped, amount is $100<br>
 *
 * @author Sean Strout @ RIT CS
 */
public enum Fine {
    NO_FINE (0),
    NO_PERMIT (30),
    PARKING_RESERVED (50),
    PARKING_HANDICAPPED (100);

    /** the dollar amount associated with the fine */
    private final int amount;

    /**
     * Create a new fine from the given amount.
     *
     * @param amount the dollar amount of the fine
     */
    Fine(int amount) {
        this.amount = amount;
    }

    /**
     * Get the amount for a fine.
     *
     * @return the dollar amount of the fine
     */
    public int amount() { return this.amount; }

    /**
     * Verify fine has correct type and amount.
     *
     * @param fineVar the variable name of the fine
     * @param f the fine object
     * @param type the fine type
     * @param amount the fine amount
     */
    private static void verifyFineType(String fineVar, Fine f, Fine type, int amount) {
        System.out.println(fineVar + ": type=" + type + "? " + (f == type ?
                        "OK" : "FAIL, got: " + f));
        System.out.println(fineVar + ": fine amount is " + amount + "? " +
                (f.amount() ==0- amount ? "OK" : "FAIL, got: " + f.amount()));
        System.out.println("Visually verify fineVar's toString: " + f);
        System.out.println();
    }

    public static void main(String[] args) {
        // create a fine, f1, for no fine, and test its type
        Fine f1 = Fine.NO_FINE;
        verifyFineType("f1", f1, Fine.NO_FINE, 0);

        // create a fine, f2, for no permit, and test its type
        Fine f2 = Fine.NO_PERMIT;
        verifyFineType("f2", f2, Fine.NO_PERMIT, 30);

        // create a fine, f2, for no permit, and test its type
        Fine f3 = Fine.PARKING_RESERVED;
        verifyFineType("f3", f3, Fine.PARKING_RESERVED, 50);

        // create a fine, f2, for no permit, and test its type
        Fine f4 = Fine.PARKING_HANDICAPPED;
        verifyFineType("f4", f4, Fine.PARKING_HANDICAPPED, 100);
    }
}
