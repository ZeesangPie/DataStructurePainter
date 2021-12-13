package com.paint.paint.Window;

import com.paint.paint.Picture;
import com.paint.paint.Utils.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.List;

@SuppressWarnings("restriction")
public class BlankMenu extends ContextMenu {
    private Picture belongTo;
    double last_x,last_y;

    public BlankMenu(Picture p) {
        belongTo=p;
        EventHandler<ActionEvent> action = null;
        try {
            action = changeTabPlacement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MenuItem newNodeMenuItem = new MenuItem("新建节点");
        newNodeMenuItem.setOnAction(action);

        MenuItem newNodesMenuItem = new MenuItem("批量新建节点");
        newNodesMenuItem.setOnAction(action);

        MenuItem clearMenuItem = new MenuItem("清空");
        clearMenuItem.setOnAction(action);

        MenuItem freshMenuItem = new MenuItem("刷新");
        freshMenuItem.setOnAction(action);

        MenuItem initMenuItem = new MenuItem("导入");
        initMenuItem.setOnAction(action);

        MenuItem outMenuItem = new MenuItem("导出");
        outMenuItem.setOnAction(action);

        if (belongTo.isMain()) getItems().addAll(newNodeMenuItem,newNodesMenuItem,freshMenuItem,clearMenuItem,initMenuItem,outMenuItem);
        else getItems().addAll(newNodeMenuItem,newNodesMenuItem,freshMenuItem,clearMenuItem,outMenuItem);
    }

    public void clickedInsert(double x,double y) {
        last_x=x;
        last_y=y;
    }

    private EventHandler<ActionEvent> changeTabPlacement() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String side = mItem.getText();
                if ("新建节点".equalsIgnoreCase(side)) {
                    System.out.println("新建节点");
                    belongTo.creatNode(last_x, last_y);
                }else if ("批量新建节点".equalsIgnoreCase(side)) {
                    System.out.println("批量新建节点");
                    String init= StringInsert.display("创建节点");
                    if (init==null||init.equals("")) return;
                    List<String> names= List.of(init.split(","));
                    for (int i=0;i<names.size();i++) {
                        belongTo.creatNode(names.get(i),last_x+i*50,last_y);
                    }
                } else if ("刷新".equalsIgnoreCase(side)) {
                    System.out.println("刷新");
                    belongTo.refresh();
                } else if ("清空".equalsIgnoreCase(side)) {
                    System.out.println("清空");
                    belongTo.clear();
                } else if ("导入".equalsIgnoreCase(side)) {
                    System.out.println("导入");
                    try {
                        belongTo.Input(Settings.getInstance().default_save_path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if ("导出".equalsIgnoreCase(side)) {
                    System.out.println("导出");
                    try {
                        belongTo.Output(Settings.getInstance().default_save_path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}