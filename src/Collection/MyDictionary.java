package Collection;

import java.util.*;

import Model.DataStructures.Value;

public class MyDictionary<K,V> implements InterfaceMyDictionary<K,V> {

    private HashMap<K, V> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<>();
    }

    private MyDictionary(HashMap<K, V> map)
    {
        this.dictionary=map;
    }


    @Override
    public Set<K> getKey()
    {
        return dictionary.keySet();
    }

    @Override
    public V get(K key) {
        for(K k:this.dictionary.keySet())
        {
                if(k.toString().equals(key.toString()))
                    return dictionary.get(k);
        }
        return null;


    }


    public V lookup(K key)
    {
        return dictionary.get(key);
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

   @Override
   public String toString()
   {
        StringBuilder result=new StringBuilder();
        for(K key:dictionary.keySet())
        {
            result.append(key.toString()+"-->"+this.dictionary.get(key).toString()).append("\n");
        }
        return result.toString();
   }

    @Override
    public boolean containsKey(K name)
    {
        for(K key: this.dictionary.keySet())
        {
            if(key.toString().equals(name.toString()))
                return true;
        }
        return false;
    }

    @Override
   public void remove(Value id)
    {
        dictionary.remove(id);

    }

    @Override
    public List<V> values()

    {
        return new LinkedList<>(this.dictionary.values());
    }



    @Override
    public Set<K> keySet()
    {
        return dictionary.keySet();
    }

    @Override
    public InterfaceMyDictionary<K,V> deepCopy()
    {
        HashMap<K,V> clone=new HashMap<>();

        for(Map.Entry<K,V> element: dictionary.entrySet())
        {
            clone.put(element.getKey(),element.getValue());
        }
        return new MyDictionary<>(clone);


    }


    @Override
    public Iterable<Map.Entry<K,V>> getAll()
    {
        return dictionary.entrySet();

    }





}
