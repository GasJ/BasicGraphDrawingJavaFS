/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Monkey
 * Class to store the model
 */
public class GraphModel {
    ArrayList<Vertex> vs; //vertices
    ArrayList<Edge> es; //edges
    ArrayList<GraphModelListener> ss; //subscribers
    int vId; //VERTICES ID
    
    
    public GraphModel() {
        vs = new ArrayList<>();
        es = new ArrayList<>();
        ss = new ArrayList<>();
    }
    
    public void addVertex(float centerX, float centerY){
        vs.add(new Vertex(centerX, centerY, vId));
        vId ++;
        notifySubscribers();
    }
    
    
    
    public void addEdge(Vertex start, Vertex end) {
        es.add(new Edge(start,end));
        notifySubscribers();
    }
    
    void moveVertex(Vertex v, double dx, double dy) {
        v.move(dx, dy);
        notifySubscribers();
    }
    
    void movingBackground(){
        notifySubscribers();
    }
    

    private void notifySubscribers() {
        ss.forEach(sub -> sub.modelChanged());
    }
    
    public void addSubscriber(GraphModelListener aSub) {
        ss.add(aSub);
    }
    
    
    public boolean contains(double x, double y) {
        return vs.stream().anyMatch(v -> v.clicking((float)x, (float)y));
    }
    
    public boolean containsOld(double x, double y) {
        boolean found = false;
        for (Vertex v : vs) {
            if (v.clicking((float)x, (float)y)) {
                found = true;
            }
        }
        return found;
    }
    
    public Optional<Vertex> find(double x, double y) {
        return vs.stream()
                .filter(v -> v.clicking((float)x, (float)y))
                .reduce((v1, v2) -> v2);
    }

    
    
    
}
