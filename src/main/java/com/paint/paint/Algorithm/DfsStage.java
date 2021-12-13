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
import java.util.HashMap;
import java.util.Map;

public class DfsStage {
    Picture picture;
    Map<String , Boolean> visit;
    Text sequence;

    private void dfs(MyNode u, MyEdge pre, MyNode fa) {

        ObservableList<Node> l = ((Group) picture.getRoot()).getChildren();
        MyNode now = picture.creatNode(u.getName(),u.getX(),u.getY(),u.getR(),u.getColor());

        if (pre!=null) {
            sequence.setText(sequence.getText()+"->"+u.getName());
            picture.creatEdge(fa, now , pre.getColor() , pre.getLength());
        }
        visit.put(u.getName(),true);

        for (MyEdge edge:u.getEdges()) {
            MyNode v=edge.getVNode();
            if (visit.get(v.getName())==null|| !visit.get(v.getName())) {
                dfs(v,edge,now);
            }
        }
    }

    public DfsStage(Picture p,MyNode st) throws IOException {
        if (st==null) return;
        p.Output("refresh_Save.dat");

        sequence = new Text("遍历顺序："+st.getName());
        sequence.setX(10);
        sequence.setY(15);

        visit=new HashMap<>();
        picture = new Picture(new Group(),p.getWidth(),p.getHeight(),false);
        dfs(st,null,null);

        p.Input("refresh_Save.dat");

        picture.setFill(Color.web("aqua"));
        ((Group)picture.getRoot()).getChildren().add(0,sequence);
        Stage stage = new Stage();
        stage.setScene(picture);
        stage.setTitle("DFS结果");
        stage.showAndWait();
    }
}
