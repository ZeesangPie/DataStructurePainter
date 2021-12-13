package com.paint.paint.Utils;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class Settings implements Serializable {
    public double default_r;
    public String default_node_color;
    public double default_edge_length;
    public String default_edge_color;
    public String default_save_path;

    public static Settings INSTANCE;

    public Settings() {
        super();
        default_r=30.0;
        default_node_color="blue";
        default_edge_length=1;
        default_edge_color="black";
        default_save_path="a.txt";
    }

    static void Output() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("setting.txt"));
            oos.writeObject(INSTANCE);
            oos.close();
        } catch (Exception e) {

        }
    }

    private static Settings Input() {
        Settings s = new Settings();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("setting.txt"));
            s = (Settings) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("没有配置文件");
        }
        return s;
    }

    public static Settings getInstance() {
        if (INSTANCE==null) {
            INSTANCE = Input();
//            display();
        }
        return INSTANCE;
    }

    public static void display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label lb1 = new Label("默认点颜色:");
        TextField tf1 = new TextField(String.valueOf(INSTANCE.default_node_color));
        Label lb2 = new Label("默认边颜色:");
        TextField tf2 = new TextField(String.valueOf(INSTANCE.default_edge_color));
        Label lb3 = new Label("默认点半径");
        TextField tf3 = new TextField(String.valueOf(INSTANCE.default_r));
        Label lb4 = new Label("默认点边权值:");
        TextField tf4 = new TextField(String.valueOf(INSTANCE.default_edge_length));
        Label lb5 = new Label("存储路径:");
        TextField tf5 = new TextField(String.valueOf(INSTANCE.default_save_path));

        Button b = new Button("确定");
        b.setOnMouseClicked(c -> {
            INSTANCE.default_node_color=tf1.getText();
            INSTANCE.default_edge_color=tf2.getText();
            INSTANCE.default_r=Double.parseDouble(tf3.getText());
            INSTANCE.default_edge_length=Double.parseDouble(tf4.getText());
            INSTANCE.default_save_path=tf5.getText();
            Output();
            stage.close();
        });

        GridPane root = new GridPane();
        root.addRow(0,lb1,tf1);
        root.addRow(1,lb2,tf2);
        root.addRow(2,lb3,tf3);
        root.addRow(3,lb4,tf4);
        root.addRow(4,lb5,tf5);
        root.addRow(5,b);
        Scene scene=new Scene(root,250,140);
        stage.setScene(scene);
        stage.setTitle("设置");
        stage.showAndWait();
    }
}