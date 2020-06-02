package sample;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    int numOfV=0;
    Digraph<String, String> g;
    SmartPlacementStrategy strategy ;
    SmartGraphPanel<String, String> graphView ;
    Stage stage;
    SFG sfg;
    int unique=0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SFG");

        TextField v= new TextField("write the numbers of vertex");
        v.setMaxWidth(160);
        Button go=new Button("GO");

        TextField from= new TextField("From");
        from.setMaxWidth(50);
        TextField to= new TextField("to");
        to.setMaxWidth(50);
        TextField weight= new TextField("weight");
        weight.setMaxWidth(50);
        Button add=new Button("Add");
        Button next=new Button("Next");

        TextField lastNode= new TextField("Enter the output node");
        lastNode.setMaxWidth(140);
        Button calc=new Button("Calc");


        VBox firstLay=new VBox(10);
        firstLay.setAlignment(Pos.CENTER);
        firstLay.getChildren().addAll(v,go);
        Scene firstScene=new Scene(firstLay, 300, 275);

        HBox B21=new HBox(10);
        B21.getChildren().addAll(from,to,weight);
        B21.setAlignment(Pos.CENTER);
        HBox B22=new HBox(10);
        B22.getChildren().addAll(add,next);
        B22.setAlignment(Pos.CENTER);
        VBox V2=new VBox(10);
        V2.getChildren().addAll(B21,B22);
        V2.setAlignment(Pos.CENTER);
        Scene s2=new Scene(V2, 300, 275);

        VBox V3=new VBox(10);
        V3.setAlignment(Pos.CENTER);
        V3.getChildren().addAll(lastNode,calc);
        Scene s3=new Scene(V3, 300, 275);

        go.setOnAction(e -> {
            try{
                primaryStage.setScene(s2);
                numOfV=(Integer.parseInt(v.getText()));
                g = new DigraphEdgeList<>();
                for(int i=1;i<=numOfV;i++)
                    g.insertVertex(""+i);
              strategy = new SmartCircularSortedPlacementStrategy();
            graphView = new SmartGraphPanel<>(g, strategy);
            Scene scene = new Scene(graphView, 700, 650);

            stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("SFG");
            stage.setScene(scene);
            stage.show();

            graphView.init();

                sfg=new SFG(numOfV);

            }catch (Exception error){
                System.out.println("only numbers is allowed");
            }
        });

        add.setOnAction(e ->{
            try {
                int f=(Integer.parseUnsignedInt(from.getText()));
                int t=(Integer.parseUnsignedInt(to.getText()));
                int w=(Integer.parseInt(weight.getText()));
                    g.insertEdge("" + f, "" + t, "" + w + unique++);
                    
                sfg.addEdge(f,t,w);
                strategy = new SmartCircularSortedPlacementStrategy();
                graphView = new SmartGraphPanel<>(g, strategy);
                Scene scene = new Scene(graphView, 700, 650);

                stage.setTitle("SFG");
                stage.setScene(scene);
                stage.show();

                graphView.init();
                graphView.update();

            }catch (Exception error){
                System.out.println("only right numbers is allowed   "+error);
            }
        });

        next.setOnAction(e -> primaryStage.setScene(s3));

        calc.setOnAction(e -> {
            try {
                int last=(Integer.parseInt(lastNode.getText()));

                if(last<=numOfV){
                    sfg.setLastNode(last);
                    double ans=sfg.calc();
                    Label l1=new Label("The result is => " + ans);
                    VBox v4=new VBox(10);
                    v4.setAlignment(Pos.CENTER);
                    v4.getChildren().add(l1);
                    Scene s4=new Scene(v4, 300, 275);
                    primaryStage.setScene(s4);
                }else{
                    System.out.println("Enter a number in the graph");
                }
            }catch (Exception error){
                System.out.println("only numbers is allowed");
            }

        });


        primaryStage.setScene(firstScene);
        primaryStage.setResizable(false);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
