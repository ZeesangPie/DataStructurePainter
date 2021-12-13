package com.paint.paint.Shape;

import com.paint.paint.Item.MyNode;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DistText extends Text {
    public MyNode belongTo;
    public DistText(MyNode node,double dist) {
        super();
        belongTo=node;
        InvalidationListener updater = o -> {
            setText("dist="+dist);
            setFont(Font.font(belongTo.getR()));

            Text theText = new Text(getText());
            theText.setFont(getFont());
            double width = theText.getBoundsInLocal().getWidth();
            double height = theText.getBoundsInLocal().getHeight();

            setX(belongTo.getX()-width/2);
            setY(belongTo.getY()-height/3);
        };
        belongTo.getCircle().centerXProperty().addListener(updater);
        belongTo.getCircle().centerYProperty().addListener(updater);
        belongTo.getCircle().radiusProperty().addListener(updater);

        updater.invalidated(null);
        setFont(Font.font(belongTo.getR()));

        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                MyNode n= belongTo;
                n.setX(e.getSceneX());
                n.setY(e.getSceneY());
            }
        });
        this.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent e){
                MouseButton button = e.getButton();
                if (button==MouseButton.SECONDARY) {
                    Node node = e.getPickResult().getIntersectedNode();
                    belongTo.getCircle().getMenu().show(node,javafx.geometry.Side.BOTTOM, 0, 0);
                }
                e.consume();
            }
        });
    }
}
