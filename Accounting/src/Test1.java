/*
 * Test1.java
 */

import bank.SavingsAccount;
import student.CDAccount;
import student.CheckingAccount;
import edu.rit.cs.Currency;
import student.*;

import java.util.ArrayList;


/**
 * This class is an old test driver for the BankAccount class hierarchy.
 * Its purpose is to illustrate the polymorphism available in the
 * BankAccount class hierarchy.
 * Here we just test setting up accounts and adding interest.
 *
 * @author Trudy Howles  tmh@cs.rit.edu
 */

public class Test1 {

    /*
     *  Number of months to run simulation
     *
     */
    private static int NUM_MONTHS = 4;

    public static void main( String[] args ) {

        ArrayList< BankAccount > accounts = new ArrayList<>();

        accounts.add(
                new CDAccount( new Currency( 11150, 0 ), "Andy Anderson" ) );
        accounts.add(
                new CheckingAccount( new Currency( 64665, 75 ), "Sharon Smith",
                                     false
                ) );
        accounts.add(
                new CDAccount( new Currency( 10000, 0 ), "Phil Phillips" ) );
        accounts.add(
                new CheckingAccount( new Currency( 450, 0 ), "Carol Carroll",
                                     true
                ) );
        accounts.add(
                new SavingsAccount( new Currency( 5000, 0 ), "Bill Bailey" ) );
        accounts.add( new CheckingAccount( new Currency( 5500, 0 ), "Lois Lane",
                                           true
        ) );

        Currency totalInt = new Currency();
        Currency totalBalances = new Currency();

        for ( int i = 1; i <= NUM_MONTHS; i++ ) {
            totalBalances = new Currency();

            System.out.println( "Interest Report - month " + i + " \n" );
            for ( BankAccount account : accounts ) {
                account.endOfMonth();
                account.printStatement();
                totalInt = totalInt.add( account.getInterest() );
                totalBalances =
                        totalBalances.add( account.getCurrentBalance() );
            }
            System.out.println( "\n\n" );
        }


        System.out.println( "Total interest: " + totalInt + "\n" );
        System.out.println( "Total balances: " + totalBalances + "\n" );


    }

} // Test1

/******************* OUTPUT ***********************************************
Interest Report - month 1

CD  Andy Anderson  	Interest Accrued: $5.08	Current Balance: $11155.08
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
CD  Phil Phillips  	Interest Accrued: $4.50	Current Balance: $10004.50
CI  Carol Carroll  	Interest Accrued: $0.00	Current Balance: $450.00
SA  Bill Bailey    	Interest Accrued: $1.25	Current Balance: $5001.25
CI  Lois Lane      	Interest Accrued: $0.46	Current Balance: $5500.46



Interest Report - month 2

CD  Andy Anderson  	Interest Accrued: $5.08	Current Balance: $11160.16
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
CD  Phil Phillips  	Interest Accrued: $4.50	Current Balance: $10009.00
CI  Carol Carroll  	Interest Accrued: $0.00	Current Balance: $450.00
SA  Bill Bailey    	Interest Accrued: $1.25	Current Balance: $5002.50
CI  Lois Lane      	Interest Accrued: $0.46	Current Balance: $5500.92



Interest Report - month 3

CD  Andy Anderson  	Interest Accrued: $5.08	Current Balance: $11165.24
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
CD  Phil Phillips  	Interest Accrued: $4.50	Current Balance: $10013.50
CI  Carol Carroll  	Interest Accrued: $0.00	Current Balance: $450.00
SA  Bill Bailey    	Interest Accrued: $1.25	Current Balance: $5003.75
CI  Lois Lane      	Interest Accrued: $0.46	Current Balance: $5501.38



Interest Report - month 4

CD  Andy Anderson  	Interest Accrued: $5.08	Current Balance: $11170.32
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
CD  Phil Phillips  	Interest Accrued: $4.51	Current Balance: $10018.01
CI  Carol Carroll  	Interest Accrued: $0.00	Current Balance: $450.00
SA  Bill Bailey    	Interest Accrued: $1.25	Current Balance: $5005.00
CI  Lois Lane      	Interest Accrued: $0.46	Current Balance: $5501.84



Total interest: $45.17

Total balances: $96810.92

*/
