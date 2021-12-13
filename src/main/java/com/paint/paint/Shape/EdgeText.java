package com.paint.paint.Shape;

import com.paint.paint.Item.MyEdge;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EdgeText extends Text {
    MyEdge belongTo;

    public EdgeText(MyEdge e) {
        super();
        belongTo=e;
        InvalidationListener updater = o -> {
            String text=String.valueOf(e.getLength());
            setText(text);
            setFont(Font.font(30));

            Text theText = new Text(getText());
            theText.setFont(getFont());
            double width = theText.getBoundsInLocal().getWidth();
            double height = theText.getBoundsInLocal().getHeight();
            double x=(e.getUNode().getX()+e.getVNode().getX())/2;
            double y=(e.getUNode().getY()+e.getVNode().getY())/2;

            setX(x-width/2);
            setY(y+height/3);
        };

        belongTo.getUNode().getCircle().centerXProperty().addListener(updater);
        belongTo.getVNode().getCircle().centerXProperty().addListener(updater);
        belongTo.getUNode().getCircle().centerYProperty().addListener(updater);
        belongTo.getVNode().getCircle().centerYProperty().addListener(updater);

        updater.invalidated(null);

        this.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent e){
                MouseButton button = e.getButton();
                if (button==MouseButton.SECONDARY) {
                    Node node = e.getPickResult().getIntersectedNode();
                    belongTo.getMenu().show(node,javafx.geometry.Side.BOTTOM, 0, 0);
                }
                e.consume();
            }
        });
    }
}
