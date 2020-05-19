/**
 * Author: Sanchit Monga
 * This is a Checking account class that implements all the abstract methods
 */
package student;
import bank.AccountType;
import edu.rit.cs.Currency;


public class CheckingAccount extends BankAccount {
    public static final double BONUS_MONTHLY_RATE=8.33E-5;// defining the intitial state of the class
    public static final Currency PREMIUM_CHECKING_MINIMUM_BALANCE=new Currency(500,0,'$');
    private boolean bonus;
    public final String chkAcctType;
    /**
     * Constructor to initialize the CheckingAccount class
     * @param newMoney: Initial bank account balance
     * @param owner: The name of the owner of the account
     * @param bonus: The bonus that determines whether the account will get the interest or not
     */
    public CheckingAccount(Currency newMoney, String owner, boolean bonus){
        super(newMoney,owner, AccountType.DEBIT);
        this.bonus=bonus;
        if(this.bonus){
            chkAcctType="CI";
        }
        else{
            chkAcctType="CN";
        }
    }
    /**
     * Calculatest the interst at the end of the month and updates all the data
     */
    @Override
    public void endOfMonth() {
        if(getAccountType()=="CI" && getCurrentBalance().compareTo(PREMIUM_CHECKING_MINIMUM_BALANCE)>0){
            Currency intEarned= getCurrentBalance().multiply(BONUS_MONTHLY_RATE);
            setInterestAccrued(intEarned);
            addInterest(intEarned);
        }else {
            Currency intEarned = Currency.ZERO;
            setInterestAccrued(intEarned);
            addInterest(intEarned);
        }
    }
    /**
     *
     * @return: Returns the type of account
     */
    public String getAccountType(){
        return chkAcctType;
    }
    /**
     *
     * @return: String representation of the account
     */
    @Override
    public String toString(){
        return super.toString()+" Checking";
    }
}
