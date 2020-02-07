package Model.Statements;

import Collection.InterfaceLock;
import Collection.InterfaceMyDictionary;
import Collection.MyDictionary;
import Model.DataStructures.*;
import Model.MyException;
import Model.ProgramState;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newLockStatement implements IStatement{

    private StringValue var;
    private static Lock lock = new ReentrantLock();

    public newLockStatement(StringValue var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        InterfaceLock lockTable = state.getLockTable();
        InterfaceMyDictionary<String, Value> symbolTable = state.getSymbolTable();

        Integer location = state.getLockAdress();

        lockTable.update(location, -1);
        symbolTable.put(var.toString(), new IntValue(location));

        state.setSymbolTable(symbolTable);
        state.setLockTable(lockTable);
        lock.unlock();
        return null;
    }

    @Override
    public String toString() {
        return "newLock( " + var+ ")";
    }

    @Override
    public IStatement deepCopy()
    {
        return new newLockStatement(var);
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
