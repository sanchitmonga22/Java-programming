import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main parking simulation program.  To run on the command line:<br>
 * <br><tt>
 * $ java RITParking            # uses standard input to read commands<br>
 * $ java RITParking filename   # uses filename to read commands<br>
 * </tt>
 *
 * @author Sean Strout @ RIT CS
 */
public class RITParking {
    // the various commands the simulation responds to
    public enum Command {
        CREATE,   // create prompt command
        DISPLAY,  // display lot command
        GET,      // get vehicle in spot command
        HELP,     // display help command
        ISSUE,    // issue permit command
        LOT,      // lot prompt command
        MAKE,     // make vehicle command
        PARK,     // park car command
        PATROL,   // patrol lot command
        PERMIT,   // add permit command
        QUIT,     // exit the simulation
        REMOVE,   // remove vehicle from lot command
        TICKETS,  // get the tickets issues by command
        VEHICLES  // get the current vehicle's status command
    }

    /** the parking lot of vehicles */
    private ParkingLot parkingLot;

    /* the parking officer */
    private ParkingOfficer officer;

    /** all the vehicles in the simulation */
    private ArrayList<Vehicle> vehicles;

    /** all the permits issued */
    private ArrayList<Permit> permits;

    /**
     * Pause the caller (officer) for a certain amount of time.
     *
     * @param amount the amount of time to pause in milliseconds
     */
    public static void pause(int amount) {
        try {
            Thread.sleep(amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new simulation.
     */
    public RITParking() {
        this.parkingLot = null;
        this.officer = new ParkingOfficer();
        this.vehicles = new ArrayList<>();
        this.permits = new ArrayList<>();
    }

    /**
     * Since the vehicles can be created in any order, search for the desired
     * one by plate number.
     *
     * @param plate the plate number of desired vehicle
     * @return the Vehicle object that matches the plate, otherwise null
     */
    private Vehicle findVehicle(int plate) {
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle.getPlate() == plate) {
                return vehicle;
            }
        }
        return null;
    }

    /**
     * Find the permit by the requested id.
     *
     * @param id the permit id
     * @return the permit otherwise null
     */
    private Permit findPermit(int id) {
        for (Permit permit : this.permits) {
            if (permit.getId() == id) {
                return permit;
            }
        }
        return null;
    }

    /**
     * Create a new lot.
     *
     * @param handicappedSpots the number of handicapped spots
     * @param reservedSpots the number of reserved spots
     * @param generalSpots the number of general spots
     * @throws ParkingException if any of the designated spots are negative
     */
    private void createLot(int handicappedSpots, int reservedSpots, int generalSpots) throws ParkingException {
        if (this.parkingLot != null) {
            System.out.println("Parking lot was already created!");
        } else if (handicappedSpots < 0) {
            throw new ParkingException(Command.CREATE, "number of handicapped spots can't be negative!");
        } else if (reservedSpots < 0) {
            throw new ParkingException(Command.CREATE, "number of reserved spots can't be negative!");
        } else if (generalSpots < 0) {
            throw new ParkingException(Command.CREATE, "number of general spots can't be negative!");
        } else {
            this.parkingLot = new ParkingLot(handicappedSpots, reservedSpots, generalSpots);
            this.officer.setParkingLot(this.parkingLot);
            System.out.println(this.parkingLot);
        }
    }

    /**
     * Create a new permit.
     *
     * @param id the permit id
     * @param type the permit type
     */
    private void createPermit(int id, String type) {
        if (findPermit(id) != null) {
            System.out.println("Permit with id " + id + " already exists!");
        } else {
            Permit permit = new Permit(id, Permit.Type.valueOf(type));
            this.permits.add(permit);
            System.out.println(permit);
        }
    }

    /**
     * Display the lot by spots, vehicles and tickets.
     *
     * @param item the type to display by
     */
    private void display(String item) {
        if (this.parkingLot == null) {
            System.out.println("Can't display lot, it has not been created yet!");
        } else if (item.equals(Command.LOT.name().toLowerCase())) {
            System.out.println(this.parkingLot);
        } else if (item.equals(Command.VEHICLES.name().toLowerCase())) {
            for (Vehicle v : this.vehicles) {
                System.out.println(v);
            }
        } else if (item.equals(Command.TICKETS.name().toLowerCase())) {
            for (Ticket t : this.officer.getTickets()) {
                System.out.println(t);
            }
        } else {
            System.out.println("Unknown display command, " + item + "!");
        }
    }

    /**
     * Print a vehicle parked in a spot.
     *
     * @param spot the spot number
     */
    private void getParkedVehicle(int spot) {
        if (this.parkingLot == null) {
            System.out.println("The lot has not been created yet!");
        } else if (!this.parkingLot.isSpotValid(spot)) {
            System.out.println("Parking spot is out of range!");
        } else if (this.parkingLot.isSpotVacant(spot)) {
            System.out.println("No vehicle in spot " + spot + "!");
        } else {
            Vehicle vehicle = this.parkingLot.getSpot(spot).getVehicle();
            if (vehicle != null) {
                System.out.println(vehicle);
            } else {
                System.out.println("Spot " + spot + " is unoccupied!");
            }
        }
    }

    /**
     * Display the help message of commands.
     */
    private void helpMessage() {
        System.out.println("- create lot #handicapped #reserved #general");
        System.out.println("\tcreate a new lot with the different spots");
        System.out.println("- create permit # HANDICAPPED|RESERVED|GENERAL");
        System.out.println("\tcreate a new permit of a certain type");
        System.out.println("- display lot");
        System.out.println("\tprint out the lot");
        System.out.println("- display vehicles");
        System.out.println("\tprint out all vehicles created");
        System.out.println("- get spot #");
        System.out.println("\tget the vehicle in spot (if one exists)");
        System.out.println("- help");
        System.out.println("\tthis help message");
        System.out.println("- issue permit # vehicle #");
        System.out.println("\tissue a previously created permit by id to a vehicle by plate");
        System.out.println("- make vehicle #");
        System.out.println("\tcreate a new vehicle by plate");
        System.out.println("- park vehicle # spot #");
        System.out.println("\tpark a vehicle by plate in a spot by id");
        System.out.println("- patrol lot");
        System.out.println("\thave officer patrol lot and issue tickets");
        System.out.println("- remove vehicle #");
        System.out.println("\tremove parked vehicle from lot by plate");
        System.out.println("- quit");
        System.out.println("\tend the simulation");
    }

    /**
     * Issue a permit to a vehicle.
     *
     * @param id permit number
     * @param plate plate number of vehicle
     */
    private void issuePermit(int id, int plate) {
        Vehicle vehicle = findVehicle(plate);
        Permit permit = findPermit(id);
        if (vehicle == null) {
            System.out.println("Vehicle with plate " + plate + " could not be found!");
        } else if (permit == null) {
            System.out.println("Permit with id " + id + " could not be found!");
        } else {
            vehicle.setPermit(permit);
            System.out.println(vehicle);
        }
    }

    /**
     * Create a new vehicle with a plate number.
     *
     * @param plate the plate number of the new vehicle
     */
    private void makeVehicle(int plate) {
        if (findVehicle(plate) != null) {
            System.out.println("A vehicle with plate " + plate + " already exists!");
        } else {
            Vehicle vehicle = new Vehicle(plate);
            this.vehicles.add(vehicle);
            System.out.println(vehicle);
        }
    }

    /**
     * Park a vehicle in a spot.
     *
     * @param plate the plate number of the vehicle
     * @param spot the lot spot to park the vehicle
     */
    private void parkVehicle(int plate, int spot) {
        if (this.parkingLot == null) {
            System.out.println("The lot has not been created yet!");
        } else if (!this.parkingLot.isSpotValid(spot)) {
            System.out.println("Parking spot is out of range!");
        } else {
            Vehicle vehicle = findVehicle(plate);
            if (vehicle == null) {
                System.out.println("Vehicle with plate " + plate + " not found!");
            } else if (vehicle.isParked()) {
                System.out.println("Vehicle with plate " + plate + " is already parked!");
            } else {
                boolean result = this.parkingLot.parkVehicle(vehicle, spot);
                if (!result) {
                    System.out.println("Unable to park vehicle " + vehicle.getPlate() + " in spot " + spot);
                } else {
                    // if the vehicle was parked in the spot, check to see if it is parked illegaly or not
                    Fine fine = ParkingOfficer.getFineVehicleSpot(vehicle, this.parkingLot.getSpot(spot));
                    if (fine == Fine.NO_FINE) {
                        System.out.print("Vehicle " + vehicle.getPlate() + " is parked legally. ");
                    } else {
                        System.out.print("Vehicle " + vehicle.getPlate() + " is parked illegaly! ");
                    }
                    // regardless print out information about the vehicle and the spot it is parked in
                    System.out.println("Vehicle permit is " +
                            (vehicle.getPermit() == null ? "null" : vehicle.getPermit().getType())
                            + " and parking spot is " +
                            this.parkingLot.getSpot(spot).getType());
                }
            }
        }
    }

    /**
     * Have the officer patrol the lot and issue tickets.
     */
    private void patrolLot() {
        if (this.parkingLot == null) {
            System.out.println("Can't patrol lot, it has not been created yet!");
        } else {
            this.officer.patrolLot();
        }
    }

    /**
     * Remove a vehicle from a spot.
     *
     * @param plate the plate number of the vehicle
     */
    private void removeParkedVehicle(int plate) {
        if (this.parkingLot == null) {
            System.out.println("The lot has not been created yet!");
        } else {
            Vehicle vehicle = findVehicle(plate);
            if (vehicle == null) {
                System.out.println("Vehicle with plate " + plate + " not found!");
            } else {
                int spot = this.parkingLot.removeVehicle(vehicle);
                if (spot != ParkingLot.ILLEGAL_SPOT) {
                    System.out.println("Vehicle with plate " + plate +
                            " was removed from spot " + spot);
                } else {
                    System.out.println("Vehicle with plate " + plate +
                            " was not parked in lot!");
                }
                System.out.println(vehicle);
            }
        }
    }

    /**
     * The main simulation loop.
     *
     * @param in the input (standard in)
     * @param stdin is the scanner tied to standard input or not?
     */
    private void mainLoop(Scanner in, boolean stdin) {
        if (stdin) {
            System.out.println("Type 'help' for the list of commands.");
        }
        System.out.print("> ");
        // continue looping until there is no more input
        while (in.hasNext()) {
            // read the next command and then call the appropriate method to process it
            String line = in.nextLine();
            if (!stdin) {
                System.out.println(line);
            }
            String fields[] = line.split("\\s+");

            try {
                if (fields[0].equals(Command.CREATE.name().toLowerCase())) {
                    if (fields[1].equals(Command.LOT.name().toLowerCase())) {
                        createLot(Integer.parseInt(fields[2]),
                                Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
                    } else if (fields[1].equals(Command.PERMIT.name().toLowerCase())) {
                        createPermit(Integer.parseInt(fields[2]), fields[3]);
                    } else {
                        System.out.println("Unknown item to create, " + fields[1] + "!");
                    }
                } else if (fields[0].equals(Command.DISPLAY.name().toLowerCase())) {
                    display(fields[1]);
                } else if (fields[0].equals(Command.GET.name().toLowerCase())) {
                    getParkedVehicle(Integer.parseInt(fields[2]));
                } else if (fields[0].equals(Command.HELP.name().toLowerCase())) {
                    helpMessage();
                } else if (fields[0].equals(Command.ISSUE.name().toLowerCase())) {
                    issuePermit(Integer.parseInt(fields[2]), Integer.parseInt(fields[4]));
                } else if (fields[0].equals(Command.MAKE.name().toLowerCase())) {
                    makeVehicle(Integer.parseInt(fields[2]));
                } else if (fields[0].equals(Command.PARK.name().toLowerCase())) {
                    parkVehicle(Integer.parseInt(fields[2]), Integer.parseInt(fields[4]));
                } else if (fields[0].equals(Command.PATROL.name().toLowerCase())) {
                    patrolLot();
                } else if (fields[0].equals(Command.REMOVE.name().toLowerCase())) {
                    removeParkedVehicle(Integer.parseInt(fields[2]));
                } else if (fields[0].equals(Command.QUIT.name().toLowerCase())) {
                    // exit the main loop
                    break;
                } else {
                    System.out.println("Unrecognized command: " + fields[0]);
                }
            } catch (ArrayIndexOutOfBoundsException aiobe) {
                System.out.println("Not enough arguments!");
            } catch (NumberFormatException nfe) {
                System.out.println("Expected a number!");
            } catch (ParkingException pe) {
                System.out.println("Error creating parking lot: " + pe.getMessage());
            }
            System.out.print("> ");
        }
    }

    /**
     * The main method that expects the commands to come from either standard
     * input (no arguments) or a file (one argument which is the name of the
     * file).
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("Usage: java RITParking input");
        } else {
            Scanner in = null;
            try {
                boolean stdin;
                // if no arguments, tie the scanner to standard input
                if (args.length == 0) {
                    in = new Scanner(System.in);
                    stdin = true;
                } else {
                    // otherwise tie scanner to a file using the first command
                    // line argument as the filename
                    in = new Scanner(new File(args[0]));
                    stdin = false;
                }
                // initialize the simulation
                RITParking parking = new RITParking();
                // pass control to the main loop which processes the commands
                parking.mainLoop(in, stdin);
            } catch (IOException ioe) {
                System.err.println(ioe);
            } finally {
                // in all cases close the scanner if it was opened
                if (in != null) {
                    in.close();
                }
            }
        }
    }
}
