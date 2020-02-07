package Model.Statements;

import Collection.InterfaceLock;
import Collection.InterfaceMyDictionary;
import Model.DataStructures.IntType;
import Model.DataStructures.StringValue;
import Model.DataStructures.Type;
import Model.DataStructures.Value;
import Model.MyException;
import Model.ProgramState;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class unlockStatement implements IStatement {

    private StringValue var;

    public unlockStatement(StringValue var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceMyDictionary<String, Value> symTbl = state.getSymbolTable();
       InterfaceLock lockTable = state.getLockTable();
        if (!symTbl.containsKey(var.toString())) throw new MyException("lala");
        Value foundIndex = symTbl.lookup(var.toString());
        if (lockTable.isDefined((Integer) foundIndex.getVal())) throw new MyException("lala");
        Integer lockTableVal = lockTable.lookup((Integer)foundIndex.getVal());
        if (lockTableVal == state.getId()) {
            Lock l = new ReentrantLock();
            l.lock();
            try {
                lockTable.update((Integer)foundIndex.getVal(), -1);
            } finally {
                l.unlock();
            }

        }
        return null;
    }

    @Override
    public String toString() {
        return "unlock( " + var + ")";
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
