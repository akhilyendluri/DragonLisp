package org.risingDragon.tree;

import java.util.List;

public interface Node {

    /**
     *
     * @return
     */
    List<?> getValue();

    /**
     *
     * @return
     */
    AType getType();
}
