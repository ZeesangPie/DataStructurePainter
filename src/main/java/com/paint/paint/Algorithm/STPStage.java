package com.paint.paint.Algorithm;

import com.paint.paint.Item.MyEdge;
import com.paint.paint.Item.MyNode;
import com.paint.paint.Picture;
import com.paint.paint.Window.ErrorMessage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class STPStage {
    Picture picture;
    Map<MyNode , MyNode> next;
    List<MyEdge> edges;
    int cnt;

    private MyNode find(MyNode x) {
        if (next.get(x)==x) return x;
        else {
            next.put(x,find(next.get(x)));
            return next.get(x);
        }
    }

    private void kruskal() {
        edges.sort(Comparator.comparingDouble(MyEdge::getLength));
        for (int i=0;i<edges.size();i++) {
            MyNode u=picture.findByName(edges.get(i).getUNode().getName());
            MyNode v=picture.findByName(edges.get(i).getVNode().getName());
            MyNode x=find(u),y=find(v);
            if (x!=y) {
                picture.creatEdge(u,v,edges.get(i).getColor(),edges.get(i).getLength());
                picture.creatEdge(v,u,edges.get(i).getColor(),edges.get(i).getLength());
                next.put(x,y);
                cnt++;
            }
        }
    }

    public STPStage(Picture p) throws IOException {
        p.Output("refresh_Save.dat");

        cnt=0;
        picture = new Picture(new Group(),p.getWidth(),p.getHeight(),false);
        next=new HashMap<>();
        edges=new ArrayList<>();
        for (MyNode node:p.getNodes()) {
            MyNode now = picture.creatNode(node.getName(),node.getX(),node.getY(),node.getR(),node.getColor());
            next.put(now,now);
            for (MyEdge edge:node.getEdges()) if (!edges.contains(edge)) edges.add(edge);
        }
        kruskal();

        p.Input("refresh_Save.dat");

        if (cnt!=next.size()-1) {
            ErrorMessage.display("该图不连通，无法生成最小生成树");
            return;
        }
        picture.setFill(Color.web("aqua"));
        Stage stage = new Stage();
        stage.setScene(picture);
        stage.setTitle("生成树");
        stage.showAndWait();
    }
}
