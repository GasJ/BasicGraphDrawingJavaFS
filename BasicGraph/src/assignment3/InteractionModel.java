/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.util.ArrayList;

/**
 *
 * will use a second publish-subscribe mechanism 
 * to notify views about changes
 * @author Monkey
 */
public class InteractionModel {
    Vertex selected;
    ArrayList<GraphModelListener> subscribers;
    double x, y; //main graph's offx and offy
    double bw, bh;//bigwitdh, bigheight
    double nx, ny, nw, nh;
    double sw, sh;
    
    int miniState;//0 for change main by mini, 1 for being changed by main
    int mainState;//1 is for raise, 0 is for others
    float ex, ey;
    
    public InteractionModel() {
        //selected = null;
        subscribers = new ArrayList<>();
        x = 0;
        y = 0;
    }
    
    public void setSelected(Vertex b) {
        mainState = 0;
        selected = b;
        notifySubscribers();
    }
    
    public void addSubscriber(GraphModelListener aSubscriber) {
        subscribers.add(aSubscriber);
    }

    public void removeSubscriber(GraphModelListener aSubscriber) {
        subscribers.remove(aSubscriber);
    }

    private void notifySubscribers() {
        subscribers.forEach((sub) -> sub.modelChanged());
    }

    void movingBackground(float currentx, float currenty) {
        nx = currentx - nw/2;
        ny = currenty - nh/2;
        if(nx < 0) nx = 0;
        if(ny < 0) ny = 0;
        if(nx > sw - nw) nx = (float) (sw - nw);
        if(ny > sh - ny) ny = (float) (sh - nh);
        
        x = (float)nx/sw * bw;
        y = (float)ny/sh * bh;
        notifySubscribers();
    }

    void setBig(double width, double height) {
        bw = width;
        bh = height;
    }

    void setSmall(double width, double height) {
        sw = width;
        sh = height;
    }

    void setNeg(float nw, float nh) {
        this.nw = nw;
        this.nh = nh;
    }

    void setDrawingV(Vertex currentVertex, double x, double y) {
        mainState = 1;
        selected = currentVertex;
        ex = (float)x;
        ey = (float)y;
        notifySubscribers();
    }

    void setLineEnd(float newX, float newY) {
        ex = newX;
        ey = newY;
        notifySubscribers();
    }

    void moveViewport(float dx, float dy) {
        // viewport moves happen in view coordinates, not model coordinates
        x -= dx;
        y -= dy;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        //System.out.println("c.getWidth() is: " + c.getWidth());
        if (x > bw - 500) x = (float) (bw - 500);
        if (y > bh - 500) y = (float) (bh - 500);
        miniState = 1;
        notifySubscribers();
    }

    void setMiniState(int i) {
        miniState = i;
    }
    
    
}
