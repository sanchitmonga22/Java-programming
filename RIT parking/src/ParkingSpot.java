/**
 * author: Sanchit Monga
 * language-JAVA
 */
public class ParkingSpot {
    private static final String OCCUPIED_STR = "*";
    private int spot;
    private Permit.Type type;
    private Vehicle vehicle;

    /**
     * Constructor
     * @param spot the spot where the vehicle is parked
     * @param type the type of the spot
     */
    public ParkingSpot(int spot, Permit.Type type) {
        this.spot = spot;
        this.type = type;
        this.vehicle = null;
    }
    /**
     *
     * @return Parking spot
     */
    public int getSpot() {
        return this.spot;
    }
    /**
     *
     * @return The permit type that is required for the particular spot
     */
    public Permit.Type getType() {
        return this.type;
    }
    /**
     *
     * @return Vehicle that is parked at the particular spot
     */
    public Vehicle getVehicle(){
        return this.vehicle;
    }
    /**
     * @param vehicle The vehicle that has to occupy the spot in the Parking lot
     */
    public void occupySpot(Vehicle vehicle){
        vehicle.setParked(true);
        this.vehicle=vehicle;
    }

    /**
     * this is used to remove the vehicle from the given spot
     */
    public void vacateSpot(){
        this.vehicle.setParked(false);
        this.vehicle=null;
    }

    /**
     * used to print the Parking spot
     * @return the parking spot
     */
    @Override
    public String toString(){
        if(this.vehicle!=null){
            return (this.spot + ":" + OCCUPIED_STR);
        }
        else{
            if(this.type==Permit.Type.GENERAL) {
                return (this.spot + ":" + "G");
            }
            else if(this.type==Permit.Type.HANDICAPPED){
                return (this.spot + ":" + "H");
            }
            else{
                return (this.spot + ":" + "R");
            }
        }
    }
    /**
     *
     * @param other the Parkingspot object that has to be checked
     * @return Returns whether or not the parrking spot obejcts are similar or not
     */
    @Override
    public boolean equals(Object other){
        boolean result=false;
        if(other instanceof ParkingSpot){
            ParkingSpot p1=(ParkingSpot)other;
            result=this.spot==p1.getSpot() && this.type==p1.getType();
        }
        return result;
    }
    /**
     * Used to verify the given functions is Parking Lot
     */
    private static void verifySpot(String spotVar, ParkingSpot s, int spot, Permit.Type type, Vehicle vehicle){
        System.out.println(spotVar+" matches the given spot " + (s.getSpot()== spot ? "OK" :
                "FAIL, got: " + spot));
        System.out.println("The type of " +spotVar+" matches the given type " + (s.getType()== type ? "OK" :
                "FAIL, got: " + type));
        if(s.getVehicle()==null){
            System.out.println("The vehicle at spot "+spotVar+" matches the vehicle? " + ((vehicle==null) ? "OK":"FAIL"));
        }
        else{
            System.out.println("The vehicle is parked? "+ ((s.getVehicle().equals(vehicle)) && (s.vehicle.isParked()) ?"OK" : "FAIL, because there is another vehicle with number plate: ")+ s.vehicle.getPlate());
        }
        System.out.println("For the user to visually verify the spot");
        System.out.println(s.toString());
    }
    public static void main(String[]  args){
        ParkingSpot s1= new ParkingSpot(50, Permit.Type.GENERAL);
        ParkingSpot s2= new ParkingSpot(20, Permit.Type.GENERAL);
        verifySpot("s1",s1, 50, Permit.Type.GENERAL, null);
        Vehicle v1 = new Vehicle(1234);
        s2.occupySpot(v1);
        verifySpot("s2", s2, 20, Permit.Type.GENERAL, null);
        s2.vacateSpot();
        verifySpot("s2", s2, 20, Permit.Type.GENERAL, null);

    }

}
