/*
 * Test2.java
 */

import bank.SavingsAccount;
import edu.rit.cs.Currency;
import student.BankAccount;
import student.CDAccount;
import student.CheckingAccount;
import student.CreditCard;

/**
 * Check for exceptional conditions.
 *
 * @author James Heliotis
 */
public class Test3 {

    /**
     * Create some accounts, do some transactions, and accrue interest.
     *
     * @param args not used
     */
    public static void main( String[] args ) {

        final Currency fifty = new Currency( 50, 0 );
        final Currency fhtf = new Currency( 410, 50 );
        final Currency hundred = new Currency( 100, 0 );

        for ( BankAccount acct : new BankAccount[]{
                new CheckingAccount( new Currency( 450, 0 ), "Checking", true ),
                new SavingsAccount( new Currency( 450, 0 ), "Savings" ),
                new CDAccount( new Currency( 450, 0 ), "CertOfDep" ),
                new CreditCard( new Currency( 450, 0 ), 1.2, "Credit" )
        } ) {
            System.out.println();
            System.out.println( acct + " -- " + acct.getCurrentBalance() );
            Test2.takeMoneyOut( fifty, acct );
            Test3.newMonthFor( acct );
            Test2.takeMoneyOut( fhtf, acct );
            Test3.newMonthFor( acct );
            Test2.putMoneyIn( hundred, acct );
            Test3.newMonthFor( acct );
            System.out.println();
        }
    }

    /**
     * Do end-of-month work on one account. This means that interest is
     * calculated and added. Print out the interest accrued and new
     * balance.
     *
     * @param account the account
     */
    private static void newMonthFor( BankAccount account ) {
        System.out.println( "Monthly report" );
        account.endOfMonth();
        account.printStatement();
        System.out.println();
    }

} // Test3

/******************* OUTPUT ***********************************************

Checking $450.00 Checking -- $450.00
$50.00 taken out of Checking's account.
Monthly report
CI  Checking       	Interest Accrued: $0.00	Current Balance: $400.00

Debit on Checking's account failed.
Monthly report
CI  Checking       	Interest Accrued: $0.00	Current Balance: $400.00

$100.00 put into Checking's account.
Monthly report
CI  Checking       	Interest Accrued: $0.00	Current Balance: $500.00



Savings $450.00 Savings -- $450.00
$50.00 taken out of Savings's account.
Monthly report
SA  Savings        	Interest Accrued: $0.10	Current Balance: $400.10

Debit on Savings's account failed.
Monthly report
SA  Savings        	Interest Accrued: $0.10	Current Balance: $400.20

$100.00 put into Savings's account.
Monthly report
SA  Savings        	Interest Accrued: $0.13	Current Balance: $500.33



CertOfDep $450.00 CD -- $450.00
$50.00 taken out of CertOfDep's account.
Monthly report
CD  CertOfDep      	Interest Accrued: $0.00	Current Balance: $400.00

Debit on CertOfDep's account failed.
Monthly report
CD  CertOfDep      	Interest Accrued: $0.00	Current Balance: $400.00

$100.00 put into CertOfDep's account.
Monthly report
CD  CertOfDep      	Interest Accrued: $0.00	Current Balance: $500.00



Credit $0.00 -- $0.00
$50.00 taken out of Credit's account.
Monthly report
CC  Credit         	Interest Accrued: ($5.00)	Current Balance: ($55.00)

Debit on Credit's account failed.
Monthly report
CC  Credit         	Interest Accrued: ($5.50)	Current Balance: ($60.50)

$100.00 put into Credit's account.
Monthly report
CC  Credit         	Interest Accrued: $3.95	Current Balance: $43.45


*/
