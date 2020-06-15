package org.openjfx;

import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXMLLoader;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Method of potentials");

//        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
//        Image image = new Image(iconStream);
//        stage.getIcons().add(image);

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/startScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Label newLabel = new Label("Аааааа");

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    public void mainStep(ArrayList<ArrayList<Integer>> StartPlan, ArrayList<ArrayList<Integer>> StartCosts) throws IOException {
        Stage stage = new Stage();
        stage.setWidth(800);
        stage.setHeight(800);
        Button but = new Button("Важная кнопка!");


        stage.setScene(moveOn(StartPlan, StartCosts, but));

        MethodPotential method = new MethodPotential(StartPlan, StartCosts);
        try {
            method.getOptimizedSolution();
        }
        catch (Exception ex){
            System.out.println("Unable to optimize");
            return;
        }

        ArrayList<Integer> stepNumber = new ArrayList<>();
        stepNumber.add(0);
//        ArrayList<ArrayList<Integer>> stepToShow = getInformationFromLine.getInfo(method.getSteps().get(stepNumber.get(0)).replace("[", " ")
//                .replace("]", " ").replace(",", " "));
//        System.out.println(stepToShow);
//        System.out.println(method.getSteps().get(0));
//        System.out.println(method.getUsteps().get(0));
//        System.out.println(method.getVsteps().get(0));

        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Я сработала");
                try {
                    // Шаг, который надо показать
                    if (stepNumber.get(0) >= method.getSteps().size()) {return;}
                    ArrayList<ArrayList<Integer>> stepToShow = getInformationFromLine.getInfo(method.getSteps().get(stepNumber.get(0)).replace("[", "")
                            .replace("]", "").replace(",", " "));
                    stage.setScene(moveOn(stepToShow, StartCosts, but));
                    stepNumber.set(0, stepNumber.get(0) + 1);
                } catch (IOException e) {
                    return;
                }
                stage.show();
            }
        });
        stage.show();
    }


    public Scene moveOn(ArrayList<ArrayList<Integer>> StartPlan, ArrayList<ArrayList<Integer>> StartCosts, Button but) throws IOException {

        Data getItems = new Data(StartPlan, StartCosts);
        ArrayList<Integer> A = getItems.getA();
        ArrayList<Integer> B = getItems.getB();
        ArrayList<ArrayList<Integer>> costs = getItems.getCosts();


        HBox hbox = new HBox();
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();

        TableView table1 = drawTheTable.drawTable(StartPlan, A, B);
        TableView table2 = drawTheTable.drawTable(StartCosts, A, B);

        vbox1.getChildren().addAll(table1, but);
        vbox2.getChildren().addAll(table2, new Label(" 9 "));
        hbox.getChildren().addAll(vbox1, vbox2);

//        FXMLLoader loader = new FXMLLoader();
//        URL xmlUrl = getClass().getResource("/mainScene.fxml");
//        loader.setLocation(xmlUrl);
//        Parent root = null;
//        root = loader.load();



        return new Scene(hbox);
    }

}