package Model.Statements.File;

import Collection.InterfaceMyDictionary;
import Model.DataStructures.StringType;
import Model.DataStructures.StringValue;
import Model.DataStructures.Type;
import Model.DataStructures.Value;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.Statements.IStatement;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseStatement implements IStatement {

    private Expression exp;

    public CloseStatement(Expression e)
    {
        this.exp=e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {

        Value returnVal=exp.eval(state.getSymbolTable(),state.getHeapTable());
        if(!returnVal.getType().equals(new StringType()))
            throw new MyException("Invalid type");
        if(!state.getFileTable().containsKey((StringValue)returnVal))
            throw new MyException("File  not defined");
        try
        {
            BufferedReader openedFile=state.getFileTable().get((StringValue)returnVal);
            openedFile.close();
        }
        catch(IOException e)
        {
            throw new MyException(e.getMessage());
        }
        state.getFileTable().remove((StringValue)returnVal);


        return state;
    }


    @Override
    public IStatement deepCopy()
    {
        return new CloseStatement(exp);
    }

    @Override
    public String toString()
    {
        return " closeFile( "+exp.toString()+" )";
    }

    @Override
    public InterfaceMyDictionary<String, Type> typecheck(InterfaceMyDictionary<String,Type> typeEnv) throws MyException
    {
        Type typexp = exp.typecheck(typeEnv);
        return typeEnv;

    }

}
