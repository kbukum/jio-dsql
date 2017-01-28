package org.oopdev.jio.dsql.common.lang;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class Pair<L, R> {
    private final L left;
    private final R right;
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
