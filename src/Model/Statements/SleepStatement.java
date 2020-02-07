package Model.Statements;

import Collection.InterfaceMyDictionary;
import Collection.InterfaceMyStack;
import Model.DataStructures.Type;
import Model.MyException;
import Model.ProgramState;

public class SleepStatement implements IStatement {

    private Integer number;

    public SleepStatement(Integer nr1)
    {
        this.number=nr1;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        if(number!=0)
        {
            InterfaceMyStack<IStatement> executionStack=state.getExecutionStack();
            executionStack.push(new SleepStatement(number-1));

        }
        return null;

    }

    @Override
    public String toString()
    {
        return "Sleep("+number.toString()+")";
    }

    @Override
    public IStatement deepCopy()
    {
        return new SleepStatement(number);
    }

    @Override
    public InterfaceMyDictionary<String, Type> typecheck(InterfaceMyDictionary<String,Type> typeEnv) throws MyException
    {
        return null;

    }


}
