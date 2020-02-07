package Controller;

import Collection.InterfaceMyStack;
import Model.Statements.IStatement;
import Model.MyException;
import Model.ProgramState;
import Repository.InterfaceRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private InterfaceRepository repository;
    private GarbageColector myGarbageCollector;
    private ExecutorService executor;

    public Controller(InterfaceRepository repository)
    {
        this.repository=repository;
        this.myGarbageCollector=new GarbageColector();
    }

    private ArrayList removeCompletedProgram(List<ProgramState> inProgress)
    {
        return (ArrayList) inProgress.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public IStatement getOriginalProgram()
    {
        return repository.getCurrentProgram().getOriginalProgram();

    }

    public InterfaceRepository getRepository()
    {
        return this.repository;
    }


    public void executeOneStep()
    {
        executor = Executors.newFixedThreadPool(8);
        ArrayList<ProgramState> programs= repository.getProgramStateList();

        programs.forEach(program-> {
            try
            {
                repository.logPrgStateExec(program);
            }
            catch (MyException e)
            {
                System.out.println(e.toString());
            }
        });

        List<Callable<ProgramState>> callList=programs.stream().map((ProgramState p) -> (Callable<ProgramState>) p::oneStepExecution).collect(Collectors.toList());

        List<ProgramState> newProgramList=null;
        try
        {
            newProgramList=executor.invokeAll(callList).stream().
                    map(future-> {
                        try
                        {
                            return future.get();
                        }
                        catch(InterruptedException | ExecutionException e)
                        {
                            System.out.println(e.toString());
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

        }
        catch (InterruptedException e)
        {
            System.out.println(e.toString());
        }

        assert newProgramList != null;
        programs.addAll(newProgramList);
        programs.forEach(program->
        {
            try
            {
                repository.logPrgStateExec(program);
                System.out.println(repository.getCurrentProgram().toString()+"\n");
            }
            catch(MyException e)
            {
                System.out.println(e.toString());
            }
        });

        repository.setProgramStateList((ArrayList)programs);

        executor.shutdownNow();
    }

    private void oneStepForAllProgram(ArrayList<ProgramState> programs) throws MyException,InterruptedException
    {
        programs.forEach(program-> {
            try
            {
                repository.logPrgStateExec(program);
            }
            catch (MyException e)
            {
                System.out.println(e.toString());
            }
        });

        var callList=programs.stream().map((ProgramState p) -> (Callable<ProgramState>)()->{return p.oneStepExecution();}).collect(Collectors.toList());

        List<ProgramState> newProgramList=null;
        try
        {
            newProgramList=executor.invokeAll(callList).stream().
                    map(future-> {
                        try
                        {
                            return future.get();
                        }
                        catch(InterruptedException | ExecutionException e)
                        {
                            System.out.println(e.toString());
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

        }
        catch (InterruptedException e)
        {
            System.out.println(e.toString());
        }

        programs.addAll(newProgramList);
        programs.forEach(program->
        {
            try
            {
                repository.logPrgStateExec(program);
                System.out.println(repository.getCurrentProgram().toString()+"\n");
            }
            catch(MyException e)
            {
                System.out.println(e.toString());
            }
        });

        programs.addAll(newProgramList);
        programs.forEach(program->
        {
            try
            {
                repository.logPrgStateExec(program);
                System.out.println(program.toString()+'\n');
            }
            catch(MyException e)
            {
                System.out.println(e.toString());
            }
        });

        repository.setProgramStateList((ArrayList)programs);



    }

    public void allStep() throws MyException,InterruptedException
    {
        executor= Executors.newFixedThreadPool(2);

        ArrayList programList=removeCompletedProgram(repository.getProgramStateList());

        while(!programList.isEmpty())
        {
            repository.getCurrentProgram().getHeapTable().setContent(
                    myGarbageCollector.safeGarbageCollector(myGarbageCollector.addIndirections(
                            myGarbageCollector.getAdresFromTables(repository.getProgramStateList()),repository.getCurrentProgram().getHeapTable()
                    ), repository.getCurrentProgram().getHeapTable()));


            oneStepForAllProgram(programList);
            programList=removeCompletedProgram(repository.getProgramStateList());


        }
        executor.shutdownNow();
        repository.setProgramStateList(programList);

    }

}
