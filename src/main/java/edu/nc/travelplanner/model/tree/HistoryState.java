package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.jump.Jump;

import java.util.List;

public class HistoryState {
    private List<PickResult> picks;
    private Jump jastJump;

    public HistoryState(List<PickResult> picks, Jump jastJump) {
        this.picks = picks;
        this.jastJump = jastJump;
    }

    public List<PickResult> getPicks() {
        return picks;
    }

    public void setPicks(List<PickResult> picks) {
        this.picks = picks;
    }

    public Jump getJastJump() {
        return jastJump;
    }

    public void setJastJump(Jump jastJump) {
        this.jastJump = jastJump;
    }
}
