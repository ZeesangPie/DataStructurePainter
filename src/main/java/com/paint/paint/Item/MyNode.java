package com.paint.paint.Item;

import com.paint.paint.Picture;
import com.paint.paint.Shape.CircleText;
import com.paint.paint.Shape.MyCircle;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MyNode {
    String name;
    double x,y,r;
    String color;
    List<MyEdge> edges;

    MyCircle circle;
    CircleText text;
    Picture belongTo;

    public MyNode(Picture p, String name, double x, double y,double r,String c) {
        this.name=name;
        this.x=x;
        this.y=y;
        this.r=r;
        this.belongTo=p;
        this.color=c;
        circle=new MyCircle(this);
        text=new CircleText(this);
        edges=new ArrayList<>();
    }

    public void remove() {
        while (edges.size()!=0) {
            edges.get(0).remove();
        }
    }

    public List<MyEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<MyEdge> edges) {
        this.edges = edges;
    }

    public Picture getBelongTo() {
        return belongTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        circle.setCenterX(x);
        this.x = x;
    }

    public double getY() { return y; }

    public void setY(double y) {
        circle.setCenterY(y);
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        circle.setRadius(r);
        this.r = r;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        try {
            circle.setFill(Color.web(color));
            this.color = color;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MyCircle getCircle() {
        return circle;
    }

    public void setCircle(MyCircle circle) {
        this.circle = circle;
    }

    public void setBelongTo(Picture belongTo) {
        this.belongTo = belongTo;
    }

    public CircleText getText() {
        return text;
    }

    public void setText(CircleText text) {
        this.text = text;
    }

}
