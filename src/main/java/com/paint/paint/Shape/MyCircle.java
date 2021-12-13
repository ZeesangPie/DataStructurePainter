package com.paint.paint.Shape;

import com.paint.paint.Item.MyNode;
import com.paint.paint.Window.NodeMenu;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

public class MyCircle extends Circle {
    final private MyNode belongTo;
    private NodeMenu menu;

    public MyNode getBelongTo() {
        return belongTo;
    }

    public MyCircle(MyNode node) {
        super(node.getX(),node.getY(),node.getR());
        this.setFill(Color.web(node.getColor()));
        this.setStroke(Color.web("Black"));
        this.belongTo=node;
        this.menu=new NodeMenu(this);

        this.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent e){
                MouseButton button = e.getButton();
                if (button==MouseButton.SECONDARY) {
                    Node node = e.getPickResult().getIntersectedNode();
                    menu.show(node,javafx.geometry.Side.BOTTOM, 0, 0);
                }
                e.consume();
            }
        });
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                MyNode n= MyCircle.this.belongTo;
                n.setX(e.getSceneX());
                n.setY(e.getSceneY());
            }
        });
    }

    public NodeMenu getMenu() {
        return menu;
    }
}