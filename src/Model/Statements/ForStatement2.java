package Model.Statements;

import Collection.InterfaceMyDictionary;
import Model.DataStructures.IntType;
import Model.DataStructures.Type;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.VarExpression;
import Model.MyException;
import Model.ProgramState;

public class ForStatement2 implements IStatement {
    private Expression exp1;
    private Expression exp2;
    private Expression exp3;
    private IStatement statement;

    public ForStatement2(Expression newE1, Expression newE2, Expression newE3, IStatement stmt){
        this.exp1 = newE1;
        this.exp2 = newE2;
        this.exp3 = newE3;
        this.statement = stmt;
    }

    @Override
    public String toString(){
        return "For v = " + exp1.toString() + "; v < " + exp2.toString() + "; v = " + exp3.toString() + "\n " + statement.toString();
    }

    @Override
    public InterfaceMyDictionary<String, Type> typecheck(InterfaceMyDictionary<String,Type> typeEnv) throws MyException
    {
        Type typexp= exp1.typecheck(typeEnv);
        if(typexp.equals(new IntType()))
        {
            statement.typecheck(typeEnv);
            return typeEnv;
        }else
        {
            Type typexp1= exp2.typecheck(typeEnv);
            if(typexp1.equals(new IntType()))
            {
                statement.typecheck(typeEnv);
                return typeEnv;
            }
            else {
                Type typexp3= exp3.typecheck(typeEnv);
                if(typexp3.equals(new IntType()))
                {
                    statement.typecheck(typeEnv);
                    return typeEnv;
                }
                else throw new MyException("lala");
            }}


    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        IStatement aux = new CompoundStatement(new CompoundStatement(new VarDeclStmt("v", new IntType()), new AssignStatement("v", exp1)),
                new WhileStatement(new RelationalExpression("<", new VarExpression("v"), exp2), new CompoundStatement(statement, new AssignStatement("v", exp3))));

        state.getExecutionStack().push(aux);
        return null;
    }

    @Override
    public IStatement deepCopy(){
        return new ForStatement2(exp1, exp2, exp3, statement);
    }
}
