package com.paint.paint.Item;

import com.paint.paint.Picture;
import com.paint.paint.Shape.EdgeText;
import com.paint.paint.Shape.MyArrow;
import com.paint.paint.Window.EdgeMenu;
import javafx.scene.paint.Color;

public class MyEdge {
    MyNode uNode,vNode;
    double length;
    String color;
    MyArrow line;
    EdgeText text;
    EdgeMenu menu;
    public MyEdge tran;

    public MyEdge(MyNode u,MyNode v) {
        this(u,v,"Black",1);
    }
    public MyEdge(MyNode u,MyNode v,String c,double l) {
        uNode=u;
        vNode=v;
        length=l;
        menu=new EdgeMenu(this);
        color=c;
        line=new MyArrow(this);
        u.getEdges().add(this);
        v.getEdges().add(this);

        text=new EdgeText(this);
    }

    public void remove() {
        Picture.deleteEdge(this);
        uNode.getEdges().remove(this);
        vNode.getEdges().remove(this);
    }

    public EdgeMenu getMenu() {
        return menu;
    }

    public void setMenu(EdgeMenu menu) {
        this.menu = menu;
    }

    public EdgeText getText() {
        return text;
    }

    public void setText(EdgeText text) {
        this.text = text;
    }

    public MyNode getUNode() {
        return uNode;
    }

    public void setUNode(MyNode uNode) {
        this.uNode = uNode;
    }

    public MyNode getVNode() {
        return vNode;
    }

    public void setVNode(MyNode vNode) {
        this.vNode = vNode;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        if (tran!=null) tran.length=length;
        this.length = length;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        try {
            Color.web(color);
            this.color = color;
            if (tran!=null) tran.color = color;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MyArrow getLine() {
        return line;
    }

    public void setLine(MyArrow line) {
        this.line = line;
    }

    public MyNode getuNode() {
        return uNode;
    }

    public void setuNode(MyNode uNode) {
        this.uNode = uNode;
    }

    public MyNode getvNode() {
        return vNode;
    }

    public void setvNode(MyNode vNode) {
        this.vNode = vNode;
    }

    public MyEdge getTran() {
        return tran;
    }

    public void setTran(MyEdge tran) {
        this.tran = tran;
    }
}
