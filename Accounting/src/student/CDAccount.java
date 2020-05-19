/**
 * Author: Sanchit Monga
 * This is used to implement the Checking deposit account and its abstract features
 */
package student;
import bank.AccountType;
import edu.rit.cs.Currency;

public class CDAccount extends BankAccount{
    public static final Currency MINIMUM_BALANCE=new Currency(1000,0,'$');
    public static final double MONTHLY_INTEREST_RATE=5.0E-4;
    /**
     * Constructor to initialize a CD Account type
     * @param newMoney: Initial payment in the account
     * @param owner: Name of the owner of the bank account
     */
    public CDAccount(Currency newMoney, String owner){
        super(newMoney,owner, AccountType.DEBIT);
    }
    /**
     * This calculates the interest of the account and updates all other values
     */
    public void endOfMonth(){
        if(getCurrentBalance().compareTo(MINIMUM_BALANCE)>0) {
            Currency intEarned = getCurrentBalance().subtract(MINIMUM_BALANCE).multiply(MONTHLY_INTEREST_RATE);
            setInterestAccrued(intEarned);
            addInterest(intEarned);
        }else{
            Currency intEarned = Currency.ZERO;
            setInterestAccrued(intEarned);
            addInterest(intEarned);
        }
    }

    /**
     *
     * @return: Abbreviation for the acccunt
     */
    public String getAccountType(){
        return "CD";
    }

    /**
     *
     * @return: String representation of the account
     */
    public String toString(){
        return super.toString()+" CD";
    }
}
