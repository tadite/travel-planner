package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.DirectJump;
import edu.nc.travelplanner.model.action.InfoAction;
import edu.nc.travelplanner.model.action.Jump;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
public class MockActionTreeFactory implements ActionTreeFactory {

    private HashMap<String, ActionTree> treeHashMap = new HashMap<>();

    {
        List<Jump> jumpList = new LinkedList<>();
        InfoAction actionHead = new InfoAction("info1-info-node","data-pres-1");
        InfoAction actionSecond = new InfoAction("info2-info-node","data-pres-2");
        InfoAction actionThree = new InfoAction("info3-info-node","data-pres-3");

        actionHead.addJump(new DirectJump(actionHead,actionSecond));
        actionSecond.addJump(new DirectJump(actionSecond,actionThree));

        treeHashMap.put("test-tree", new SimpleActionTree("test-tree",actionHead));
    }

    @Override
    public ActionTree createByName(String name) {
        return treeHashMap.get(name);
    }
}
