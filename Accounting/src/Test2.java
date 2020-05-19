/*
 * Test2.java
 */

import bank.AccountType;
import bank.InsufficientFunds;
import bank.SavingsAccount;
import edu.rit.cs.Currency;
import student.BankAccount;
import student.CDAccount;
import student.CheckingAccount;
import student.CreditCard;
/**
 * This class is a test driver for the BankAccount class hierarchy.
 * Here we actually do debit and credit transactions in addition to
 * setting up accounts and adding interest.
 *
 * @author James Heliotis
 */
public class Test2 {

    /**
     * Number of months to run simulation
     */
    private static int NUM_MONTHS = 6;

    /**
     * Running total of interest accumulated
     */
    private static Currency totalInterest;

    /**
     * Sum of balances at any given time. This static field is shared by
     * multiple static methods.
     */
    private static Currency totalBalances;

    /**
     * Create some accounts, do some transactions, and accrue interest.
     *
     * @param args not used
     */
    public static void main( String[] args ) {

        BankAccount[] accounts = {
                new CDAccount( new Currency( 11150, 0 ), "Andy Anderson" ),
                new CheckingAccount( new Currency( 64665, 75 ), "Sharon Smith",
                                     false
                ),
                new SavingsAccount( new Currency( 5000, 0 ), "Bill Bailey" ),
                new CreditCard( new Currency( 8000, 0 ), 0.12, "Moms Mabley" )
        };

        totalInterest = new Currency();
        totalBalances = new Currency();

        totalBalances = new Currency();
        System.out.println( "\nInitial Report\n" );
        for ( BankAccount account : accounts ) {
            account.printStatement();
            addBalanceFor( account );
        }
        System.out.println( "\nTotal of balances: " + totalBalances + "\n" );
        System.out.println( '\n' );

        for ( int month = 1; month <= NUM_MONTHS; month++ ) {
            if ( month == NUM_MONTHS - 2 ) {
                takeMoneyOut( new Currency( 1169, 74 ), accounts[ 0 ] );
                putMoneyIn( new Currency( 660, 0 ), accounts[ 2 ] );
                takeMoneyOut( new Currency( 250, 0 ), accounts[ 3 ] );
            }
            else if ( month == NUM_MONTHS - 1 ) {
                putMoneyIn( new Currency( 200, 0 ), accounts[ 3 ] );
            }
            else if ( month == NUM_MONTHS ) {
                for ( BankAccount account : accounts ) {
                    takeMoneyOut( new Currency( 20000, 0 ), account );
                }
            }
            newMonthFor( accounts, month );
            System.out
                    .println( "\nTotal of balances: " + totalBalances + "\n" );
            System.out.println( "\n" );
        }

        System.out.println(
                "Total interest paid/charged: " + totalInterest + "\n" );
    }

    /**
     * Do a deposit/payment and print a message stating what was done.
     * @param amount how much to add
     * @param account to which account to add the money
     */
    static void putMoneyIn( Currency amount, BankAccount account ) {
        account.credit( amount );
        System.out.println( amount + " put into " + account.getOwnerName() +
                            "'s account." );
    }

    /**
     * Attempt a withdrawal/charge and print a message stating what was done.
     * If InsufficientFunds is thrown, report failure to complete the
     * transaction.
     * @param amount how much to take out
     * @param account from which account to remove the money
     */
    static void takeMoneyOut( Currency amount, BankAccount account ) {
        try {
            account.debit( amount );
            System.out.println(
                    amount + " taken out of " + account.getOwnerName() +
                    "'s account." );
        }
        catch( InsufficientFunds isf ) {
            System.out.println( "Debit on " + account.getOwnerName() +
                                "'s account failed." );
        }
    }

    /**
     * Do end-of-month work on all accounts. This means that interest is
     * calculated and added. Print out the interest accrued and new
     * balances. Accumulate the total of the balances reported in the
     * static field totalBalances.
     *
     * @param accounts the account array
     * @param month the month number (used just for output)
     */
    private static void newMonthFor( BankAccount[] accounts, int month ) {
        totalBalances = Currency.ZERO;

        System.out.println( "Report, month #" + month + " \n" );
        for ( BankAccount account : accounts ) {
            account.endOfMonth();
            account.printStatement();
            totalInterest = totalInterest.add( account.getInterest() );
            addBalanceFor( account );
        }
    }

    /**
     * Add more money to the totalBalances field. The balances in both
     * debit accounts and credit accounts add positively.
     * @param account for which account to look up a balance
     */
    private static void addBalanceFor( BankAccount account ) {
        totalBalances =
                account.type == AccountType.DEBIT ?
                        totalBalances.add( account.getCurrentBalance() ) :
                        totalBalances.subtract( account.getCurrentBalance() );
    }

} // Test2

/******************* OUTPUT ***********************************************

Initial Report

CD  Andy Anderson  	Interest Accrued: $0.00	Current Balance: $11150.00
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
SA  Bill Bailey    	Interest Accrued: $0.00	Current Balance: $5000.00
CC  Moms Mabley    	Interest Accrued: $0.00	Current Balance: $0.00

Total of balances: $80815.75



Report, month #1

CD  Andy Anderson  	Interest Accrued: $5.08	Current Balance: $11155.08
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
SA  Bill Bailey    	Interest Accrued: $1.25	Current Balance: $5001.25
CC  Moms Mabley    	Interest Accrued: $0.00	Current Balance: $0.00

Total of balances: $80822.08



Report, month #2

CD  Andy Anderson  	Interest Accrued: $5.08	Current Balance: $11160.16
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
SA  Bill Bailey    	Interest Accrued: $1.25	Current Balance: $5002.50
CC  Moms Mabley    	Interest Accrued: $0.00	Current Balance: $0.00

Total of balances: $80828.41



Report, month #3

CD  Andy Anderson  	Interest Accrued: $5.08	Current Balance: $11165.24
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
SA  Bill Bailey    	Interest Accrued: $1.25	Current Balance: $5003.75
CC  Moms Mabley    	Interest Accrued: $0.00	Current Balance: $0.00

Total of balances: $80834.74



$1169.74 taken out of Andy Anderson's account.
$660.00 put into Bill Bailey's account.
$250.00 taken out of Moms Mabley's account.
Report, month #4

CD  Andy Anderson  	Interest Accrued: $4.50	Current Balance: $10000.00
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
SA  Bill Bailey    	Interest Accrued: $1.42	Current Balance: $5665.17
CC  Moms Mabley    	Interest Accrued: ($2.50)	Current Balance: ($252.50)

Total of balances: $80583.42



$200.00 put into Moms Mabley's account.
Report, month #5

CD  Andy Anderson  	Interest Accrued: $4.50	Current Balance: $10004.50
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $64665.75
SA  Bill Bailey    	Interest Accrued: $1.42	Current Balance: $5666.59
CC  Moms Mabley    	Interest Accrued: ($0.52)	Current Balance: ($53.02)

Total of balances: $80389.86



Debit on Andy Anderson's account failed.
$20000.00 taken out of Sharon Smith's account.
Debit on Bill Bailey's account failed.
Debit on Moms Mabley's account failed.
Report, month #6

CD  Andy Anderson  	Interest Accrued: $4.50	Current Balance: $10009.00
CN  Sharon Smith   	Interest Accrued: $0.00	Current Balance: $44665.75
SA  Bill Bailey    	Interest Accrued: $1.42	Current Balance: $5668.01
CC  Moms Mabley    	Interest Accrued: ($0.53)	Current Balance: ($53.55)

Total of balances: $60396.31



Total interest paid/charged: $33.20

*/
