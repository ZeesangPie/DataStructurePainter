package com.paint.paint.Utils;

import com.paint.paint.Item.MyEdge;
import com.paint.paint.Item.MyNode;
import com.paint.paint.Picture;
import com.paint.paint.Window.ErrorMessage;
import javafx.scene.Group;

public class TreePicture extends Picture {

    public TreePicture(Group group, double height, double width, boolean b) {
        super(group,height,width,b);
    }

    @Override
    public MyEdge creatEdge(MyNode u, MyNode v, String color, double length) {
        if (u==v) {
            System.out.println("不能与自己相连");
            ErrorMessage.display("不能与自己相连");
            return null;
        }
        for (MyEdge edge:u.getEdges()) {
            if (edge.getVNode()==v) {
                System.out.println("边已存在");
                return null;
            }
        }
        MyEdge tr=null;
        for (MyEdge edge:v.getEdges()) {
            if (edge.getVNode()==u) {
                tr=edge;
            }
        }
        MyEdge e=new MyEdge(u,v,color,length);
        if (tr!=null) tr.tran=e;
        e.tran=tr;
        Group g = (Group) this.getRoot();
        g.getChildren().addAll(reserve,e.getLine().getChildren());
//        g.getChildren().add(reserve+1,e.getText());
        return e;
    }
}
