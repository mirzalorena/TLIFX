package Model;

import Collection.*;
import Model.DataStructures.Value;
import Model.Statements.IStatement;
import java.io.*;

public class ProgramState {
    private InterfaceMyStack<IStatement> executionStack;
    private InterfaceMyDictionary<String,Value> symbolTable;
    private InterfaceMyList<Value> outputList;
    private IStatement originalProgram;
    private InterfaceMyDictionary<Value, BufferedReader> fileTable;
    private InterfaceHeap heapTable;
    private int id;
   private int lastAssigned=0;
    private InterfaceLock lockTable;
    private int lockAddress=0;

    public int getNewId()
    {
        lastAssigned++;
        return lastAssigned;
    }


    public ProgramState(InterfaceMyStack<IStatement> programStateStack,InterfaceMyDictionary<String,Value> symbolTable,InterfaceMyList<Value> outputList, IStatement originalProgram,InterfaceMyDictionary<Value, BufferedReader> fileTable,InterfaceHeap<Integer,Value> heapTable,InterfaceLock lockTable)
    {
       this.executionStack=programStateStack;
       this.symbolTable=symbolTable;
       this.outputList=outputList;
       this.originalProgram=originalProgram.deepCopy();
       this.fileTable=fileTable;
       this.heapTable=heapTable;
       this.id=getNewId();
       this.lockTable=lockTable;


       executionStack.push(originalProgram);
    }

    public ProgramState(InterfaceMyStack<IStatement> programStateStack,InterfaceMyDictionary<String,Value> symbolTable,InterfaceMyList<Value> outputList, IStatement originalProgram,InterfaceMyDictionary<Value, BufferedReader> fileTable,InterfaceHeap<Integer,Value> heapTable,int id,InterfaceLock lockTable)
    {
        this.executionStack=programStateStack;
        this.symbolTable=symbolTable;
        this.outputList=outputList;
        this.originalProgram=originalProgram.deepCopy();
        this.fileTable=fileTable;
        this.heapTable=heapTable;
        this.id=id;
        this.lockTable=lockTable;

        executionStack.push(originalProgram);
    }

    public ProgramState(IStatement initialProgram)
    {
        this.executionStack= new MyStack<>();
        this.symbolTable= new MyDictionary<String,Value>();
        this.outputList= new MyList<>();
        this.originalProgram = initialProgram.deepCopy();
        this.fileTable=new MyDictionary<Value,BufferedReader>();
        this.heapTable=new HeapTable<Integer,Value>();
        this.id=getNewId();
        this.lockTable=new LockTable();

        executionStack.push(originalProgram);
    }

    public int getId() {return id;}

    public InterfaceLock getLockTable() {return this.lockTable;}
    public void setLockTable(InterfaceLock lockTable1){this.lockTable=lockTable1;}
    public int getLockAdress(){return lockAddress++;}

    public InterfaceMyStack<IStatement> getExecutionStack()
    {
        return executionStack;
    }


    public InterfaceMyDictionary<String, Value> getSymbolTable()
    {
        return symbolTable;
    }

    public void setSymbolTable(InterfaceMyDictionary<String,Value> symbolTable)
    {
        this.symbolTable=symbolTable;
    }

    public InterfaceMyList<Value> getOutputList()
    {
        return outputList;
    }


    public IStatement getOriginalProgram()
    {
        return originalProgram;
    }


    public InterfaceMyDictionary<Value,BufferedReader> getFileTable(){return this.fileTable;}


    public InterfaceHeap<Integer,Value> getHeapTable(){return this.heapTable;}


    public Boolean isNotCompleted()
    {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStepExecution() throws MyException
    {
        if(executionStack.isEmpty())
            throw new MyException("Execution stack is empty\n");
        IStatement currentStatement=executionStack.pop();

            return currentStatement.execute(this);

    }



    @Override
    public String toString()
    {
        return "\nID: "+id +
                "\nExecution stack:\n"+
                executionStack.toString()+"\nSymbol table:\n"+
                symbolTable.toString()+"\nOutput list:\n"+
                outputList.toString()+"\nFile table:\n"+
                fileTable.toString()+"\nHeap table:\n"+
                heapTable.toString()+"\nLock table:\n"+
                lockTable.toString()+"\n";

    }







}
