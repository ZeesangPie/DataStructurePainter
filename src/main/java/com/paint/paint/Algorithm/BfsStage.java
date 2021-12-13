package com.paint.paint.Algorithm;

import com.paint.paint.Item.MyEdge;
import com.paint.paint.Item.MyNode;
import com.paint.paint.Picture;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class BfsStage {
    Picture picture;
    Map<String , Boolean> visit;
    Text sequence;

    private void bfs(MyNode st) {
        Queue<MyNode> q = new ArrayDeque();
        q.add(st);
        picture.creatNode(st.getName(),st.getX(),st.getY(),st.getR(),st.getColor());
        visit.put(st.getName(),true);


        while (!q.isEmpty()) {
            MyNode u=q.poll();
            MyNode now = picture.findByName(u.getName());
            for (MyEdge edge:u.getEdges()) {
                if (edge.getVNode()==u) continue;
                MyNode v=edge.getVNode();
                if (visit.get(v.getName())==null|| !visit.get(v.getName())) {
                    q.add(v);
                    picture.creatEdge(now,picture.creatNode(v.getName(),v.getX(),v.getY(),v.getR(),v.getColor()));
                    visit.put(v.getName(),true);
                    sequence.setText(sequence.getText()+"->"+v.getName());
                }
            }
        }
    }

    public BfsStage(Picture p,MyNode st) throws IOException {
        if (st==null) return;
        p.Output("refresh_Save.dat");

        sequence = new Text("遍历顺序："+st.getName());
        sequence.setX(10);
        sequence.setY(15);

        visit=new HashMap<>();
        picture = new Picture(new Group(),p.getWidth(),p.getHeight(),false);
        bfs(st);

        p.Input("refresh_Save.dat");

        picture.setFill(Color.web("aqua"));
        ((Group)picture.getRoot()).getChildren().add(0,sequence);
        picture.reserve++;
        Stage stage = new Stage();
        stage.setScene(picture);
        stage.setTitle("BFS结果");
        stage.showAndWait();
    }
}
