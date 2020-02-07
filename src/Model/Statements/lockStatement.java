package Model.Statements;

import Collection.InterfaceLock;
import Collection.InterfaceMyDictionary;
import Collection.InterfaceMyStack;
import Model.DataStructures.*;
import Model.MyException;
import Model.ProgramState;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class lockStatement implements IStatement {
    private StringValue var;


    public lockStatement(StringValue var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceMyDictionary<String, Value> symTbl = state.getSymbolTable();
        InterfaceLock lockTable = state.getLockTable();
        InterfaceMyStack<IStatement> stack = state.getExecutionStack();
        if (!symTbl.containsKey(var.toString())) throw new MyException("lala");
        Value foundIndex = symTbl.lookup(var.toString());
        if (lockTable.isDefined((Integer) foundIndex.getVal())) throw new MyException("lla");
        Integer lockTableVal = lockTable.lookup((Integer)foundIndex.getVal());
        if (lockTableVal == -1) {
            Lock l = new ReentrantLock();
            l.lock();
            try {
                lockTable.update((Integer)foundIndex.getVal(), state.getId());
            } finally {
                l.unlock();
            }

        } else
            stack.push(this);
        return null;
    }

    @Override
    public String toString() {
        return "lock( " + var + ")";
    }

    @Override
    public IStatement deepCopy()
    {
        return new lockStatement(var);
    }
    @Override
    public InterfaceMyDictionary<String, Type> typecheck(InterfaceMyDictionary<String,Type> typeEnv) throws MyException
    {
        if (!(typeEnv.lookup(var.toString()) instanceof IntType)){
            throw new MyException(var + "is not of Int type");
        }
        return null;

    }
}
