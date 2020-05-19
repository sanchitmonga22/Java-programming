package bank;/*
 * SavingsAccount.java
 */

import edu.rit.cs.Currency;
import student.BankAccount;

/**
 * The SavingsAccount class is a subclass of the abstract
 * BankAccount.  Interest is calculated and credited to the
 * account monthly.
 *
 * @author Trudy Howles  tmh@cs.rit.edu
 */

public class SavingsAccount extends BankAccount {

    /**
     * The annual interest rate for a standard savings account is currently 0
     * .3%.
     */
    public static final double SAVINGS_MONTHLY_INTEREST_RATE =
            .003 / NUM_PERIODS_PER_YEAR;

    /**
     * A constructor for a SavingsAccount object. It accepts the amount
     * of money deposited when the account is created and the owner name.
     *
     * @param newMoney The amount of money deposited when the account
     *                 is opened.
     * @param owner    the owner of this account
     */
    public SavingsAccount( Currency newMoney, String owner ) {
        super( newMoney, owner, AccountType.DEBIT );
    }

    /**
     * Calculate the interest and update the balance for this account.
     * Savings accounts earn interest
     * on the entire current balance. Interest earned is calculated monthly,
     * and added to the current balance.
     * The value returned by {@link BankAccount#getInterest()} will be
     * the interest computed by this method.
     */
    @Override
    public void endOfMonth() {
        Currency intEarned = getCurrentBalance().multiply(
                               SAVINGS_MONTHLY_INTEREST_RATE );
        setInterestAccrued( intEarned );
        addInterest( intEarned );
    }

    /**
     * The account type designation for this account.
     *
     * @return "SA"
     */
    @Override
    public String getAccountType() {
        return "SA";
    }


    /**
     * A printable version of this account
     *
     * @return the owner name, current balance and the literal "Savings" to
     * identify this account
     */
    @Override
    public String toString() {
        return super.toString() + " Savings";
    }
} // SavingsAccount
