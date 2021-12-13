package com.paint.paint.Shape;

import com.paint.paint.Item.MyEdge;
import com.paint.paint.Window.EdgeMenu;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyArrow extends Group implements Cloneable{
    MyEdge belongTo;
    public List lines;

    public MyArrow(MyEdge e) {
        this(new Line() , new Line(), new Line() , e);
    }

    public MyArrow(Line line, Line arrow1, Line arrow2, MyEdge e) {
        super(line,arrow1,arrow2);

        lines = List.of(line,arrow1,arrow2);

        line.startXProperty().bind(e.getUNode().getCircle().centerXProperty());
        line.startYProperty().bind(e.getUNode().getCircle().centerYProperty());
        line.endXProperty().bind(e.getVNode().getCircle().centerXProperty());
        line.endYProperty().bind(e.getVNode().getCircle().centerYProperty());
        belongTo=e;
        line.setStrokeWidth(5);
        line.setStroke(Color.web(e.getColor()));

        InvalidationListener updater = o -> {
            double ex = line.getEndX();
            double ey = line.getEndY();
            double sx = line.getStartX();
            double sy = line.getStartY();

            double factor = (e.getVNode().getR()+15) / Math.hypot(sx-ex, sy-ey);
            double factorO = 5 / Math.hypot(sx-ex, sy-ey);

            double dx = (sx - ex) * factor;
            double dy = (sy - ey) * factor;
            double ox = (sx - ex) * factorO;
            double oy = (sy - ey) * factorO;

            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);
            arrow1.setStartX(ex + dx - oy);
            arrow1.setStartY(ey + dy + ox);
            arrow2.setStartX(ex + dx + oy);
            arrow2.setStartY(ey + dy - ox);
            arrow1.setStrokeWidth(5);
            arrow2.setStrokeWidth(5);

            arrow1.strokeProperty().bind(line.strokeProperty());
            arrow2.strokeProperty().bind(line.strokeProperty());
            arrow1.onMousePressedProperty().bind(line.onMouseClickedProperty());
            arrow2.onMousePressedProperty().bind(line.onMouseClickedProperty());
        };
        line.startXProperty().addListener(updater);
        line.startYProperty().addListener(updater);
        line.endXProperty().addListener(updater);
        line.endYProperty().addListener(updater);

        updater.invalidated(null);

        line.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent e){
                MouseButton button = e.getButton();
                if (button==MouseButton.SECONDARY) {
                    Node node = e.getPickResult().getIntersectedNode();
                    belongTo.getMenu().show(node, e.getScreenX(),e.getScreenY());
                }
                e.consume();
            }
        });
    }
}
