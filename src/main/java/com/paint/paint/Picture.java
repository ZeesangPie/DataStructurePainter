package com.paint.paint;

import com.paint.paint.Item.MyEdge;
import com.paint.paint.Item.MyNode;
import com.paint.paint.Shape.CircleText;
import com.paint.paint.Shape.EdgeText;
import com.paint.paint.Shape.MyArrow;
import com.paint.paint.Shape.MyCircle;
import com.paint.paint.Utils.MainMenu;
import com.paint.paint.Utils.Settings;
import com.paint.paint.Window.BlankMenu;
import com.paint.paint.Window.ErrorMessage;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Picture extends Scene{
    protected List<MyNode> nodes;
    BlankMenu menu;
    protected int nameTop;
    boolean isMain;
    public int reserve;

    public Picture() {
        super(new Group());
    }

    public Picture(Parent g, double x, double y,boolean isMain) {
        super(g,x,y);
        setMain(isMain);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Node node = e.getPickResult().getIntersectedNode();
                if (node!=null) return;
                switch (e.getButton()) {
                    case SECONDARY: {
                        Window w=Picture.super.getWindow();
                        menu.clickedInsert(e.getX(), e.getY());
                        menu.show(w,e.getSceneX()+w.getX(), e.getSceneY()+w.getY());
                        break;
                    }
                }
            }
        });

        nameTop=0;
        reserve=0;
        nodes= new ArrayList<>();
        menu=new BlankMenu(this);
    }

    public MyNode creatNode(String name,double x,double y,double r,String c) {
        if (existByName(name)) {
            System.out.println(name+"已存在");
            return null;
        }
        MyNode node= new MyNode(this,name,x,y,r,c);
        nodes.add(node);
        Group g = (Group) this.getRoot();
        g.getChildren().add(node.getCircle());
        g.getChildren().add(node.getText());

        return node;
    }

    public MyNode creatNode(String name,double x,double y) {
        return creatNode(name,x,y, Settings.getInstance().default_r,Settings.getInstance().default_node_color);
    }

    public MyNode creatNode(double x,double y) {
        while (existByName(String.valueOf(++nameTop)));
        return creatNode(String.valueOf(nameTop),x,y);
    }

    public void clear() {
        nodes.clear();
        nameTop=0;
        Group g = (Group) getRoot();
        int pos=g.getChildren().size()-1;
        while (pos>=reserve) {
//            if (g.getChildren().get(pos).getClass() == CircleText.class || g.getChildren().get(pos).getClass() == MyCircle.class || g.getChildren().get(pos).getClass() == EdgeText.class || g.getChildren().get(pos).getClass() == Line.class) {
                g.getChildren().remove( pos );
//            }
            pos--;
        }
    }

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
        g.getChildren().add(reserve+1,e.getText());
        return e;
    }

    public MyEdge creatEdge(MyNode u,MyNode v) {
        return creatEdge(u,v,Settings.getInstance().default_edge_color,Settings.getInstance().default_edge_length);
    }

    public MyEdge creatEdge(MyNode u,String vName) {
        MyNode v=findByName(vName);
        if (v==null) {
            System.out.println("找不到点");
            return null;
        }
        return creatEdge(u,v);
    }

    private boolean existByName(String name) {
        Iterator<MyNode> it = nodes.iterator();
        while (it.hasNext()) {
            MyNode now = it.next();
            if (now.getName().equals(name)) return true;
        }
        return false;
    }

    public MyNode findByName(String name) {
        Iterator<MyNode> it = nodes.iterator();
        while (it.hasNext()) {
            MyNode now = it.next();
            if (now.getName().equals(name)) return now;
        }
        return null;
    }

    public static void deleteNode(MyNode node) {
        Picture p=node.getBelongTo();
        Group g=(Group) p.getRoot();
        node.remove();
        g.getChildren().removeAll(node.getCircle());
        g.getChildren().removeAll(node.getText());
        p.nodes.remove(node);
    }

    public static void deleteEdge(MyEdge edge) {
        Picture p=edge.getUNode().getBelongTo();
        Group g = (Group) p.getRoot();
        g.getChildren().removeAll(edge.getLine().lines);
        g.getChildren().removeAll(edge.getText());
    }

    public void Output(String fileName) throws IOException{
        String sp="==";

        OutputStream os = new FileOutputStream(fileName);
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os));
        String line;
        for (MyNode node:nodes) {
            line="N"+sp;
            line+=node.getName()+sp+node.getX()+sp+node.getY()+sp+node.getR()+sp+node.getColor()+"\n";
            bw.write(line);
            for (MyEdge edge:node.getEdges()) {
                if (edge.getUNode()!=node) continue;
                line="E"+sp;
                line+=edge.getUNode().getName()+sp+edge.getVNode().getName()+sp+edge.getLength()+sp+edge.getColor()+"\n";
                bw.write(line);
            }
        }
        bw.close();
        os.close();
    }

    public void Input(String fileName) throws IOException{
        String sp="==";

        this.clear();
        InputStream is = new FileInputStream(fileName);
        String line;
        List<String> edges = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine();
        while (line!=null) {
            if (line.charAt(0)=='E') {
                edges.add(line);
            } else {
                String data[]=line.split(sp);
                String name=data[1];
                double x=Double.parseDouble(data[2]);
                double y=Double.parseDouble(data[3]);
                double r=Double.parseDouble(data[4]);
                if (x<0) x=r;
                if (y<0) y=r;
                String color=data[5];

                creatNode(name,x,y,r,color);
            }
            line = reader.readLine();
        }
        for (String edgeString:edges) {
            String data[]=edgeString.split(sp);
            MyNode u=findByName(data[1]);
            MyNode v=findByName(data[2]);
            double length=Double.parseDouble(data[3]);
            String color=data[4];
            if (u==null||v==null) continue;

            creatEdge(u,v,color,length);
        }
        reader.close();
        is.close();
    }

    public void refresh() {
        try {
            this.Output("refresh_Save.dat");
            this.Input("refresh_Save.dat");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MyNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<MyNode> nodes) {
        this.nodes = nodes;
    }

    public BlankMenu getMenu() {
        return menu;
    }

    public void setMenu(BlankMenu menu) {
        this.menu = menu;
    }

    public int getNameTop() {
        return nameTop;
    }

    public void setNameTop(int nameTop) {
        this.nameTop = nameTop;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}