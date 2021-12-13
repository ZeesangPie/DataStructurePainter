package com.paint.paint.Window;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StringInsert {
    private static String name;
    public static String display(String message) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label init = new Label(message+":");
        TextField tf = new TextField();
        Button b = new Button("确定");
        b.setOnMouseClicked(e -> {
            name=tf.getText();
            stage.close();
        });
        tf.setOnKeyPressed(e -> {
            if (e.getCode()== KeyCode.ENTER) {
                name=tf.getText();
                stage.close();
            }
        });

        GridPane root = new GridPane();
        root.addRow(0,init,tf,b);
        Scene scene=new Scene(root,270,35);
        stage.setScene(scene);
        stage.setTitle("输入");
        stage.showAndWait();

        return name;
    }
}
