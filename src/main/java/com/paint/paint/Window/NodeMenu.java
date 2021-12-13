package com.paint.paint.Window;

import com.paint.paint.Algorithm.BfsStage;
import com.paint.paint.Algorithm.DfsStage;
import com.paint.paint.Algorithm.DijskraStage;
import com.paint.paint.Algorithm.TreeStage;
import com.paint.paint.Item.MyNode;
import com.paint.paint.Shape.MyCircle;
import com.paint.paint.Picture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.List;

public class NodeMenu extends ContextMenu {
    private MyCircle belongTo;

    /** * 私有构造函数 */
    public NodeMenu(MyCircle node) {
        belongTo=node;
        EventHandler<ActionEvent> action = changeTabPlacement();

        MenuItem newEdgeMenuItem = new MenuItem("新建边");
        newEdgeMenuItem.setOnAction(action);

        MenuItem deleteMenuItem = new MenuItem("删除");
        deleteMenuItem.setOnAction(action);

        MenuItem settingMenuItem = new MenuItem("设置点");
        settingMenuItem.setOnAction(action);

        Menu algorithmMenu = new Menu("算法");
        MenuItem bfsMenuItem = new MenuItem("以选中点开始广搜");
        bfsMenuItem.setOnAction(action);

        MenuItem dfsMenuItem = new MenuItem("以选中点开始深搜");
        dfsMenuItem.setOnAction(action);

        MenuItem dijskraMenuItem = new MenuItem("以选中点为源点进行最短路");
        dijskraMenuItem.setOnAction(action);

        MenuItem treeMenuItem = new MenuItem("以选中点为根进行树的遍历");
        treeMenuItem.setOnAction(action);
        algorithmMenu.getItems().addAll(dfsMenuItem,bfsMenuItem,dijskraMenuItem,treeMenuItem);

        getItems().addAll(newEdgeMenuItem,deleteMenuItem,settingMenuItem,algorithmMenu);
    }

    private EventHandler<ActionEvent> changeTabPlacement() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String side = mItem.getText();
                if ("新建边".equalsIgnoreCase(side)) {
                    System.out.println("新建边");
                    MyNode n= belongTo.getBelongTo();
                    String init= StringInsert.display("连接节点");
                    List<String> names= List.of(init.split(","));
                    for (String name:names) {
                        n.getBelongTo().creatEdge(n,name);
                    }
                } else if ("删除".equalsIgnoreCase(side)) {
                    System.out.println("删除");
                    Picture.deleteNode(belongTo.getBelongTo());
                } else if ("以选中点开始广搜".equalsIgnoreCase(side)) {
                    System.out.println("以选中点开始广搜");
                    try {
                        BfsStage bfsStage = new BfsStage(belongTo.getBelongTo().getBelongTo(),belongTo.getBelongTo());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if ("以选中点开始深搜".equalsIgnoreCase(side)) {
                    System.out.println("以选中点开始深搜");
                    try {
                        DfsStage dfsStage = new DfsStage(belongTo.getBelongTo().getBelongTo(),belongTo.getBelongTo());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if ("以选中点为源点进行最短路".equalsIgnoreCase(side)) {
                    System.out.println("以选中点为源点进行最短路");
                    try {
                        DijskraStage dijskraStage = new DijskraStage(belongTo.getBelongTo().getBelongTo(),belongTo.getBelongTo());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if ("以选中点为根进行树的遍历".equalsIgnoreCase(side)) {
                    System.out.println("以选中点为根进行树的遍历");
                    try {
                        TreeStage treeStage = new TreeStage(belongTo.getBelongTo().getBelongTo(),belongTo.getBelongTo());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if ("设置点".equalsIgnoreCase(side)) {
                    System.out.println("设置点");
                    NodeSetting.display(belongTo.getBelongTo());
                    belongTo.getBelongTo().getBelongTo().refresh();
                }
            }
        };
    }
}