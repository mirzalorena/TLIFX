package Collection;


import java.util.List;

public interface InterfaceMyStack<T> {
    T pop();
    void push(T v);
    boolean isEmpty();
    String toString();
    List<T> getAll();

}
