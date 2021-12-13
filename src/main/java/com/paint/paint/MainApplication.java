package com.paint.paint;

import com.paint.paint.Utils.MainMenu;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static Picture MainWindow;

    @Override
    public void start(Stage stage) {
        Picture MainWindow = new Picture(new Group(),1000 , 800,true);
        ((Group)MainWindow.getRoot()).getChildren().add(0,MainMenu.getInstance(MainWindow));
        MainWindow.setMain(true);
        MainWindow.reserve=1;
        MainWindow.setFill(null);

        stage.setScene(MainWindow);
        stage.setTitle("数据结构画图辅助");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}