package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.source.Response;

public class DirectJump implements Jump {
    private Action current;
    private Action next;

    public DirectJump(Action current, Action next) {
        this.current = current;
        this.next = next;
    }

    @Override
    public Action getCurrent() {
        return current;
    }

    @Override
    public Action getNext() {
        return next;
    }

    @Override
    public boolean canJump(ActionArgs args, Response response) {
        return true;
    }
}
