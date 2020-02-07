package Model.Statements.File;

import Collection.InterfaceMyDictionary;
import Model.DataStructures.*;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.Statements.IStatement;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {

    private Expression exp;
    private StringValue varName;

    public ReadFileStatement(Expression e, StringValue vn)
    {
        this.exp=e;
        this.varName=vn;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {

        if(state.getSymbolTable().containsKey((String)varName.getVal()) && state.getSymbolTable().get((String)varName.getVal()).getType().equals(new IntType()))
        {
            try
            {
                Value result=exp.eval(state.getSymbolTable(),state.getHeapTable());
                if(result instanceof StringValue)
                {
                    BufferedReader openedFile=state.getFileTable().get((StringValue)result);
                    String line=openedFile.readLine();
                    IntValue newVal;


                    if(line==null)
                    {
                        newVal=new IntValue(0);
                    }
                    else
                    {
                        newVal=new IntValue(Integer.parseInt(line));

                    }
                    state.getSymbolTable().put((String)varName.getVal(),newVal);

                }
                else
                {
                    throw new MyException("not string value");
                }
            }
            catch(IOException e)
            {
                throw new MyException(e.getMessage());
            }
        }

        return state;
    }

    @Override
    public IStatement deepCopy()
    {
        return new ReadFileStatement(exp,varName);
    }

    @Override
    public String toString()
    {
        return "readFile( "+exp.toString()+" , "+varName+" )";
    }

    @Override
    public InterfaceMyDictionary<String, Type> typecheck(InterfaceMyDictionary<String,Type> typeEnv) throws MyException
    {
        Type typevar = typeEnv.lookup(varName.toString());
        if (typevar.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("la");

    }



}
