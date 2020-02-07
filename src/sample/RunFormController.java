package sample;

import Collection.InterfaceMyStack;
import Collection.InterfaceMyDictionary;
import Collection.InterfaceMyList;
import Collection.InterfaceLock;
import Collection.InterfaceHeap;
import Controller.Controller;
import Model.DataStructures.IntValue;
import Model.DataStructures.Value;
import Model.Expressions.ValueExpression;
import Model.MyException;
import Model.ProgramState;
import Model.Statements.IStatement;
import com.sun.source.doctree.AttributeTree;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RunFormController implements Initializable{

    private Controller controller;
    private int currentId;

    @FXML
    private TableView<Map.Entry<Integer,Integer>> lockTableView;

    @FXML
    private TableColumn<Map.Entry<Integer,Integer>,Integer> lockTableColumn1;

    @FXML
    private TableColumn<Map.Entry<Integer,Integer>,Integer> lockTableColumn2;

    @FXML
    private TableView<Map.Entry<Integer,Value>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Value> heapValueColumn;

    @FXML
    private TableView<Map.Entry<Value, BufferedReader>> fileTableView;

    @FXML
    private TableColumn<Map.Entry<Value, BufferedReader>, Value> fileIdentifierColumn;

    @FXML
    private TableColumn<Map.Entry<Value, BufferedReader>, Value> fileNameColumn;

    @FXML
    private TableView<Map.Entry<String, Value>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolTableVariableColumn;

    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> symbolTableValueColumn;

    @FXML
    private ListView<Value> outputListView;

    @FXML
    private ListView<Integer> programStateListView;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private Button executeOneStepButton;

    void setController(Controller controller)
    {
        this.controller=controller;
        populateProgramStateIdentifiers();
    }

    private List<Integer> getProgramStateIds(List<ProgramState> programStateList)
    {
        return programStateList.stream().map(ProgramState::getId).distinct().collect(Collectors.toList());
    }

    private void populateProgramStateIdentifiers()
    {
        List<ProgramState> programStates=controller.getRepository().getProgramStateList();
        programStateListView.setItems(FXCollections.observableList(getProgramStateIds(programStates)));

        numberOfProgramStatesTextField.setText(""+programStates.size() );
    }

    private void executeOneStep()
    {
        if(controller==null)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR,"Program not seleced",ButtonType.OK);
            alert.showAndWait();
            return;
        }

        boolean programStateLeft =controller.getRepository().getProgramStateWithId(currentId).getExecutionStack().isEmpty();

                if(programStateLeft)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

        controller.executeOneStep();

        changeProgramState(controller.getRepository().getProgramStateWithId(currentId));
        populateProgramStateIdentifiers();

    }

    private void changeProgramState(ProgramState currentProgramState)
    {
        if(currentProgramState==null)
            return;
        populateExecutionStack(currentProgramState);
        populateSymbolTable(currentProgramState);
        populateOutput(currentProgramState);
        populateFileTable(currentProgramState);
        populateHeapTable(currentProgramState);
        populateLockTable(currentProgramState);
    }

    private void populateLockTable(ProgramState currentProgramState)
    {
       InterfaceLock lockTable=currentProgramState.getLockTable();
        List<Map.Entry<Integer,Integer>> lockTableList=new ArrayList<>();
        for(Map.Entry<Integer,Integer> entry: lockTable.getAll())
        {
                lockTableList.add(entry);

        }
        lockTableView.setItems(FXCollections.observableList(lockTableList));
        lockTableView.refresh();
    }

    private void populateHeapTable(ProgramState currentProgramState)
    {
        InterfaceHeap<Integer,Value> heapTable=currentProgramState.getHeapTable();

        List<Map.Entry<Integer, Value>> heapTableList = new ArrayList<>(heapTable.getEntrySet());
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

    private void populateFileTable(ProgramState currentProgramState)
    {
        InterfaceMyDictionary<Value,BufferedReader> fileTable=currentProgramState.getFileTable();
        Map<Value,BufferedReader> fileTableMap=new HashMap<>();
        for(Map.Entry<Value,BufferedReader> entry: fileTable.getAll())
        {
            fileTableMap.put(entry.getKey(),entry.getValue());

        }
        List<Map.Entry<Value,BufferedReader>> fileTableList=new ArrayList<>(fileTableMap.entrySet());
        fileTableView.setItems(FXCollections.observableList(fileTableList));
        fileTableView.refresh();
    }

    private void populateOutput(ProgramState currentProgramState) {
        InterfaceMyList<Value> ot = currentProgramState.getOutputList();
        ArrayList<Value> output = new ArrayList<>();
        for (int i = 0; i < ot.size(); i++){
            output.add(ot.get(i));
        }
        outputListView.setItems(FXCollections.observableList(output));
        outputListView.refresh();
    }

    private void populateSymbolTable(ProgramState currentProgramState)
    {
        InterfaceMyDictionary<String, Value> symbolTable=currentProgramState.getSymbolTable();

        List<Map.Entry<String,Value>> symbolTableList=new ArrayList<>();
        for(Map.Entry<String, Value> entry : symbolTable.getAll())
        {

            symbolTableList.add((entry));
        }
        symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        symbolTableView.refresh();


    }

    private void populateExecutionStack(ProgramState currentProgramState)
    {
        InterfaceMyStack<IStatement> executionStack=currentProgramState.getExecutionStack();

        List<String> executionStackList=new ArrayList<>();
        for(IStatement s:executionStack.getAll())
        {
            executionStackList.add(s.toString());
        }
        executionStackListView.setItems(FXCollections.observableList(executionStackList));
        executionStackListView.refresh();
    }

    private ProgramState getCurrentProgramState()
    {
        if(programStateListView.getSelectionModel().getSelectedIndex()==-1)
            return null;

        currentId=programStateListView.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramStateWithId(currentId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        lockTableColumn1.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getKey()));
        lockTableColumn2.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getValue()));

        heapAddressColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        heapValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        fileIdentifierColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        fileNameColumn.setCellValueFactory(p -> new SimpleObjectProperty(p.getValue().getValue().toString() + ""));
        symbolTableVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        programStateListView.setOnMouseClicked(mouseEvent -> { changeProgramState(getCurrentProgramState()); });

        executeOneStepButton.setOnAction(actionEvent -> { executeOneStep(); });
    }



}
