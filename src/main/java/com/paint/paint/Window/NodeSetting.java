package com.paint.paint.Window;

import com.paint.paint.Item.MyNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NodeSetting {
    public static void  display(MyNode n) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label lb1 = new Label("名字:");
        TextField tf1 = new TextField(n.getName());
        Label lb2 = new Label("颜色");
        TextField tf2 = new TextField(n.getColor());
        Label lb3 = new Label("半径");
        TextField tf3 = new TextField(String.valueOf(n.getR()));

        Button b = new Button("确定");
        b.setOnMouseClicked(c -> {
            n.setName(tf1.getText());
            n.setColor(tf2.getText());
            n.setR(Double.valueOf(tf3.getText()));
            stage.close();
        });

        GridPane root = new GridPane();
        root.addRow(0,lb1,tf1);
        root.addRow(1,lb2,tf2);
        root.addRow(2,lb3,tf3);
        root.addRow(3,b);
        Scene scene=new Scene(root,210,95);
        stage.setScene(scene);
        stage.setTitle("设置点"+n.getName());
        stage.showAndWait();
    }
}
