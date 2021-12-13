package com.paint.paint.Algorithm;

import com.paint.paint.Item.MyEdge;
import com.paint.paint.Item.MyNode;
import com.paint.paint.Picture;
import com.paint.paint.Window.ErrorMessage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TopologicalStage {
    Picture picture;
    Map<String,Integer> to;
    public TopologicalStage(Picture p) throws IOException {
        p.Output("refresh_Save.dat");

        picture = new Picture(new Group(),p.getWidth(),p.getHeight(),false);
        to = new HashMap<>();

        for (MyNode node:p.getNodes()) {
            int cnt=0;
            if (to.get(node.getName())==null) to.put(node.getName(),0);
            for (MyEdge edge:node.getEdges()) {
                if (edge.getUNode()==node) {
                    String vName=edge.getVNode().getName();
                    if (to.get(vName)==null) to.put(vName,0);
                    to.put(vName,to.get(vName)+1);
                }
            }
        }
        MyNode pre=null;
        for (int i=1;i<=p.getNodes().size();i++) {
            String choose=null;
            for (String key:to.keySet()) {
                if (to.get(key)==0) {
                    choose=key;
                    break;
                }
            }
            if (choose!=null) {
                MyNode node=p.findByName(choose);
                for (MyEdge edge:node.getEdges()) {
                    if (edge.getUNode()==node) {
                        String vName=edge.getVNode().getName();
                        to.put(vName,to.get(vName)-1);
                    }
                }
                to.remove(choose);
                MyNode now=picture.creatNode(choose,node.getX(),node.getY(),node.getR(),node.getColor());
                if (pre!=null) {
                    picture.creatEdge(pre,now);
                }
                pre=now;
            } else {
                ErrorMessage.display("该图存在环");
                return;
            }
        }

        picture.setFill(Color.web("aqua"));
        Stage stage = new Stage();
        stage.setScene(picture);
        stage.setTitle("拓扑排序结果");
        stage.showAndWait();

        p.Input("refresh_Save.dat");
    }
}
