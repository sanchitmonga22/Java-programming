/**
 * An exception class used to capture the various errors that can occur while
 * running the parking simulation.
 *
 * @author Sean Strout @ RIT CS
 */
public class ParkingException extends Exception {
    // store the command associated with the exception
    RITParking.Command cmd;

    /**
     * Create a new exception.
     *
     * @param cmd the command associated with the exception
     * @param msg the string message associated with the exception
     */
    public ParkingException(RITParking.Command cmd, String msg) {
        super(msg);
        this.cmd = cmd;
    }

    @Override
    public String getMessage() {
        return "Command: " + this.cmd.name() + ", Message: " + super.getMessage();
    }
}
