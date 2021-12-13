package com.paint.paint.Utils;

import com.paint.paint.Picture;
import com.paint.paint.Shape.DistText;
import javafx.collections.ObservableList;
import javafx.scene.Group;

import java.util.HashMap;
import java.util.Map;

public class DijPicture extends Picture {
    public DijPicture(Group group, double width, double height, boolean b) {
        super(group,width,height,b);
    }

    @Override
    public void refresh() {
        Map<String,String> ds=new HashMap<>();

        ObservableList list = ((Group) getRoot()).getChildren();
        for (int i=0;i<list.size();i++) {
            if (list.get(i).getClass() == DistText.class) {
                DistText distText = (DistText) list.get(i);
                ds.put(distText.belongTo.getName(),distText.getText());
            }
        }

        try {
            this.Output("refresh_Save.dat");
            this.Input("refresh_Save.dat");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String key:ds.keySet()) {
            DistText distText = new DistText(findByName(key),Double.parseDouble(ds.get(key).substring(5)));
            list.add(distText);
        }
    }
}
