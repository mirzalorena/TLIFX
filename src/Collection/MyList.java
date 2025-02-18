package Collection;

import java.util.ArrayList;

public class MyList<T> implements InterfaceMyList<T> {

    private ArrayList<T> list;

    public MyList()
    {
        list= new ArrayList<>();
    }

    @Override
    public int size()
    {
        return list.size();
    }

    @Override
    public boolean add(T e)
    {
        return list.add(e);
    }

    @Override
    public T get(int index)
    {
        return list.get(index);
    }

    @Override
    public String toString()
    {
        return list.toString();
    }

}
