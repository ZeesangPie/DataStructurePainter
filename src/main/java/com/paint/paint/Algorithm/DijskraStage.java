package com.paint.paint.Algorithm;

import com.paint.paint.Item.MyEdge;
import com.paint.paint.Item.MyNode;
import com.paint.paint.Picture;
import com.paint.paint.Shape.DistText;
import com.paint.paint.Utils.DijPicture;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DijskraStage {
    DijPicture picture;
    Map<String , Boolean> visit;
    Map<String , Double> dist;
    Map<MyNode , MyEdge> pre;

    public DijskraStage(Picture p, MyNode st) throws IOException {
        if (st==null) return;
        p.Output("refresh_Save.dat");

//        sequence = new Text("遍历顺序："+st.getName());
//        sequence.setX(10);
//        sequence.setY(10);

        visit=new HashMap<>();
        dist=new HashMap<>();
        pre=new HashMap<>();
        picture = new DijPicture(new Group(),p.getWidth(),p.getHeight(),false);
        dijskra(p,st);

        p.Input("refresh_Save.dat");

        picture.setFill(Color.web("aqua"));
//        ((Group)picture.getRoot()).getChildren().add(0,sequence);
        Stage stage = new Stage();
        stage.setScene(picture);
        stage.setTitle("最短路结果");
        stage.showAndWait();
    }

    private void dijskra(Picture p, MyNode st) {
        dist.put(st.getName(), (double) 0);
        picture.creatNode(st.getName(),st.getX(),st.getY(),st.getR(),st.getColor());

        while (dist.size()>visit.size()) {
            double min_d=Double.MAX_VALUE;
            String min_name="";
            for (String key:dist.keySet()) {
                if (visit.get(key)==null&&dist.get(key)<=min_d) {
                    min_d=dist.get(key);
                    min_name=key;
                }
            }

            MyNode u=p.findByName(min_name);
            visit.put(u.getName(), true);
            for (MyEdge edge:u.getEdges()) {
                MyNode v=edge.getVNode();
                if (u!=v) {
                    double d;
                    if (dist.get(v.getName())==null) {
                        d=dist.get(u.getName())+edge.getLength();
                        pre.put(picture.creatNode(v.getName(),v.getX(),v.getY(),v.getR(),v.getColor()),edge);
                    } else if (dist.get(v.getName())>dist.get(u.getName())+edge.getLength()) {
                        d=dist.get(u.getName())+edge.getLength();
                        pre.put(picture.findByName(v.getName()),edge);
                    } else {
                        d=dist.get(v.getName());
                    }
                    dist.put(v.getName(),d);
                }
            }
        }

        DistText distText = new DistText(picture.findByName(st.getName()),0);
        ((Group)picture.getRoot()).getChildren().add(distText);
//        picture.reserve++;

        for (MyNode v:pre.keySet()) {
            MyEdge edge=pre.get(v);
            MyNode u=picture.findByName(edge.getUNode().getName());
            picture.creatEdge(u,v,"yellow",edge.getLength());

            distText = new DistText(v,dist.get(v.getName()));
            ((Group)picture.getRoot()).getChildren().add(distText);
//            picture.reserve++;
        }
    }
}
