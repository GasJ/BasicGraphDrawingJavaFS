/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import static com.sun.javafx.geom.Point2D.distance;

/**
 *
 * @author Monkey
 * Represents a vertex in the graph
 */
public class Vertex {
    float centreX,centreY,radius;
    int id;
    
    public Vertex(float X, float Y, int ID){
        centreX = X;
        centreY = Y;
        radius = 20f;
        id = ID;
    }
    
    public boolean clicking(float currentX, float currentY){
        //determine whether the vertex is clicking
        
        // r > Math.sqrt(Math.pow(sx - x, 2) + Math.pow(sy - y, 2));
        //if(distance(centreX,centreX,currentX,currentY) <= radius){
        if (radius > Math.sqrt(Math.pow(currentX - centreX, 2) + Math.pow(currentY - centreY, 2))){
            //System.out.println("you are clicking a vertex");
         
            return true;
        }
        //System.out.println("you are not clicking a vertex");
        return false;
    }
    
    public void move(double dx, double dy) {
        centreX += dx;
        centreY += dy;
    }
}
