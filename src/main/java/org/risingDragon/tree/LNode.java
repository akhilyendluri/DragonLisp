package org.risingDragon.tree;

import java.util.List;

public class LNode<T extends Node> extends BaseNode {
    private final List<T> data;

    public LNode(List<T> value) {
        this.data = value;
    }

    @Override
    public List<T> getValue() {
        return data;
    }

    @Override
    public AType getType() {
        return AType.list;
    }
}
