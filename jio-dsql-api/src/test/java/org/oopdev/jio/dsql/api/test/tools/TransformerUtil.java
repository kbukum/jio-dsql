package org.oopdev.jio.dsql.api.test.tools;

import org.oopdev.jio.dsql.api.criteria.Criteria;
import org.oopdev.jio.dsql.common.lang.Pair;

import java.util.Map;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class TransformerUtil {

    public static <T> Pair<String, Map<String, Object>> list(Criteria<T> criteria) {

        StringBuilder queryBuilder = new StringBuilder("SELECT");
        queryBuilder
                .append(" ")
                .append(criteria.getAlias())
                .append(" FROM ")
                .append(criteria.getEntityClass().getName())
                .append(" ")
                .append(criteria.getAlias());
        Pair<String, Map<String, Object>> pair = new Pair<>(queryBuilder.toString(), null);
        return pair;
    }



}
