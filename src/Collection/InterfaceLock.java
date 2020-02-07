package Collection;


import java.util.Map;

public interface InterfaceLock {
    boolean isDefined(Integer key);
    void update(Integer newT1, Integer newT2);
    Integer lookup(Integer key);
    String toString();
    LockTable deepCopy();
    Iterable<Map.Entry<Integer, Integer>> getAll();
}
