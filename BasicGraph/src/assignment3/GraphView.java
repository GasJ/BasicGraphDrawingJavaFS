/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;


import java.awt.Rectangle;
import java.awt.Stroke;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author Monkey
 * A custom view for drawing the graph
 */
public class GraphView extends Pane implements GraphModelListener{
    GraphModel model;
    Canvas c;
    GraphicsContext pen;
    GraphViewController ctrl;
    InteractionModel iModel;
    //MiniView mini;
    
    
    public GraphView(double width, double height) {
        c = new Canvas(width, height);
        pen = c.getGraphicsContext2D();
        getChildren().add(c);
        //vp = new Rectangle();
    }
 /*   
    void setMini(MiniView mini) {
        this.mini = mini;
    }*/
    
    public void setController(GraphViewController gvc){
        ctrl = gvc;
        c.setOnMousePressed(ctrl::handleMousePressed);
        c.setOnMouseDragged(ctrl::handleMouseDragged);
        c.setOnMouseReleased(ctrl::handleMouseReleased);
    }
    
    public void setInteractionModel(InteractionModel anIModel) {
        iModel = anIModel;
        iModel.setBig(c.getWidth(),c.getHeight());
    }
    
    public void setModel(GraphModel gm) {
        model = gm;
    }
    
    public void draw() {
        
        pen.setFill(Color.GRAY);
        pen.fillRect(0, 0, c.getWidth(), c.getHeight());
        
        // draw vertices
        model.vs.forEach((v) -> {
            drawVertex(v);
        });
        
        // draw temporary edge if there is one being drawn
        drawingEdge();
        
        // draw edges
        model.es.forEach((e) -> {
            drawEdge(e);
        });
        
        
        
    }

    @Override
    public void modelChanged() {
        //System.out.println("model is changing without argument");
        this.draw();
    }
    

    private void drawEdge(Edge e) {
        pen.setLineWidth(1);
        float x1, x2, y1, y2;
        //set x
        float vax = e.start.centreX;
        float vbx = e.end.centreX;
        if(vax > vbx){
            x1 = vbx + 20f; //20f is the radius
            x2 = vax - 20f;
            y1 = e.end.centreY;
            y2 = e.start.centreY;
        }else{
            x1 = vax + 20f; //20f is the radius
            x2 = vbx - 20f;
            y1 = e.start.centreY;
            y2 = e.end.centreY;
        }
        
        float x, y;
        x = (float) iModel.x;
        y = (float) iModel.y;
        pen.setFill(Color.BLACK);
        pen.strokeLine(x1-x,y1-y,x2-x,y2-y);
    }

    private void drawVertex(Vertex v) {
        float x, y;
        x = (float) iModel.x;
        y = (float) iModel.y;

        pen.setLineWidth(1);
        pen.setStroke(Color.BLACK);
        
        if (v == iModel.selected && iModel.mainState != 1) {
            pen.setFill(Color.ORANGE);
        } else {
            pen.setFill(Color.LIGHTBLUE);
        }
        
        float topx, topy;
        topx = v.centreX - v.radius;
        topy = v.centreY - v.radius;
        pen.fillOval(topx - x, topy - y, v.radius * 2, v.radius * 2);
        
        if(v == iModel.selected && iModel.mainState == 1){
            pen.setLineWidth(3);
        }
        pen.strokeOval(topx - x, topy - y, v.radius * 2, v.radius * 2);
        
        pen.setFill(Color.BLACK);
        if(v.id < 10){
            pen.fillText(String.valueOf(v.id), v.centreX-4-x, v.centreY+5-y);
        }else{
            pen.fillText(String.valueOf(v.id), v.centreX-7-x, v.centreY+5-y);
        }
         
        
    }

    private void drawingEdge() {
        float x, y;
        x = (float) iModel.x;
        y = (float) iModel.y;
        
        if (iModel.selected != null && iModel.mainState == 1) {
            Vertex ev = iModel.selected;
            float ex = iModel.ex;
            float ey = iModel.ey;
            
            float x1;
            if(ex + x > ev.centreX) x1 = (float) (ev.centreX + 20f - x);
            else x1 = (float) (ev.centreX - 20f - x);
            
            float y1 = (float) (ev.centreY - y);
            pen.setStroke(Color.BLACK);
            pen.setLineWidth(3);
            pen.strokeLine(x1, y1, ex, ey);
        }
    }
    /*
    public void setEdgesV(Vertex aVer) {
        //ev = aVer;
        draw();
    }*/
    
    /*
    public void setLineEnd(float newX, float newY) {
        ex = newX;
        ey = newY;
        draw();
    }*/
        
      
    
}

