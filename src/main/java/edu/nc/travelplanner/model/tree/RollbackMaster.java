package edu.nc.travelplanner.model.tree;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayDeque;
import java.util.Deque;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class RollbackMaster {
    private Deque<HistoryState> stateHistory = new ArrayDeque<>();

    public HistoryState rollback(){
        return stateHistory.pop();
    }

    public void addStep(HistoryState step){
        this.stateHistory.push(step);
    }

    public boolean isEmpty(){
        return stateHistory.isEmpty();
    }
}
