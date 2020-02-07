package sample;

import Controller.Controller;
import Model.DataStructures.*;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.Heap.HeapReading;
import Model.Expressions.RelationalExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VarExpression;
import Model.ProgramState;
import Model.Statements.*;
import Model.Statements.File.CloseStatement;
import Model.Statements.File.OpenFileStatement;
import Model.Statements.File.ReadFileStatement;
import Model.Statements.Heap.NewStatement;
import Model.Statements.Heap.WriteStatement;
import Repository.InterfaceRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SelectFormController implements Initializable{

    private List<IStatement> programStatements;
    private RunFormController mainWindowController;

    @FXML
    private ListView<String> programListView;

    @FXML
    private Button executeButton;

    void setMainWindowController(RunFormController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    private void buildProgramStatements()
    {
        IStatement ex1 = new CompoundStatement(new VarDeclStmt("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VarExpression("v"))));

        IStatement ex2 = new CompoundStatement( new VarDeclStmt("a",new IntType()),
                new CompoundStatement(new VarDeclStmt("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VarExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VarExpression("b"))))));


        IStatement ex3 = new CompoundStatement(new VarDeclStmt("a",new BoolType()),
                new CompoundStatement(new VarDeclStmt("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IFStatement(new VarExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VarExpression("v"))))));


        IStatement ex4 = new CompoundStatement(new CompoundStatement(new VarDeclStmt("a",new IntType()), new VarDeclStmt("b",new IntType())),
                new CompoundStatement(new OpenFileStatement(new ValueExpression(new StringValue("test.in"))), new CompoundStatement( new ReadFileStatement
                        (new ValueExpression(new StringValue("test.in")), new StringValue("a")), new CompoundStatement( new ReadFileStatement
                        (new ValueExpression(new StringValue("test.in")), new StringValue("b")),   new CompoundStatement(new PrintStatement(new VarExpression("a")),
                        new CompoundStatement(new PrintStatement(new VarExpression("b")),new CloseStatement(new ValueExpression(new StringValue("test.in")))))))));

        //new stmt
        IStatement ex5 = new CompoundStatement(new CompoundStatement(new VarDeclStmt("v", new RefType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new CompoundStatement(new CompoundStatement(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        new NewStatement(new StringValue("a"), new VarExpression("v"))),  new CompoundStatement(new PrintStatement(new VarExpression("v")),
                        new PrintStatement(new VarExpression("a")))));


        //read expr
        IStatement ex6 = new CompoundStatement( new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VarDeclStmt
                ("v", new RefType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new VarDeclStmt("a", new RefType(new RefType(new IntType())))), new NewStatement(new StringValue("a"),
                new VarExpression("v"))), new PrintStatement(new HeapReading(new VarExpression("v")))), new PrintStatement(
                new ArithmeticExpression('+', new HeapReading(new HeapReading(new VarExpression("a"))),
                        new ValueExpression(new IntValue(5)))));



        //write stmt
        IStatement ex7 =new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VarDeclStmt("v", new RefType(new IntType())), new NewStatement(new StringValue("v"),
                new ValueExpression(new IntValue(20)))), new PrintStatement(new HeapReading(new VarExpression("v")))),
                new WriteStatement(new StringValue("v"), new ValueExpression(new IntValue(30)))), new PrintStatement(new ArithmeticExpression('+', new HeapReading(new VarExpression("v")), new ValueExpression(new IntValue(5)))));


        //while
        IStatement ex8=new CompoundStatement(new VarDeclStmt("v",new IntType()), new CompoundStatement(
                new AssignStatement("v",new ValueExpression(new IntValue(4))), new WhileStatement(
                new RelationalExpression(">", new VarExpression("v"),new ValueExpression(new IntValue(0))),
                new CompoundStatement(new PrintStatement(new VarExpression("v")),
                        new AssignStatement( "v",new ArithmeticExpression('-',new VarExpression("v"),
                                new ValueExpression(new IntValue(1))))))));


        //garbage collector
        IStatement ex9=new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VarDeclStmt("v", new RefType(new IntType())),
                new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))), new VarDeclStmt("a",
                new RefType(new RefType(new IntType())))), new NewStatement(new StringValue("a"), new VarExpression("v"))),
                new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(30)))), new PrintStatement(new HeapReading(new HeapReading(new VarExpression("a")))));


        IStatement ex10 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VarDeclStmt("v", new IntType()), new VarDeclStmt("a", new RefType(new IntType()))),
                new AssignStatement("v", new ValueExpression(new IntValue(10)))), new NewStatement(new StringValue("a"), new ValueExpression(new IntValue(22)))),
                new ForkStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new WriteStatement(new StringValue("a"), new ValueExpression(new IntValue(30))), new AssignStatement("v", new ValueExpression(new IntValue(32)))),
                        new PrintStatement(new VarExpression("v"))), new PrintStatement(new HeapReading(new VarExpression("a")))))), new PrintStatement(new VarExpression("v"))),
                new PrintStatement(new HeapReading(new VarExpression("a"))));

        IStatement ex11= new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VarDeclStmt("v", new IntType()), new AssignStatement("v", new ValueExpression(new IntValue(10)))),
                new ForkStatement(new CompoundStatement(new CompoundStatement(new AssignStatement("v", new ArithmeticExpression('-', new VarExpression("v"), new ValueExpression(new IntValue(1)))),
                        new AssignStatement("v", new ArithmeticExpression('-', new VarExpression("v"), new ValueExpression(new IntValue(1))))), new PrintStatement(new VarExpression("v"))))),
                new SleepStatement(10)), new PrintStatement(new ArithmeticExpression('*', new VarExpression("v"), new ValueExpression(new IntValue(10)))));



       /* IStatement ex12 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new VarDeclStmt("a", new RefType(new IntType())), new NewStatement(new StringValue("a"), new ValueExpression(new IntValue(20)))),
                new ForStatement2(new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)), new ArithmeticExpression('+', new VarExpression("v"), new ValueExpression(new IntValue(1))),
                        new ForkStatement(new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                new AssignStatement("v", new HeapReading(new ArithmeticExpression('*', new VarExpression("v"), new VarExpression("a")))))))),
                new PrintStatement(new HeapReading(new VarExpression("a"))));
*/
        IStatement ex13 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new VarDeclStmt("a", new RefType(new IntType())),
                new NewStatement(new StringValue("a"), new ValueExpression(new IntValue(20)))),
                new ForStatement2( new ValueExpression(new IntValue(0)),
                        new ValueExpression(new IntValue(3)),
                        new ArithmeticExpression('+',new VarExpression("v"), new ValueExpression(new IntValue(1))),
                        new ForkStatement(new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                new AssignStatement("v", new HeapReading(new ArithmeticExpression('*',
                                        new VarExpression("v"), new VarExpression("a")))))))),
                new PrintStatement(new HeapReading(new VarExpression("a"))));

        IStatement fork2 = new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new CompoundStatement(new lockStatement(new StringValue("q")), new WriteStatement(new StringValue("v1"),
                new ArithmeticExpression('+', new HeapReading(new VarExpression("v2")), new ValueExpression(new IntValue(5))))),
                new unlockStatement(new StringValue("q")))), new CompoundStatement(new CompoundStatement(new lockStatement(new StringValue("q")),
                new WriteStatement(new StringValue("v2"), new HeapReading(new ArithmeticExpression('*', new VarExpression("v2"), new ValueExpression(new IntValue(10)))))),
                new unlockStatement(new StringValue("q")))));

        IStatement lock1 = new CompoundStatement(new CompoundStatement(new lockStatement(new StringValue("x")), new PrintStatement(new HeapReading(new VarExpression("v1")))), new unlockStatement(new StringValue("x")));
        IStatement lock2 = new CompoundStatement(new CompoundStatement(new lockStatement(new StringValue("q")), new PrintStatement(new HeapReading(new VarExpression("v2")))), new unlockStatement(new StringValue("q")));


        IStatement ex14 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VarDeclStmt("v1", new RefType(new IntType())), new VarDeclStmt("v2", new RefType(new IntType()))),
                new VarDeclStmt("x", new IntType())), new VarDeclStmt("q", new IntType())), new NewStatement(new StringValue("v1"), new ValueExpression(new IntValue(190)))),
                new NewStatement(new StringValue("v2"), new ValueExpression(new IntValue(350)))), new newLockStatement(new StringValue("x"))),
                new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new CompoundStatement(new lockStatement(new StringValue("x")), new WriteStatement(new StringValue("v1"),
                        new ArithmeticExpression('-', new HeapReading(new VarExpression("v1")), new VarExpression("1")))),
                new unlockStatement(new StringValue("x")))), new CompoundStatement(new CompoundStatement(new lockStatement(new StringValue("x")),
                        new WriteStatement(new StringValue("v2"), new HeapReading(new ArithmeticExpression('*', new VarExpression("v1"), new ValueExpression(new IntValue(10)))))),
                        new unlockStatement(new StringValue("x")))))), new newLockStatement(new StringValue("q"))), fork2), lock1), lock2);


        programStatements=new ArrayList<>(Arrays.asList(ex1,ex2,ex3,ex4,ex5,ex6,ex7,ex8,ex9,ex10,ex11,ex13,ex14));
    }


    private List<String> getStringRepresentations(){
        return programStatements.stream().map(IStatement::toString).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        buildProgramStatements();
        programListView.setItems(FXCollections.observableArrayList(getStringRepresentations()));

        executeButton.setOnAction(actionEvent -> {
            int index = programListView.getSelectionModel().getSelectedIndex();

            if(index < 0)
                return;

            ProgramState initialProgramState = new ProgramState(programStatements.get(index));
            InterfaceRepository repository = new Repository("log" + index + ".txt");
            repository.addProgramState(initialProgramState);
            Controller ctrl = new Controller(repository);

            mainWindowController.setController(ctrl);
        });


    }


}
