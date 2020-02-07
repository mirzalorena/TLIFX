
package Collection;

import Model.DataStructures.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HeapTable<I extends Number, V> implements InterfaceHeap<Integer, Value> {
    private Integer nextFreeMemory;
    private HashMap<Integer, Value> table;

    public HeapTable(){
        this.table = new HashMap<>();
        this.nextFreeMemory = 1;
        table.put(0, null);
    }

    @Override
    public boolean isDefined(Integer id) {
        return !table.containsKey(id);
    }

    @Override
    public void update(Integer id, Value value) {
        table.put(id, value);
    }

    @Override
    public void update(Value value) {
        table.put(nextFreeMemory, value);
        nextFreeMemory++;
    }


    @Override
    public Integer getCurrentFree() {
        return nextFreeMemory;
    }

    @Override
    public Set<Map.Entry<Integer, Value>> getEntrySet() {
        return this.table.entrySet();
    }

    @Override
    public Value getValue(Integer key) {
        return table.get(key);
    }

    @Override
    public void setContent(Map newContent) {
        this.table = (HashMap) newContent;
    }

    @Override
    public String toString(){


        StringBuilder toReturn = new StringBuilder();
        this.table.keySet().forEach(key -> {
            toReturn.append(key.toString()).append(" --> ");
            if (this.table.get(key) == null)
                toReturn.append("null").append("\n");
            else
                toReturn.append(this.table.get(key).toString()).append("\n");
        });
        return toReturn.toString();

    }

}