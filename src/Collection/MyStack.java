package Collection;


import java.util.List;
import java.util.Stack;

public class MyStack<T> implements InterfaceMyStack<T>{
    private Stack<T> stack;

    public MyStack(){
        stack=new Stack<>();
    }

    @Override
    public T pop()
    {
        return stack.pop();
    }

    @Override
    public void push(T v)
    {
        stack.push(v);
    }

    @Override
    public boolean isEmpty()
    {
        return stack.empty();
    }

    @Override
    public List<T> getAll()
    {
        return stack;
    }


    @Override
    public String toString()
    {
        StringBuilder result=new StringBuilder();
        int size = this.stack.size()-1;

        for(int i=0;i<=size;i++)
        {
            result.append(this.stack.get(i).toString()).append("\n");
        }

        return result.toString();
    }



}
