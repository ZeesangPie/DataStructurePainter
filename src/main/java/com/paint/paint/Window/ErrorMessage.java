package com.paint.paint.Window;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorMessage {
    public static void display(String error) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label lb=new Label("       "+error+"       ");
        lb.setFont(Font.font(15));

        Button bt=new Button("确定");
        bt.setOnMouseClicked(e -> {
            stage.close();
        });
        bt.setOnKeyPressed(e -> {
            if (e.getCode()== KeyCode.ENTER) {
                stage.close();
            }
        });

        StackPane sp = new StackPane();
        sp.setPrefSize(250,50);
        sp.getChildren().addAll(lb,bt);
        sp.setAlignment(lb, Pos.TOP_CENTER);
        sp.setAlignment(bt,Pos.BOTTOM_CENTER);

        stage.setScene(new Scene(sp));
        stage.setTitle("错误");
        stage.showAndWait();
    }
}
