package Repository;

import Model.MyException;
import Model.ProgramState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public interface InterfaceRepository {

    ProgramState getCurrentProgram();
    ArrayList<ProgramState> getProgramStateList();
    void setProgramStateList(ArrayList<ProgramState> programStateList);
    ProgramState getProgramStateWithId(int currentId);

    void logPrgStateExec(ProgramState state) throws MyException;

    void addProgramState(ProgramState initialProgramState);


}
