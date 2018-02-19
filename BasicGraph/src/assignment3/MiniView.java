/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Monkey
 */
public class MiniView extends Pane implements GraphModelListener{
    MiniViewController ctrl;
    Canvas c;
    GraphicsContext pen;
    GraphView mv; // main view
    GraphModel model;
    InteractionModel im;
    //int state;//0 for change main by mini, 1 for being changed by main
    
    //float nx, ny, nw, nh;
    
    public MiniView(double width, GraphView mainv){
        c = new Canvas(width, width); // miniview will be a square
        pen = c.getGraphicsContext2D();
        getChildren().add(c);
        mv = mainv;
        initialization();
    }
    
    public void setModel(GraphModel gm) {
        model = gm;
    }
    
    public void setInteractionModel(InteractionModel anIModel) {
        im = anIModel;
        im.setSmall(c.getWidth(),c.getHeight());
        float nw = (float) (500/mv.c.getWidth() * c.getWidth());
        float nh = (float) (500/mv.c.getHeight() * c.getHeight());
        im.setNeg(nw, nh);
    }
    
    
    
    public void setMainView(GraphView mainv) {
        mv = mainv;
    }
    
    public void draw(){
        //System.out.println("changing");
        pen.setFill(Color.LIGHTGRAY);
        pen.fillRect(0, 0, 150,150);


        if(im.miniState == 0){ 
            pen.setFill(Color.LIGHTGREEN);
            pen.fillRect(im.nx, im.ny, im.nw, im.nh);
        }else{
            drawNavigates();
        }
        
        // draw vertices
        //System.out.println("you have "+ model.vs.size() + " vertex");
        model.vs.forEach((v) -> {
            drawVertex(v);
        });
        
        drawing();
        
        // draw edges
        model.es.forEach((e) -> {
            drawEdge(e);
        });
        
        
        
    }

    @Override
    public void modelChanged() {
        this.draw();
    }

 /*   @Override
    public void modelChanged(double dx, double dy) {
        this.draw();
    }*/

    private void drawVertex(Vertex v) {
        pen.setStroke(Color.BLACK);
        pen.setLineWidth(1);
         
        if(v == im.selected){
            pen.setStroke(Color.ORANGE);
        }
        
        float setx, sety, topx, topy;
        setx = v.centreX;
        sety = v.centreY;
        

        topx = (float) ((setx - v.radius)/mv.c.getWidth() * c.getWidth());
        topy = (float) ((sety - v.radius)/mv.c.getHeight() * c.getHeight());
        pen.strokeOval(topx, topy, 2, 2);
    }

    private void drawEdge(Edge e) {
        pen.setStroke(Color.BLACK);
        pen.setLineWidth(1);
        float x1, x2, y1, y2;
        //set x
        float vax = (float) (e.start.centreX/mv.c.getWidth() * c.getWidth());
        float vbx = (float) (e.end.centreX/mv.c.getWidth() * c.getWidth());
        if(vax > vbx){
            x1 = vbx+1f; //1f is the radius
            x2 = vax-1f;
            y1 = (float) (e.end.centreY/mv.c.getHeight() * c.getHeight());
            y2 = (float) (e.start.centreY/mv.c.getHeight() * c.getHeight());
        }else{
            x1 = vax+1f; //1f is the radius
            x2 = vbx-1f;
            y1 = (float) (e.start.centreY/mv.c.getHeight() * c.getHeight());
            y2 = (float) (e.end.centreY/mv.c.getHeight() * c.getHeight());
        }
        
        pen.setFill(Color.BLACK);
        pen.strokeLine(x1,y1,x2,y2);
    }

    private void initialization() {
       pen.setFill(Color.LIGHTGRAY);
       pen.fillRect(0, 0, c.getWidth(), c.getHeight());
       pen.setFill(Color.LIGHTGREEN);
       pen.fillRect(0, 0, 500/mv.c.getWidth() * c.getWidth(), 500/mv.c.getHeight() * c.getHeight());
    }



    private void drawNavigates() {
        float x = (float) (im.x/mv.c.getWidth() * c.getWidth());
        float y = (float) (im.y/mv.c.getHeight() * c.getHeight());
        pen.setFill(Color.LIGHTGREEN);
        pen.fillRect(x, y, im.nw, im.nh);
    }

    void setController(MiniViewController mvc) {
        ctrl = mvc;
        c.setOnMousePressed(ctrl::handleMousePressed);
        c.setOnMouseDragged(ctrl::handleMouseDragged);
        c.setOnMouseReleased(ctrl::handleMouseReleased);}

    private void drawing() {
        
        if (im.selected != null && im.mainState == 1) {
            Vertex ev = im.selected;
            float ex = (float) (im.ex + im.x);
            float ey = (float) (im.ey + im.y);
            
            float x1;
            if(ex > ev.centreX) x1 = (float) (ev.centreX + 20f);
            else x1 = (float) (ev.centreX - 20f);
            
            float y1 = (float) (ev.centreY);
            
            x1 = (float) (x1/im.bw * im.sw);
            y1 = (float) (y1/im.bh * im.sh);
            pen.setStroke(Color.BLACK);
            pen.setLineWidth(1);
            pen.strokeLine(x1, y1, ex/im.bw * im.sw, ey/im.bw * im.sw);
        }
    }
    
}
