package org.oopdev.jio.dsql.api.test.tools;

import org.oopdev.jio.dsql.api.criteria.Criteria;

import org.oopdev.jio.dsql.api.criteria.CriteriaJoin;
import org.oopdev.jio.dsql.api.criteria.CriteriaParent;
import org.oopdev.jio.dsql.api.criteria.criterion.Restriction;
import org.oopdev.jio.dsql.api.criteria.projection.Projection;
import org.oopdev.jio.dsql.common.lang.Pair;
import org.oopdev.jio.dsql.common.util.Strings;

import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class TransformerUtil {

    public static <T> Pair<String, Map<String, Object>> query(Criteria<T> criteria) {
        StringBuilder listBuilder = new StringBuilder();
        // configure select
        String from = from(criteria);
        StringJoiner selectJoiner = new StringJoiner(", ");
        String query = criteriaToQuery(criteria, selectJoiner);
        listBuilder.append("SELECT ").append(selectJoiner.toString()).append(" ").append(from).append(query);
        Pair<String, Map<String, Object>> pair = new Pair<>(listBuilder.toString(), null);
        return pair;
    }


    public static <T> Pair<String, Map<String, Object>> count(Criteria<T> criteria) {
        StringBuilder countBuilder = new StringBuilder();
        // configure select
        String from = from(criteria);
        String query = criteriaToQuery(criteria, null);
        countBuilder.append("SELECT ").append("count(1)").append(" ").append(from).append(query);
        return new Pair<>(countBuilder.toString(), null);
    }

    public static <T> Pair<String, Pair<String, Map<String, Object>>> pairList(Criteria<T> criteria) {
        StringBuilder listBuilder = new StringBuilder();
        StringBuilder countBuilder = new StringBuilder();
        // configure select
        String from = from(criteria);
        StringJoiner selectJoiner = new StringJoiner(", ");
        String query = criteriaToQuery(criteria, selectJoiner);
        listBuilder.append("SELECT ").append(selectJoiner.toString()).append(" ").append(from).append(query);
        countBuilder.append("SELECT ").append("count(1)").append(" ").append(from).append(query);
        return new Pair<>(listBuilder.toString(), new Pair<>(countBuilder.toString(), null));
    }

    public static <E> String criteriaToQuery(Criteria<E> criteria, StringJoiner selectJoiner){
        StringJoiner joinJoiner = new StringJoiner("\n");
        StringJoiner whereJoiner = new StringJoiner(", ");
        criteriaToQuery(criteria, selectJoiner, joinJoiner, whereJoiner);
        String groupByString = groupByToString(criteria);
        String orderByString = orderByToString(criteria);
        String query = joinJoiner.toString() + whereJoiner.toString() + groupByString + orderByString;
        return query;
    }

    public static <E> void criteriaToQuery(
            CriteriaParent<E> criteria,
            StringJoiner selectJoiner,
            StringJoiner joinJoiner,
            StringJoiner whereJoiner
            ){

        if(selectJoiner != null) {
            if(criteria.getProjections() != null && criteria.getProjections().size() > 0) {
                for(Projection projection: criteria.getProjections()) {
                    String projectionString = projectionToString(projection);
                    if(!Strings.isEmptyOrNull(projectionString)) {
                        selectJoiner.add(projectionString);
                    }
                }
            }
        }

        if(criteria.getRestrictions() != null && criteria.getRestrictions().size() > 0) {
            for(Restriction restriction: criteria.getRestrictions()) {
                String restrictionToString = restrictionToString(restriction);
                if(!Strings.isEmptyOrNull(restrictionToString)) {
                    whereJoiner.add(restrictionToString);
                }
            }
        }

        if(criteria.getJoins() != null && criteria.getJoins().size() > 0) {
            for(Map.Entry<String, CriteriaJoin<E>> joinEntry: criteria.getJoins().entrySet()) {
                String joinToString = joinToString(criteria, joinEntry);
                if(!Strings.isEmptyOrNull(joinToString)) {
                    joinJoiner.add(joinToString);
                }
                criteriaToQuery(joinEntry.getValue(), selectJoiner, joinJoiner,whereJoiner);
            }
        }
    }

    public static String projectionToString(Projection projection) {
        // TODO projection to string
        return null;
    }

    public static String restrictionToString(Restriction restriction) {
        // TODO restriction to string
        return null;
    }


    public static <E> String joinToString(CriteriaParent<E> parent, Map.Entry<String, CriteriaJoin<E>> joinEntry){
        // TODO join to string
        return null;
    }

    public static <E> String groupByToString(CriteriaParent<E> criteria) {
        return null;
    }
    public static <E> String orderByToString(CriteriaParent<E> criteria) {
        return null;
    }

    public static String from(Criteria criteria){
        return "\nFROM " + criteria.getEntityClass().getName() + " " + criteria.getAlias();
    }
}
