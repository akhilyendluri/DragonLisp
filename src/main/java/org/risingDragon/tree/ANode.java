package org.risingDragon.tree;

import java.util.Collections;
import java.util.List;

public class ANode<T> extends BaseNode {
    private final T data;
    private final AType type;

    public ANode(T data, AType type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public List<T> getValue() {
        return Collections.singletonList(data);
    }

    @Override
    public AType getType() {
        return type;
    }
}
