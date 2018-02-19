/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.awt.Color;
import java.awt.Panel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Monkey
 * 
 * JavaFX application class
 */
public class GraphDemo extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
        // Set up MVC
        GraphView mainPanel = new GraphView(2000,2000);
        GraphModel gm = new GraphModel();
        GraphViewController controller = new GraphViewController();
        MiniView mini = new MiniView(150, mainPanel);
        InteractionModel iModel = new InteractionModel();
        MiniViewController mvc = new MiniViewController();
        
        // connections between M,V,C
        controller.setModel(gm);
        controller.setInteractionModel(iModel);
        mainPanel.setInteractionModel(iModel);
        iModel.addSubscriber(mainPanel);
        mainPanel.setModel(gm);
        mainPanel.setController(controller);
        mainPanel.draw();
        gm.addSubscriber(mainPanel);

        mainPanel.setModel(gm);
        gm.addSubscriber(mainPanel);
        
        //mini part
        mini.setModel(gm);
        mini.setController(mvc);
        mini.setInteractionModel(iModel);
        iModel.addSubscriber(mini);
        gm.addSubscriber(mini);
        
        mvc.setInteractionModel(iModel);
        mvc.setModel(gm);
        
        StackPane sk = new StackPane();
        
        sk.getChildren().addAll(mainPanel);
        sk.getChildren().addAll(mini);
        
        StackPane.setAlignment(mainPanel, Pos.TOP_LEFT);
        StackPane.setAlignment(mini, Pos.TOP_LEFT);
        mini.setPickOnBounds(false);
      
        primaryStage.setTitle("Graph Demo!");
        Scene scene = new Scene(sk, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
