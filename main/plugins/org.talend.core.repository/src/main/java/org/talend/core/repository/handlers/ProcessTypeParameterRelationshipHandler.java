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
package org.talend.core.repository.handlers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.core.model.relationship.AbstractJobParameterRelationshipHandler;
import org.talend.core.model.relationship.Relation;
import org.talend.core.model.relationship.RelationshipItemBuilder;
import org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class ProcessTypeParameterRelationshipHandler extends AbstractJobParameterRelationshipHandler {

    public static final String IMPLICIT_TCONTEXTLOAD = "IMPLICIT_TCONTEXTLOAD"; //$NON-NLS-1$

    public static final String PROPERTY_TYPE_IMPLICIT_CONTEXT_PROPERTY_TYPE = "PROPERTY_TYPE_IMPLICIT_CONTEXT:PROPERTY_TYPE"; //$NON-NLS-1$

    public static final String PROPERTY_TYPE_IMPLICIT_CONTEXT_REPOSITORY_PROPERTY_TYPE = "PROPERTY_TYPE_IMPLICIT_CONTEXT:REPOSITORY_PROPERTY_TYPE"; //$NON-NLS-1$

    public static final String ON_DATABASE_FLAG = "ON_DATABASE_FLAG";

    public static final String PROPERTY_TYPE_PROPERTY_TYPE = "PROPERTY_TYPE:PROPERTY_TYPE";

    public static final String PROPERTY_TYPE_REPOSITORY_PROPERTY_TYPE = "PROPERTY_TYPE:REPOSITORY_PROPERTY_TYPE";

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.relationship.AbstractParameterRelationshipHandler#collect(java.util.Map,
     * java.util.Map)
     */
    @SuppressWarnings("nls")
    @Override
    protected Set<Relation> collect(Map<String, ElementParameterType> parametersMap, Map<?, ?> options) {
        Set<Relation> relationSet = new HashSet<Relation>();

        // job version
        String jobVersion = RelationshipItemBuilder.LATEST_VERSION;
        ElementParameterType processVersionParamType = parametersMap.get("PROCESS:PROCESS_TYPE_VERSION");
        if (processVersionParamType == null) {
            processVersionParamType = parametersMap.get("PROCESS_TYPE_VERSION");
        }
        if (processVersionParamType != null) {
            jobVersion = processVersionParamType.getValue();
        }

        //
        ElementParameterType processParamType = parametersMap.get("PROCESS:PROCESS_TYPE_PROCESS");
        if (processParamType == null) {
            processParamType = parametersMap.get("PROCESS_TYPE_PROCESS");
        }
        if (processParamType != null) {
            String jobIds = processParamType.getValue();
            String[] jobsArr = jobIds.split(RelationshipItemBuilder.COMMA);
            for (String jobId : jobsArr) {
                if (StringUtils.isNotEmpty(jobId)) {
                    Relation addedRelation = new Relation();
                    addedRelation.setId(jobId);
                    addedRelation.setType(RelationshipItemBuilder.JOB_RELATION);
                    addedRelation.setVersion(jobVersion);
                    relationSet.add(addedRelation);
                }
            }
        }

        // implicit_tcontextload
        ElementParameterType implicit_tcontextload_Type = parametersMap.get(IMPLICIT_TCONTEXTLOAD);
        if (implicit_tcontextload_Type != null && implicit_tcontextload_Type.getValue().equals("true")) {
            ElementParameterType property_type_implicit_context_property_type = parametersMap
                    .get(PROPERTY_TYPE_IMPLICIT_CONTEXT_PROPERTY_TYPE);
            if (property_type_implicit_context_property_type != null
                    && "REPOSITORY".equals(property_type_implicit_context_property_type.getValue())) {
                ElementParameterType property_type_implicit_context_repository_property_type = parametersMap
                        .get(PROPERTY_TYPE_IMPLICIT_CONTEXT_REPOSITORY_PROPERTY_TYPE);
                if (property_type_implicit_context_repository_property_type != null) {
                    String Id = property_type_implicit_context_repository_property_type.getValue();
                    if (StringUtils.isNotEmpty(Id)) {
                        Relation addedRelation = new Relation();
                        addedRelation.setId(Id);
                        addedRelation.setType(RelationshipItemBuilder.PROPERTY_RELATION);
                        addedRelation.setVersion(RelationshipItemBuilder.LATEST_VERSION);
                        relationSet.add(addedRelation);
                    }
                }
            }
        }

        // stats & logs
        ElementParameterType on_database_flag = parametersMap.get(ON_DATABASE_FLAG);
        if (on_database_flag != null && on_database_flag.getValue().equals("true")) {
            ElementParameterType property_type_property_type = parametersMap.get(PROPERTY_TYPE_PROPERTY_TYPE);
            if (property_type_property_type != null && "REPOSITORY".equals(property_type_property_type.getValue())) {
                ElementParameterType property_type_repository_property_type = parametersMap
                        .get(PROPERTY_TYPE_REPOSITORY_PROPERTY_TYPE);
                if (property_type_repository_property_type != null) {
                    String Id = property_type_repository_property_type.getValue();
                    if (StringUtils.isNotEmpty(Id)) {
                        Relation addedRelation = new Relation();
                        addedRelation.setId(Id);
                        addedRelation.setType(RelationshipItemBuilder.PROPERTY_RELATION);
                        addedRelation.setVersion(RelationshipItemBuilder.LATEST_VERSION);
                        relationSet.add(addedRelation);
                    }
                }
            }
        }

        return relationSet;
    }

}
