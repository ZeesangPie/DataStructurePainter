package com.paint.paint.Algorithm;

import com.paint.paint.Item.MyEdge;
import com.paint.paint.Item.MyNode;
import com.paint.paint.Picture;
import com.paint.paint.Utils.TreePicture;
import com.paint.paint.Window.ErrorMessage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class TreeStage {
    TreePicture tree;
    String vlr,ldr,lrd;
    MyNode r;
    Boolean isBinary;


    static boolean isTree(Picture p,MyNode root) {
        Queue<MyNode> q = new ArrayDeque<>();
        Map<MyNode,Boolean> visit = new HashMap<>();
        Map<MyNode,MyNode> fa = new HashMap<>();

        q.add(root);
        visit.put(root,true);
        while (!q.isEmpty()) {
            MyNode u=q.poll();
            for (MyEdge edge:u.getEdges()) {
                MyNode v= edge.getVNode();
                if (u==v||v==fa.get(u)) continue;
                if (visit.get(v)!=null) return false;
                q.add(v);
                visit.put(v,true);
                fa.put(v,u);
            }
        }
        return p.getNodes().size() == visit.size();
    }

    static boolean isBinaryTree(Picture p,MyNode root) {
        Queue<MyNode> q = new ArrayDeque<>();
        Map<MyNode,MyNode> fa = new HashMap<>();

        q.add(root);
        if (root.getEdges().size()>2) return false;
        while (!q.isEmpty()) {
            MyNode u=q.poll();
            if (u.getEdges().size()>3) return false;
            for (MyEdge edge:u.getEdges()) {
                MyNode v = edge.getVNode();
                if (v==u||fa.get(u)==v) continue;
                q.add(v);
                fa.put(v,u);
            }
        }
        return true;
    }

    private void buildTree(Picture p,MyNode root) {
        Queue<MyNode> q = new ArrayDeque<>();
        Map<MyNode,MyNode> fa = new HashMap<>();

        q.add(root);
        r=tree.creatNode(root.getName(),root.getX(),root.getY(),root.getR(),root.getColor());
        while (!q.isEmpty()) {
            MyNode u=q.poll();
            for (MyEdge edge:u.getEdges()) {
                MyNode v= edge.getVNode();
                if (u==v||fa.get(u)==v) continue;
                tree.creatEdge(tree.findByName(u.getName()),tree.creatNode(v.getName(),v.getX(),v.getY(),v.getR(),v.getColor()));
                q.add(v);
                fa.put(v,u);
            }
        }
    }

    private String getVlr(MyNode u,MyNode fa) {
        String result=u.getName();
        if (fa!=null) result="->"+result;

        for (MyEdge edge:u.getEdges()) {
            MyNode v=edge.getVNode();
            if (v!=fa&&v!=u) result+=getVlr(v,u);
        }
        return result;
    }

    private String getLrd(MyNode u,MyNode fa) {
        String result="";

        for (MyEdge edge:u.getEdges()) {
            MyNode v=edge.getVNode();
            if (v!=fa&&v!=u) result+=getLrd(v,u);
        }
        result+=u.getName();
        if (fa!=null) result+="->";
        return result;
    }

    private String getLdr(MyNode u,MyNode fa) {
        List<MyNode> to=new ArrayList();
        for (MyEdge edge:u.getEdges()) {
            MyNode v= edge.getVNode();
            if (v!=u&&v!=fa) to.add(v);
        }

        String result="";
        if (to.size()==0) result=u.getName()+"->";
        if (to.size()==1) result=getLdr(to.get(0),u)+u.getName()+"->";
        if (to.size()==2) result=getLdr(to.get(0),u)+u.getName()+"->"+getLdr(to.get(1),u);
        if (fa==null) result=result.substring(0,result.length()-2);
        return result;
    }

    public TreeStage(Picture p, MyNode root) throws IOException {
        if (root==null) {
            return;
        }
        p.Output("refresh_Save.dat");
        tree = new TreePicture(new Group(),p.getWidth(),p.getHeight(),false);

        if (!TreeStage.isTree(p,root)) {
            ErrorMessage.display("该图不是以"+root.getName()+"为根的树");
            return ;
        }
        isBinary=TreeStage.isBinaryTree(p,root);

        buildTree(p,root);

        vlr=getVlr(r,null);
        System.out.println(vlr);
        Text t1 = new Text(10,15,"前序遍历:"+vlr);

        lrd=getLrd(r,null);
        System.out.println(lrd);
        Text t2 = new Text(10,35,"后序遍历:"+lrd);

        if (isBinary) {
            ldr = getLdr(r, null);
            System.out.println(ldr);
            Text t3 = new Text(10,55,"中序遍历:"+ldr);
            ((Group)tree.getRoot()).getChildren().add(0,t3);
            tree.reserve++;
        }

        ((Group)tree.getRoot()).getChildren().addAll(0,List.of(t1,t2));
        tree.reserve+=2;

        tree.setFill(Color.web("aqua"));
        Stage stage = new Stage();
        stage.setScene(tree);
        stage.setTitle("树");
        stage.showAndWait();

        System.out.println(isBinary);
    }
}
