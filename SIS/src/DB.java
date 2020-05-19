import java.util.Collection;
/**
 *Database inteface
 * author:Sanchit Monga
 */

public interface DB <K,V>{
    /**
     * @param value: value to be added in the database
     * @return: previously existing value
     */
    V addValue(V value);
    /**
     * @param key: get the value associated with the key
     * @return: value
     */
    V getValue(K key);
    /**
     * @param key: key to get the value
     * @return: whether the key exists or not
     */
    boolean hasKey(K key);
    /**
     *
     * @return: collection of all the values
     */
    Collection<V> getAllValues();
}
