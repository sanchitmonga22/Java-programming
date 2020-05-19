/**
 * author:Sanchit Monga
 * language-JAVA
 */

import java.util.ArrayList;

public class ParkingLot {
    private int capacity;
    private int generalSpots;
    private int handicappedSpots;
    public static int ILLEGAL_SPOT=-1;
    private ArrayList<ParkingSpot> lot;
    private int parkedVehicles;
    private int reservedSpots;
    private static int SPOTS_PER_LINE=10;
    /**
     * Constructor
     * @param handicappedSpots number of handicapped spots in the lot
     * @param reservedSpots number of reserved spots in the lot
     * @param generalSpots number of general spots in the lot
     */
    public ParkingLot(int handicappedSpots, int reservedSpots, int generalSpots){
        this.handicappedSpots=handicappedSpots;
        this.reservedSpots=reservedSpots;
        this.generalSpots=generalSpots;
        this.lot= new ArrayList<ParkingSpot>();
        this.parkedVehicles=0;
        this.capacity=0;
        initializeSpots();
    }
    /**
     *
     * @return The caoacity of the lot
     */
    public int getCapacity(){
        this.capacity=generalSpots+handicappedSpots+reservedSpots;
        return this.capacity;
    }
    /**
     *
     * @return number of the parked vehicles
     */
    public int getNumberedParkedVehicles() {
        return this.parkedVehicles;
    }
    /**
     * Intitializing the spots in the given lot
     */
    public void initializeSpots() {
        int h=this.handicappedSpots;
        int r=this.reservedSpots;
        for (int i = 0; i <=getCapacity(); i++) {
            if(h>=0){
                ParkingSpot p1=new ParkingSpot(i,Permit.Type.HANDICAPPED);
                this.lot.add(p1);
                h--;
            }
            else if(r>=0){
                ParkingSpot p1=new ParkingSpot(i,Permit.Type.RESERVED);
                this.lot.add(p1);
                r--;
            }
            else{
                ParkingSpot p1=new ParkingSpot(i,Permit.Type.GENERAL);
                this.lot.add(p1);
            }
        }
    }
    /**
     * @param spot Spot to be checked
     * @return Checks whether the spot is valid or not
     */
    public boolean isSpotValid(int spot) {
        return(spot>=0 && spot<this.capacity);
    }
    /**
     * @param spot Spot to be returned
     * @return Returning the particluar spot in the lot
     */
    public ParkingSpot getSpot(int spot){
        ParkingSpot p1 = this.lot.get(spot);
        return p1;
    }
    /**
     * @param spot Spot to be checked
     * @return whether the spot is vacant or not
     */
    public boolean isSpotVacant(int spot){
        ParkingSpot p2= getSpot(spot);
        Vehicle v1= p2.getVehicle();
        if(v1==null){
            return true;
        }
        return false;
    }
    /**
     * @param vehicle Vehicle to be parked
     * @param spot SPot at which it is to be parked
     * @return Whether or not the vehicle was parked successfully
     */
    public boolean parkVehicle(Vehicle vehicle, int spot){
        ParkingSpot p2= this.lot.get(spot);
        Vehicle v1= p2.getVehicle();
        if(v1==null){
            p2.occupySpot(vehicle);
            return true;
        }
        return false;
    }
    /**
     * @param vehicle  Vehicle to be removed
     * @return the number of the spot from which the vehicle was removed
     */
    public int removeVehicle(Vehicle vehicle){
        ParkingSpot p1;
        for(int w=0; w <this.lot.size(); w++){
            p1=this.lot.get(w);
            Vehicle v1=p1.getVehicle();
            if(v1!=null && v1.equals(vehicle)) {
                p1.vacateSpot();
                return p1.getSpot();
            }
        }
        return ILLEGAL_SPOT;
    }
    /**
     * @return the string form of the parking lot
     */
    @Override
    public String toString(){
        int a=0;
        int vacant=0;
        String k="";
        String b="0";// for printing the output in the required format
        for(int i=0;i<SPOTS_PER_LINE;i++){// error out of bounds
            for(int j=0;j<SPOTS_PER_LINE;j++){
                ParkingSpot p1= this.lot.get(a);
                k=k+b+p1+" ";
                if(isSpotVacant(a)){
                    vacant++;
                }
                a++;
            }
            b="";
            k=k+"\n";
        }
        k=k+"Vacant Spots: "+vacant;
        return k;
    }
    /**
     * Checks the above function whether or not they are working correctly
     */
    public static void verifyLot(String lotName, ParkingLot l1, int spot, Vehicle vehicle, ParkingSpot p, int capacity, int parked){
        System.out.println("Lot "+lotName+" has the "+vehicle+" parked at the spot: "+p+" "+ (l1.getSpot(spot).equals(p) && vehicle.equals(p.getVehicle()) ?"OK":"FAIL"));
        System.out.println("Lot "+lotName+" matches the capacity: "+ ((l1.getCapacity()==capacity) ? "OK":"FAIL"));
        System.out.println("Lot "+lotName+" matches the number of parked vehicles: "+((l1.getNumberedParkedVehicles()==parked)?"OK":"FAIL"));
        System.out.println("Lot" + lotName +"has the valid spot: "+ (l1.isSpotValid(spot)?"OK":"FAIL"));
        System.out.println("Lot "+lotName+" the spot is vacant: "+(l1.isSpotVacant(spot)?"OK":"FAIL"));
        System.out.println("Lot "+lotName+" the vehicle is parked: "+(vehicle.isParked()?"OK":"FAIL"));
    }
    public static void main(String[] args){
        ParkingLot l1=new ParkingLot(45,30,25);
        System.out.println(l1.getCapacity());
        System.out.println(l1.getNumberedParkedVehicles());
        l1.initializeSpots();
        System.out.println(l1);
        Vehicle v1=new Vehicle(1234);
        Vehicle v2=new Vehicle(1253);
        l1.parkVehicle(v1,15);
        l1.parkVehicle(v2,17);
        System.out.println(l1);
        l1.removeVehicle(v1);
        l1.removeVehicle(v2);
        System.out.println(l1);
        verifyLot("l1",l1,15,v1,l1.getSpot(15),100,1);
    }
}