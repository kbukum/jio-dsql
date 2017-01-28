package org.oopdev.jio.dsql.api.criteria.transform;

import java.util.List;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class Result<T> {
    private List<T> list;
    private Long count;
    public Result(List<T> list, Long count) {
        this.list = list;
        this.count = count;
    }

    public List<T> list() {
        return this.list;
    }
    public Long count(){
        return this.count;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
