package Collection;
import Model.DataStructures.Value;


import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfaceMyDictionary<K,V> {

    V get(K key);
    void put(K key, V value);
    String toString();

    boolean containsKey(K name);
    void remove(Value id);
    List<V> values();

    InterfaceMyDictionary<K,V> deepCopy();

    Set<K> keySet();

    Set<K> getKey();
    V lookup(K key);

    Iterable<Map.Entry<K,V>> getAll();



}
