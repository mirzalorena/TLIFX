package Model.Statements;

import Collection.InterfaceHeap;
import Collection.InterfaceMyDictionary;
import Collection.InterfaceMyStack;
import Model.DataStructures.*;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;

public class WhileStatement implements IStatement {

    private Expression expression;
    private IStatement statement;

    public WhileStatement(Expression expression, IStatement statement){
        this.expression = expression;
        this.statement = statement;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value expresResult;
        expresResult=expression.eval(state.getSymbolTable(),state.getHeapTable());
        if(!expresResult.getType().equals(new BoolType()))
        {
            throw new MyException("lala");
        }
        if(!((BoolValue)expresResult).getVal().equals(false))
        {
            state.getExecutionStack().push(this);
            statement.execute(state);
        }
        return null;

    }

    @Override
    public IStatement deepCopy(){
        return new WhileStatement(this.expression, this.statement);
    }

    @Override
    public String toString(){
        return "while(" + expression.toString() + ")execute(" + this.statement.toString() + ")";
    }

    @Override
    public InterfaceMyDictionary<String, Type> typecheck(InterfaceMyDictionary<String,Type> typeEnv) throws MyException
    {
        /*Type typexp= expression.typecheck(typeEnv);
        if(typexp.equals(new IntType()))
        {
            statement.typecheck(typeEnv);
            return typeEnv;
        }
        else throw new MyException("lala");*/

        return null;

    }



}
