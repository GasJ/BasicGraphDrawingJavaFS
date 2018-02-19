/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import javafx.scene.input.MouseEvent;

/**
 *
 * @author Monkey
 */
public class MiniViewController {
    GraphModel model;
    InteractionModel im;
    
    int state;
    final int STATE_READY = 0;
    final int STATE_DRAGGING = 1;
    final int STATE_CLICKED = 2;
    
    float currentx, currenty, prevX, prevY, dx, dy;
    
    public MiniViewController(){
        
    }
    
    public void setModel(GraphModel aModel) {
        model = aModel;
    }
    
    public void setInteractionModel(InteractionModel anIModel) {
        im = anIModel;
    }
    
    
    public void handleMousePressed(MouseEvent event){
        prevX = currentx;
        prevY = currenty;
        currentx = (float) event.getX();
        currenty = (float) event.getY();
        switch (state){
            case STATE_READY:
                //do nothing
        }
    }
    
    public void handleMouseDragged(MouseEvent event){
        switch (state){
            case STATE_READY:
                state = STATE_DRAGGING;
            case STATE_DRAGGING:
                im.setMiniState(0);
                im.movingBackground((float) event.getX(), (float) event.getY());
                state = STATE_DRAGGING;
                break;
        }
        
    }
    
    
    public void handleMouseReleased(MouseEvent event){
        switch (state){
            case STATE_READY:
                state = STATE_CLICKED;
            case STATE_CLICKED:
                im.setMiniState(0);
                im.movingBackground(currentx, currenty);
        }
        
        im.setMiniState(1);
        state = STATE_READY;
    }
}
