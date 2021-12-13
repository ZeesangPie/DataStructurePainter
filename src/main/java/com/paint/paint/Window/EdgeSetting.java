package com.paint.paint.Window;

import com.paint.paint.Item.MyEdge;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EdgeSetting {
    public static void  display(MyEdge e) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label lb1 = new Label("权值:");
        TextField tf1 = new TextField(String.valueOf(e.getLength()));
        Label lb2 = new Label("颜色");
        TextField tf2 = new TextField(String.valueOf(e.getColor()));

        Button b = new Button("确定");
        b.setOnMouseClicked(c -> {
            e.setLength(Double.valueOf(tf1.getText()));
            e.setColor(tf2.getText());
            stage.close();
        });

        GridPane root = new GridPane();
        root.addRow(0,lb1,tf1);
        root.addRow(1,lb2,tf2);
        root.addRow(2,b);
        Scene scene=new Scene(root,220,75);
        stage.setScene(scene);
        stage.setTitle("设置边<"+e.getUNode().getName()+","+e.getVNode().getName()+">");
        stage.showAndWait();
    }
}
