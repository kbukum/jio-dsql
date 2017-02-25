package org.oopdev.jio.dsql.hibernate.hql;

import org.oopdev.jio.dsql.api.criteria.CriteriaJoin;
import org.oopdev.jio.dsql.api.criteria.CriteriaParent;
import org.oopdev.jio.dsql.api.criteria.JoinRelation;
import org.oopdev.jio.dsql.api.criteria.criterion.Restriction;
import org.oopdev.jio.dsql.api.criteria.criterion.RestrictionList;
import org.oopdev.jio.dsql.api.criteria.projection.*;
import org.oopdev.jio.dsql.common.lang.Increment;
import org.oopdev.jio.dsql.common.util.Strings;

import java.util.*;

/**
 * Created by kamilbukum on 30/01/2017.
 */
public class HqlUtil {

    /**
     *
     * @param criteria
     * @param projection
     * @param alias
     * @param elements
     * @param groupJoiner
     * @return
     */
    public static String selectForListByProjection(CriteriaParent criteria, Projection projection, String alias, TransformerUtil.Elements elements, StringJoiner groupJoiner){
        String result;
        if(projection instanceof IdentifierProjection) {
            result = criteria.getAlias() + "." + criteria.getMeta().getIdentityName() + getAsKey(criteria, criteria.getMeta().getIdentityName(), alias);
        }  else if(projection instanceof ProjectionElements) {
            ProjectionElements p = (ProjectionElements)projection;
            String elementAlias = alias != null ? alias: p.getProperty();
            if(elements.elementsMap == null) {
                elements.elementsMap = new LinkedHashMap<>();
            }
            elements.elementsMap.put(p.getProperty(), elementAlias);
            return null;
        } else if(projection instanceof PropertyProjection) {
            PropertyProjection p = (PropertyProjection)projection;
            result = criteria.getAlias() + "." + p.getProperty() + getAsKey(criteria, p.getProperty(), alias);
            if(p.isGrouped()) {
                groupJoiner.add( criteria.getAlias() + "." + p.getProperty());
            }
        } else if(projection instanceof FunctionProjection) {
            FunctionProjection pp = (FunctionProjection)projection;
            if(FunctionProjection.Type.COUNT == pp.getFnType() && Strings.isEmptyOrNull(pp.getProperty())) {
                return pp.getFnType().name() + "(1)" + getAsKey(criteria, "fn_" + pp.getFnType().name().toLowerCase(), alias);
            } else {
                return pp.getFnType().name() + "(" + criteria.getAlias() + "." + pp.getProperty() + ")" + getAsKey(criteria, "fn_" + pp.getProperty() + "_" + pp.getFnType().name().toLowerCase() , alias);
            }
        } else if(projection instanceof EnhancedProjection) {
            EnhancedProjection p = (EnhancedProjection)projection;
            result = selectForListByProjection(criteria, p.getProjection(), p.getAlias(),elements, groupJoiner);
        } else if(projection instanceof ProjectionList){
            ProjectionList p = (ProjectionList)projection;
            StringJoiner joiner = new StringJoiner(", ");
            for(int i = 0 ; i < p.getLength(); i++) {
                Projection childProjection = p.getProjection(i);
                String projectionString = selectForListByProjection(criteria, childProjection, null,elements, groupJoiner);
                if(!Strings.isEmptyOrNull(projectionString)) {
                    joiner.add(projectionString);
                }
            }
            result = joiner.toString();
        } else {
            throw new RuntimeException("Unknown Projection !"+ projection);
        }
        return result;
    }

    /**
     *
     * @param criteria
     * @param selectAlias
     * @param asAlias
     * @return
     */
    private static String getAsKey(CriteriaParent criteria, String selectAlias, String asAlias){
        String alias;
        if(asAlias != null) {
            alias = asAlias.replaceAll("\\.", "_0_");
        } else if(criteria.isRoot()) {
            alias = selectAlias;
        } else {
            alias =  criteria.getAlias() + "_0_" + selectAlias;
        }
        return  " AS "  + alias;
    }

    /**
     *
     * @param criteria
     * @param restrictions
     * @param restrictionJoiner
     * @param qJoiner
     * @param parameterMap
     * @param restrictionOrder
     */
    public static void generateRestrictions(
            CriteriaParent criteria,
            List<Restriction> restrictions,
            StringJoiner restrictionJoiner,
            StringJoiner qJoiner,
            Map<String, Object> parameterMap,
            Increment restrictionOrder){
        for(Restriction restriction: restrictions) {
            String result = null;
            switch (restriction.getOperator()) {
                case EQUALS:
                    result = restrictionToString(criteria, restriction, restrictionOrder.increment(), restriction.getValue(), "=", parameterMap);
                    break;
                case IS_NULL:
                    result = criteria.getAlias() + "." + restriction.getName() + " IS NULL";
                    break;
                case NOT_EQUALS:
                    result = criteria.getAlias() + "." + restrictionToString(criteria, restriction, restrictionOrder.increment(), restriction.getValue(), "!=", parameterMap);
                    break;
                case IS_NOT_NULL:
                    result = restriction.getName() + " IS NOT NULL";
                    break;
                case LESS_THAN:
                    result = restrictionToString(criteria, restriction, restrictionOrder.increment(), restriction.getValue(), " < ", parameterMap);
                    break;
                case LESS_OR_EQUALS_THAN:
                    result = restrictionToString(criteria, restriction, restrictionOrder.increment(), restriction.getValue(), " <= ", parameterMap);
                    break;
                case GREATER_THAN:
                    result = restrictionToString(criteria, restriction, restrictionOrder.increment(), restriction.getValue(), " > ", parameterMap);
                    break;
                case GREATER_OR_EQUALS_THAN:
                    result = restrictionToString(criteria, restriction, restrictionOrder.increment(), restriction.getValue(), " >= ", parameterMap);
                    break;
                case Q:
                    result = null;
                    qJoiner.add(criteria.getAlias() + "." + restriction.getName() + " LIKE " + ":" + restriction.getValueAlias());
                    parameterMap.putIfAbsent(restriction.getValueAlias(), getPercentValue(restriction));
                    break;
                case CONTAINS:
                    Object containsValue = getPercentValue(restriction);
                    result = restrictionToString(criteria, restriction, restrictionOrder.increment(), containsValue, " LIKE ", parameterMap);
                    break;
                case IN:
                    result = restrictionToString(
                            criteria,
                            restriction,
                            restrictionOrder.increment(),
                            restriction.getValue(),
                            " IN ", parameterMap
                    );
                    break;
                case AND:
                    StringJoiner andJoiner =  new StringJoiner(" AND ");
                    generateRestrictions(criteria, ((RestrictionList)restriction).getRestrictions(), andJoiner, qJoiner, parameterMap, restrictionOrder);
                    if(!"".equals(andJoiner.toString())) {
                        result = "( " +  andJoiner.toString() + " )";
                    }break;
                case OR:
                    StringJoiner orJoiner = new StringJoiner(" OR ");
                    generateRestrictions(criteria, ((RestrictionList)restriction).getRestrictions(), orJoiner, qJoiner, parameterMap, restrictionOrder);
                    if(!"".equals(orJoiner.toString())) {
                        result = "( " +  orJoiner.toString() + " )";
                    }
            }
            if(result != null) {
                restrictionJoiner.add(result);
            }
        }
    }
    /**
     *
     * @param criteria
     * @param restriction
     * @param operator
     * @return
     */
    private static String restrictionToString(CriteriaParent criteria, Restriction restriction, Integer order, Object value, String operator, Map<String, Object> parameterMap){
        if(Strings.isEmptyOrNull(restriction.getValueAlias())) {
            restriction.setValueAlias(criteria.getAlias() + "_" + restriction.getName()+ "_" + order);
        }
        parameterMap.put(restriction.getValueAlias(), value);
        return criteria.getAlias() + "." + restriction.getName() + operator + (value instanceof Collection ? (  "(:" + restriction.getValueAlias() + ")"): ":" + restriction.getValueAlias());
    }

    private static Object getPercentValue(Restriction restriction) {
        if(restriction.getValue() != null) {
            return "%" + restriction.getValue() + "%";
        }
        return  "%%";
    }


    /**
     * Generates Join Query for the given JoinCriteria
     * @param criteriaJoin
     * @return
     */
    public static String joinToString(CriteriaJoin criteriaJoin) {
        StringBuilder builder = new StringBuilder("LEFT OUTER JOIN ")
                .append(criteriaJoin.getEntityClass().getName())
                .append(" ")
                .append(criteriaJoin.getAlias())
                .append(" ")
                .append(" ON ");

        if(criteriaJoin.getJoinRelations().size() == 0) {
            throw new RuntimeException("Not found any Join Relations in " + criteriaJoin.getAlias() + " Join Criteria ! ");
        }


        StringJoiner joiner = new StringJoiner(" AND ");
        List<JoinRelation> relationList = criteriaJoin.getJoinRelations();
        for(JoinRelation joinRelation: relationList) {
            StringBuilder relationBuilder = new StringBuilder("\n")
                    .append(joinRelation.getRelationCriteria().getAlias())
                    .append(".")
                    .append(joinRelation.getRelationField())
                    .append("=")
                    .append(joinRelation.getJoinedCriteria().getAlias())
                    .append(".")
                    .append(joinRelation.getJoinedField());
            joiner.add(relationBuilder.toString());
        }

        if(joiner.length() > 0) {
            builder.append(joiner.toString());
        }
        return builder.toString();
    }
}
