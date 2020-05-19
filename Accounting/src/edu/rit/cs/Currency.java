package edu.rit.cs;

import java.util.regex.Pattern;
import java.util.Scanner;

/**
 * A handy-dandy abstraction and implementation for dollars and cents.
 * <em>Note that Currency objects are immutable. Therefore to
 * &quot;update&quot; them you need to assign them:</em><br>
 * <pre>curr = curr.op(...)</pre>
 *
 * @author James Heliotis
 */
public class Currency implements Comparable< Currency > {

    public static class Incompatible extends RuntimeException {};

    public static final long CENTS_PER_CURR_UNIT = 100;

    public static final char DEFAULT_CURR_SYM = '$';

    private final static Pattern CURR_DELIMITERS =
            Pattern.compile( "[ \\t\\n\\.]" );

    public final char currencySymbol;

    private long cents;

    /**
     * Create the amount 0.00
     * @param currencySymbol the symbol used for a particular currency
     */
    public Currency( char currencySymbol ) {
        this( 0, 0, currencySymbol );
    }

    /**
     * Create a Currency object with the given values.
     *
     * @param dollars the number of units of currency
     * @param cents   the number of cents
     * @param currencySymbol the symbol used for a particular currency
     */
    public Currency( long dollars, long cents, char currencySymbol ) {
        this.cents = cents + dollars * CENTS_PER_CURR_UNIT;
        this.currencySymbol = currencySymbol;
    }

    /**
     * Generate a Currency object from an input stream.
     * The format expected is "DDD.CC", any number of
     * digits.
     *
     * @param in the input stream
     * @param currencySymbol the symbol used for a particular currency
     */
    public Currency( Scanner in, char currencySymbol ) {
        Pattern prevDelimiter = in.delimiter();
        in.useDelimiter( CURR_DELIMITERS );
        this.cents = in.nextLong() * CENTS_PER_CURR_UNIT;
        in.findInLine( "\\." );
        this.cents += in.nextLong();
        in.useDelimiter( prevDelimiter );
        this.currencySymbol = currencySymbol;
    }

    /**
     * Create the amount 0.00 in the default currency.
     * @see #DEFAULT_CURR_SYM
     */
    public Currency() {
        this( 0, 0, DEFAULT_CURR_SYM );
    }

    /**
     * Create a Currency object with the given values in the default currency.
     * @see #DEFAULT_CURR_SYM
     *
     * @param dollars the number of units of currency
     * @param cents   the number of cents
     */
    public Currency( long dollars, long cents ) {
        this( dollars, cents, DEFAULT_CURR_SYM );
    }

    /**
     * Generate a Currency object from an input stream in the default currency.
     * The format expected is "DDD.CC", any number of
     * digits.
     * @see #DEFAULT_CURR_SYM
     *
     * @param in the input stream
     */
    public Currency( Scanner in ) {
        this( in, DEFAULT_CURR_SYM );
    }

    /**
     * Construct a copy of the a Currency object.
     *
     * @param other the original Currency object
     */
    public Currency( Currency other ) {
        this( 0, other.cents );
    }

    /**
     * Create the sum of two Currency objects.
     * This method does not change the state of the target object.
     *
     * @param other the second operand
     * @return this currency + other
     * @throws Incompatible if
     *           the target and argument currency types don't match
     */
    public Currency add( Currency other ) {
        if ( this.currencySymbol != other.currencySymbol ) {
            throw new Incompatible();
        }
        return new Currency( 0, cents + other.cents );
    }

    /**
     * Create the difference of two Currency objects.
     * This method does not change the state of the target object.
     *
     * @param other the second operand
     * @return this currency - other
     * @throws Incompatible if
     *           the target and argument currency types don't match
     */
    public Currency subtract( Currency other ) {
        if ( this.currencySymbol != other.currencySymbol ) {
            throw new Incompatible();
        }
        return new Currency( 0, cents - other.cents );
    }

    /**
     * Create a new Currency object which is the current
     * one multiplied by an integer scalar value.
     * This method does not change the state of the target object.
     *
     * @param scalar the value by which to multiply this Currency value
     * @return this currency * scalar
     */
    public Currency multiply( int scalar ) {
        return new Currency( 0, cents * scalar );
    }

    /**
     * Create a new Currency object which is the current
     * one multiplied by a floating point scalar value.
     * Round-off errors may occur.
     * This method does not change the state of the target object.
     *
     * @param scalar the value by which to multiply this Currency value
     * @return this currency * scalar
     */
    public Currency multiply( double scalar ) {
        return new Currency( 0, Math.round( cents * scalar ) );
    }

    /**
     * Create a string representation of the Currency value.
     * {@inheritDoc}
     * This method does not change the state of the target object.
     *
     * @return "$DDD.CC" or "($DDD.CC)" for a negative value
     */
    @Override
    public String toString() {
        long neg = ( cents < 0 ) ? -1 : 1;
        long pennies = neg * cents;
        long dollars = pennies / CENTS_PER_CURR_UNIT;
        pennies = pennies % CENTS_PER_CURR_UNIT;
        String cstring = "" + this.currencySymbol + dollars + '.';
        cstring += ( pennies < 10 ) ? ( "0" + pennies ) : pennies;

        if ( neg == -1 ) {
            return '(' + cstring + ')';
        }
        else {
            return cstring;
        }
    }

    /**
     * A handy constant representing $0.00
     */
    public final static Currency ZERO = new Currency();

    /**
     * This method does not change the state of the target object.
     * @return true iff the target object represents a negative value
     */
    public boolean negative() {
        return compareTo( ZERO ) < 0;
    }

    /**
     * {@inheritDoc}
     * This method does not change the state of the target object.
     *
     * @param other Currency object to compare with this one
     * @return &lt;0 if less; 0 if equal; &gt;0 if greater
     * @throws Incompatible if
     *           the target and argument currency types don't match
     */
    public int compareTo( Currency other ) {
        if ( this.currencySymbol != other.currencySymbol ) {
            throw new Incompatible();
        }
        long result = cents - other.cents;
        if ( result < 0 ) return -1;
        else if ( result > 0 ) return 1;
        else return 0;
    }

    /**
     * Test the Currency class.
     */
    private static void test() {
        try ( Scanner stdin = new Scanner( System.in ) ) {
            System.out.print(
                    "Please enter a dollar&cents value (no '$' sign): " );
            System.out.flush();
            Currency c1 = new Currency( stdin, DEFAULT_CURR_SYM );
            System.out.print( "Please enter another dollar&cents value: " );
            System.out.flush();
            Currency c2 = new Currency( stdin );
            Currency c3 = c1.add( c2 );
            Currency c4 = c1.subtract( c2 );
            double factor = 2.5;
            Currency c5 = c1.multiply( factor );
            int iFactor = -3;
            Currency c6 = c1.multiply( iFactor );
            System.out.println( " First value = " + c1 );
            System.out.println( "Second value = " + c2 );
            System.out.println( "         Sum = " + c3 );
            System.out.println( "  Difference = " + c4 );
            System.out.println( "First value * " + factor + " = " + c5 );
            System.out.println( "First value * " + iFactor + " = " + c6 );
        }
    }

    /**
     * Invoke the test program.
     *
     * @param args unused
     */
    public static void main( String[] args ) {
        if ( args.length != 0 ) {
            System.err.println( "No command line arguments, please." );
            System.exit( 1 );
        }
        test();
    }
}
