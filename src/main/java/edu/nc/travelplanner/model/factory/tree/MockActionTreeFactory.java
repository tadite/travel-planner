package edu.nc.travelplanner.model.factory.tree;

import edu.nc.travelplanner.model.tree.ActionTree;
import org.springframework.stereotype.Component;

import java.util.HashMap;

public class MockActionTreeFactory implements ActionTreeFactory {

    private HashMap<String, ActionTree> treeHashMap = new HashMap<>();
    {


    }

    @Override
    public ActionTree createByName(String name) {
        return treeHashMap.get(name);
    }
}
