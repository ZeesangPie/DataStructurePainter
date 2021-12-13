package com.paint.paint.Utils;

import com.paint.paint.Algorithm.*;
import com.paint.paint.Item.MyNode;
import com.paint.paint.Picture;
import com.paint.paint.Window.StringInsert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class MainMenu extends MenuBar {
    static MainMenu INSTANCE;

    static public MainMenu getInstance(Picture p) {
        if (INSTANCE==null) {
            INSTANCE=new MainMenu(p);
        }
        return INSTANCE;
    }
    private MainMenu(Picture p) {
        super();
        Menu me1 = new Menu("文件");
        Menu me2 = new Menu("设置");
        Menu me3 = new Menu("算法");
        Menu me4 = new Menu("帮助");
        getMenus().addAll(me1, me2, me3,me4);

        setPrefWidth(p.getWidth());
        p.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                MainMenu.this.setPrefWidth(t1.doubleValue());
            }
        });

//        设置
        MenuItem m21 = new MenuItem("打开设置");
        m21.setOnAction(e -> {
            System.out.println("打开设置");
            Settings.getInstance();
            Settings.display();
        });
        MenuItem m22 = new MenuItem("重置设置");
        m22.setOnAction(e -> {
            System.out.println("重置设置");
            Settings.getInstance();
            Settings.INSTANCE=new Settings();
        });
        me2.getItems().addAll(m21,m22);

//        文件
        MenuItem m11 = new MenuItem("打开");
        m11.setOnAction(e -> {
            System.out.println("导入");
            try {
                p.Input(StringInsert.display("输入文件名"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem m12 = new MenuItem("保存");
        m12.setOnAction(e -> {
            System.out.println("导出");
            try {
                p.Output(StringInsert.display("输入文件名"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem m13 = new MenuItem("关闭");
        m13.setOnAction(e -> {
            System.out.println("关闭");
            exit();
        });
        me1.getItems().addAll(m11,m12,m13);

//        算法
        MenuItem m31 = new MenuItem("深度优先搜索");
        m31.setOnAction(e -> {
            System.out.println("深搜");
            try {
                MyNode st = p.findByName(StringInsert.display("输入起始点"));
                DfsStage ds = new DfsStage(p,st);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem m32 = new MenuItem("广度优先搜索");
        m32.setOnAction(e -> {
            try {
                System.out.println("广搜");
                MyNode st = p.findByName(StringInsert.display("输入起始点"));
                BfsStage bs = new BfsStage(p,st);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem m33 = new MenuItem("Dijskra求最短路");
        m33.setOnAction(e -> {
            try {
                System.out.println("dij");
                MyNode st = p.findByName(StringInsert.display("输入起始点"));
                DijskraStage ds = new DijskraStage(p,st);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem m34 = new MenuItem("生成树");
        m34.setOnAction(e -> {
            try {
                System.out.println("生成树");
                STPStage ss = new STPStage(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem m35 = new MenuItem("拓扑排序");
        m35.setOnAction(e -> {
            try {
                System.out.println("拓扑排序");
                TopologicalStage ts = new TopologicalStage(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem m36 = new MenuItem("树的遍历");
        m36.setOnAction(e -> {
            try {
                System.out.println("树的遍历");
                TreeStage ts=new TreeStage(p,p.findByName(StringInsert.display("输入根")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        me3.getItems().addAll(m31,m32,m33,m34,m35,m36);
    }
}
