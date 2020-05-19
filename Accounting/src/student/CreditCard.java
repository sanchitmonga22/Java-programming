/**
 *Author: Sanchit Monga
 * This class is the implementation of the Credit card with its various abstract methods
 */
package student;
import bank.AccountType;
import edu.rit.cs.Currency;

public class CreditCard extends BankAccount {
    private Currency creditLimit;
    private double monthlyInterestRate;
    /**
     *
     * @param creditLimit  : Current credit card limit of the account
     * @param interestRate: The interest rate of the card
     * @param owner: The name of the owner of the car
     */
    public CreditCard(Currency creditLimit, double interestRate,String owner){
        super(creditLimit,owner, AccountType.CREDIT);
        this.creditLimit=creditLimit;
        this.monthlyInterestRate=interestRate/12;
    }
    /**
     *
     * @return Returning the current balance of the Credit card or the amount that the owner ows
     */
    @Override
    public Currency getCurrentBalance(){
        return this.creditLimit.subtract(super.getCurrentBalance());
    }
    /**
     *Calculates and updates the interest
     */
    public void endOfMonth(){
        Currency intEarned = this.getCurrentBalance().multiply(monthlyInterestRate);
        setInterestAccrued(intEarned);
        addInterest(intEarned);
    }
    /**
     *
     * @return : The type of the account
     */
    public String getAccountType(){
        return "CC";
    }

}
