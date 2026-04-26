package org.risingDragon;

import org.risingDragon.tree.ANode;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> bindings;
    private final Environment parent;

    public Environment (Environment parent) {
        this.bindings = new HashMap<>();
        this.parent = parent;
    }

    public Object lookUp(final ANode<?> node) {
        Object value = node.getValue().getFirst();
        if (bindings.containsKey(value.toString()))
            return bindings.get(value.toString());
        if (parent != null)
            return parent.lookUp(node);
        return value;
    }

    public void define(String key, Object value) {
        bindings.put(key, value);
    }
}
