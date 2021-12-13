package com.paint.paint.Window;

import com.paint.paint.Item.MyEdge;
import com.paint.paint.Picture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class EdgeMenu extends ContextMenu {
    private MyEdge belongTo;

    /** * 私有构造函数 */
    public EdgeMenu(MyEdge edge) {
        belongTo=edge;
        EventHandler<ActionEvent> action = changeTabPlacement();

        MenuItem TranEdgeMenuItem = new MenuItem("新建反边");
        TranEdgeMenuItem.setOnAction(action);

        MenuItem deleteMenuItem = new MenuItem("删除");
        deleteMenuItem.setOnAction(action);

        MenuItem settingMenuItem = new MenuItem("设置边");
        settingMenuItem.setOnAction(action);

        getItems().addAll(TranEdgeMenuItem,deleteMenuItem,settingMenuItem);
    }

    private EventHandler<ActionEvent> changeTabPlacement() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String side = mItem.getText();
                if ("新建反边".equalsIgnoreCase(side)) {
                    System.out.println("新建反边");
                    Picture p=belongTo.getUNode().getBelongTo();
                    MyEdge e=p.creatEdge(belongTo.getVNode(),belongTo.getUNode());
                    if (e!=null) {
                        e.setColor(belongTo.getColor());
                        e.setLength(belongTo.getLength());
                    }
                } else if ("删除".equalsIgnoreCase(side)) {
                    System.out.println("删除");
                    belongTo.remove();
                } else if ("设置边".equalsIgnoreCase(side)) {
                    System.out.println("设置边");
                    EdgeSetting.display(belongTo);
                    belongTo.getUNode().getBelongTo().refresh();
                }
            }
        };
    }
}
