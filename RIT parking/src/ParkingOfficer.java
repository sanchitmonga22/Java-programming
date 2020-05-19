/**
 * author:Sanchit Monga
 * language:JAVA
 */
import java.util.ArrayList;
public class ParkingOfficer {
    private ParkingLot lot;
    private static int PAUSE_TIME=1000;
    private ArrayList<Ticket> tickets;
    /**
     * constuctor
     */
    public ParkingOfficer(){
        this.lot=null;
        this.tickets=new ArrayList<Ticket>();
    }
    /**
     *
     * @param vehicle Vehicle to be  checked
     * @param spot The spot that has to be checked
     * @return Whether or not the vehicle was parked in the right place and returs the fine for the vehicle if there is any
     */

    public static Fine getFineVehicleSpot(Vehicle vehicle, ParkingSpot spot){
        Permit.Type p2=spot.getType();// permit required for the spot
        if(p2==Permit.Type.HANDICAPPED &&(vehicle.hasPermit()==false || vehicle.getPermit().getType()!=Permit.Type.HANDICAPPED)){
            return Fine.PARKING_HANDICAPPED;
        }
        else if(p2==Permit.Type.RESERVED &&(vehicle.hasPermit()==false || vehicle.getPermit().getType()!= Permit.Type.RESERVED || vehicle.getPermit().getType()!=Permit.Type.HANDICAPPED)){
            return Fine.PARKING_RESERVED;
        }
        else if(p2==Permit.Type.GENERAL && vehicle.hasPermit()==false){
            return Fine.NO_PERMIT ;
        }
        else if(p2==vehicle.getPermit().getType()){
            return Fine.NO_FINE;
        }
        return Fine.NO_FINE;
    }
    /**
     *
     * @param lot Initializing the parking lot that has the spots
     */
    public void setParkingLot(ParkingLot lot)
    {
        this.lot=lot;
    }

    /**
     *
     * @return returns all the tickets issued by the officer
     */
    public ArrayList<Ticket> getTickets(){
        return this.tickets;
    }
    /**
     *
     * @param vehicle Issuing the tickets for the vehicle
     * @param spot The spot that has to be checked
     * @param fine the fine that will be charged to a vehicle if it is parked illegaly
     */
    public void issueTicket(Vehicle vehicle, int spot, Fine fine){
        ParkingSpot p1=this.lot.getSpot(spot);
        if(getFineVehicleSpot(vehicle, p1)!=Fine.NO_FINE){
            Ticket t1=new Ticket(vehicle.getPlate(),fine);
            vehicle.giveTicket(t1);
            System.out.println("Issuing ticket to: {"+vehicle+"} in spot "+spot+" for "+fine+"");
            System.out.println("");
            System.out.println("Where {"+vehicle+"} is the Vehicle, "+spot+" is the spot number and "+fine+" is the Fine type.");
        }
    }
    /**
     * Patrol lot function invokes other function and checks whether the vehicle was charged with any fine or not
     */
    public void patrolLot(){
        for (int i=0; i<this.lot.getCapacity();i++){
            ParkingSpot p1=this.lot.getSpot(i);
            if(p1.getVehicle()!=null && getFineVehicleSpot(p1.getVehicle(), p1)!=Fine.NO_FINE){
                issueTicket(p1.getVehicle(),i,getFineVehicleSpot(p1.getVehicle(),p1));
                RITParking.pause(PAUSE_TIME);
            }
        }
    }
    /**
     *
     * @param name Name of the parking officer
     * @param pOfficer Current parking officer
     * @param lot current parking lot
     * @param vehicle vehicle that is parked
     * @param spot The spot that has to be checked to be parked
     * This function is testing the following functions:
     *             1. get Fine vehicles
     *             2. get Tickets function
     */
    public static void verifyOfficer(String name,ParkingOfficer pOfficer,ParkingLot lot, Vehicle vehicle, int spot)
    {
        System.out.println("Officer "+name+" patrolled the lot \n"+ lot +"\nand found that the vehicle was parked in the "+lot.getSpot(spot).getType()+" lot and the vehicle's actual permit type was "+vehicle.getPermit().getType());
        System.out.println("The vehicle was charged a fine: "+getFineVehicleSpot(vehicle,lot.getSpot(spot)));
    }
    public static void main(String[] args){
        ParkingOfficer po= new ParkingOfficer();
        ParkingLot l1= new ParkingLot(4,26,70);
        po.setParkingLot(l1);
        System.out.println(l1);// printing empty lot with no parked vehicle
        Vehicle v1= new Vehicle(123);
        Permit p1=new Permit(5,Permit.Type.RESERVED);
        v1.setPermit(p1);
        l1.parkVehicle(v1,2);
        verifyOfficer("po",po,l1,v1,2);
        Vehicle v2= new Vehicle(1234);
        Permit p2=new Permit(7,Permit.Type.HANDICAPPED);
        v2.setPermit(p2);
        l1.parkVehicle(v2,3);
        System.out.println();
        System.out.println("Lot After parking another vehicle");
        System.out.println(l1);
        po.patrolLot();

    }
}
