/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.util.Optional;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Monkey
 * Controller for the GraphView
 */
public class GraphViewController {
    GraphModel model;
    InteractionModel iModel;
    Vertex currentVertex;
    
    int state;
    final int STATE_READY = 0;
    final int STATE_VERTEX = 1;
    final int STATE_DRAGGING = 2;
    final int STATE_DRAWING_EDGE = 3;
    final int STATE_MOVINGBACK = 4;
    final int STATE_BACKGROUND = 5;

    float currentx, currenty, prevX, prevY, dx, dy;
    
    
    public GraphViewController(){
        
    }
    
    public void setModel(GraphModel aModel) {
        model = aModel;
    }
    
    public void setInteractionModel(InteractionModel anIModel) {
        iModel = anIModel;
    }
    
    
    public void handleMousePressed(MouseEvent event){
        prevX = currentx;
        prevY = currenty;
        currentx = (float) event.getX();
        currenty = (float) event.getY();
        
        //System.out.println("you are in the handle mouse pressed");
        switch (state){
            case STATE_READY:
                System.out.println("STATE_READY");
                //on vertex or other
                Optional<Vertex> maybeVertex = 
                        model.find(event.getSceneX()+iModel.x, event.getSceneY()+iModel.y);
                if (maybeVertex.isPresent()) {
                    if(event.isShiftDown()){
                        currentVertex = maybeVertex.get();
                        //System.out.println("you are in shift click");
                        iModel.setDrawingV(currentVertex, event.getX(), event.getY());
                        state = STATE_VERTEX;
                        
                    }else{
                        iModel.setSelected(maybeVertex.get());
                        prevX = (float) event.getX();
                        prevY = (float) event.getY();
                        state = STATE_DRAGGING;
                    }
                    
                } else {
                    state = STATE_BACKGROUND;
                }
                break;                    
        }
    }
  
    
    public void handleMouseDragged(MouseEvent event){
        currentx = (float)event.getX();
        currenty = (float)event.getY();
        dx = (float) (currentx - prevX);
        dy  = (float) (currenty - prevY);
        switch (state) {
            case STATE_DRAGGING:
                System.out.println("STATE_DRAGGING");
                dx = (float) (event.getX() - prevX);
                dy = (float) (event.getY() - prevY);
                model.moveVertex(iModel.selected, dx, dy);
                prevX = (float) event.getX();
                prevY = (float) event.getY();
                state = STATE_DRAGGING;
                break;
            case STATE_BACKGROUND:
                System.out.println("STATE_BACKGROUND");
                state = STATE_MOVINGBACK;
                break;
            case STATE_VERTEX:
                System.out.println("STATE_VERTEX");
                state = STATE_DRAWING_EDGE;
            case STATE_DRAWING_EDGE:
                System.out.println("STATE_DRAWING_EDGE");
                iModel.setLineEnd((float)(event.getX()), (float)event.getY());
                break;
            case STATE_MOVINGBACK:
                System.out.println("STATE_DRAGGING");
                iModel.moveViewport(dx,dy);
                break;            
        }
        
    }
    
    public void handleMouseReleased(MouseEvent event){
        
        switch (state) {
            case STATE_DRAGGING:
                System.out.println("STATE_DRAGGING");
                iModel.setSelected(null);
                break;
            case STATE_BACKGROUND:
                System.out.println("STATE_BACKGROUND");
                model.addVertex((float)event.getX()+ (float)iModel.x, 
                        (float)event.getY() + (float)iModel.y);
                break;
            case STATE_DRAWING_EDGE:
                System.out.println("STATE_DRAWING_EDGE");
                //on vertex or other
                Optional<Vertex> maybeVertex = 
                        model.find(event.getSceneX() +iModel.x, event.getSceneY() +iModel.y);
             
                if (maybeVertex.isPresent()) {
                    Vertex endV = maybeVertex.get();
                    if(currentVertex.id != endV.id)
                        model.addEdge(currentVertex, endV);
                }
                currentVertex = null;
                iModel.setSelected(null);
                break;
            case STATE_VERTEX:
                System.out.println("STATE_VERTEX");
                currentVertex = null;
                iModel.setSelected(null);
                break;
            case STATE_MOVINGBACK:
                System.out.println("STATE_MOVINGBACK");
                model.movingBackground();
                break;
            
        }
        
        state = STATE_READY;
        System.out.println("Ready again!");
    }

}
