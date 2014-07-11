// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.model.relationship;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType;

/**
 * DOC ggu class global comment. Detailled comment
 */
public abstract class AbstractJobParameterInRepositoryRelationshipHandler extends AbstractJobParameterRelationshipHandler {

    public static final String IN_REPOSITORY = "REPOSITORY"; //$NON-NLS-1$

    public static final String TRUE = "true";

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.relationship.AbstractParameterRelationshipHandler#collect(java.util.Map,
     * java.util.Map)
     */
    @Override
    protected Set<Relation> collect(Map<String, ElementParameterType> parametersMap, Map<?, ?> options) {
        Set<Relation> relationSet = new HashSet<Relation>();

        if (getUsedCondition() == null) {
            relationSet = doCollect(parametersMap, options);
        } else {
            ElementParameterType repositoryTypeParam = getParameterTypeByNames(parametersMap, getUsedCondition());
            if (repositoryTypeParam != null && TRUE.equals(repositoryTypeParam.getValue())) {
                relationSet = doCollect(parametersMap, options);
            }
        }

        return relationSet;
    }

    private Set<Relation> doCollect(Map<String, ElementParameterType> parametersMap, Map<?, ?> options) {
        Set<Relation> relationSet = new HashSet<Relation>();

        ElementParameterType repositoryTypeParam = getParameterTypeByNames(parametersMap, getRepositoryTypeName(),
                getRepositoryTypeNameN());
        if (repositoryTypeParam != null && IN_REPOSITORY.equals(repositoryTypeParam.getValue())) {
            ElementParameterType repositoryTypeValueParam = getParameterTypeByNames(parametersMap, getRepositoryTypeValueName(),
                    getRepositoryTypeValueNameN());
            if (repositoryTypeValueParam != null) {
                String repositoryIdOrValue = repositoryTypeValueParam.getValue();
                if (StringUtils.isNotEmpty(repositoryIdOrValue)) {
                    Relation addedRelation = new Relation();
                    addedRelation.setId(repositoryIdOrValue);
                    addedRelation.setType(getRepositoryRelationType());
                    addedRelation.setVersion(RelationshipItemBuilder.LATEST_VERSION);
                    relationSet.add(addedRelation);
                }
            }

        }
        return relationSet;
    }

    /**
     * Try to find the name1 of parameter type, if not found, will try the list of nameN.
     */
    protected ElementParameterType getParameterTypeByNames(Map<String, ElementParameterType> parametersMap, String name1,
            String... nameN) {
        if (parametersMap == null || parametersMap.isEmpty() || name1 == null) {
            return null;
        }
        ElementParameterType paramType = parametersMap.get(name1);
        if (paramType == null && nameN != null && nameN.length > 0) {
            for (String name : nameN) {
                paramType = parametersMap.get(name);
                if (paramType != null) { // found
                    break;
                }
            }
        }
        return paramType;
    }

    /**
     * Something like PROPERTY:PROPERTY_TYPE
     */
    protected abstract String getRepositoryTypeName();

    /**
     * Something like PROPERTY:REPOSITORY_PROPERTY_TYPE
     */
    protected abstract String getRepositoryTypeValueName();

    /**
     * Something like PROPERTY_TYPE
     */
    protected String[] getRepositoryTypeNameN() {
        return new String[0]; // default, no Name N
    }

    /**
     * Something like REPOSITORY_PROPERTY_TYPE
     */
    protected String[] getRepositoryTypeValueNameN() {
        return new String[0];// default, no Name N
    }

    protected String getUsedCondition() {
        return null;
    }

    /**
     * get the type of relation type
     */
    protected abstract String getRepositoryRelationType();
}
