package org.talend.core.repository.handlers;

import org.talend.core.model.relationship.AbstractJobParameterInRepositoryRelationshipHandler;
import org.talend.core.model.relationship.RelationshipItemBuilder;

public class ImplicitContextTypeParameterRelationshipHandler extends AbstractJobParameterInRepositoryRelationshipHandler {

    public static final String IMPLICIT_TCONTEXTLOAD = "IMPLICIT_TCONTEXTLOAD"; //$NON-NLS-1$

    public static final String PROPERTY_TYPE_IMPLICIT_CONTEXT_PROPERTY_TYPE = "PROPERTY_TYPE_IMPLICIT_CONTEXT:PROPERTY_TYPE"; //$NON-NLS-1$

    public static final String PROPERTY_TYPE_IMPLICIT_CONTEXT_REPOSITORY_PROPERTY_TYPE = "PROPERTY_TYPE_IMPLICIT_CONTEXT:REPOSITORY_PROPERTY_TYPE"; //$NON-NLS-1$

    public ImplicitContextTypeParameterRelationshipHandler() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.relationship.AbstractJobParameterInRepositoryRelationshipHandler#getRepositoryTypeName()
     */
    @Override
    protected String getRepositoryTypeName() {
        return PROPERTY_TYPE_IMPLICIT_CONTEXT_PROPERTY_TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.relationship.AbstractJobParameterInRepositoryRelationshipHandler#getRepositoryTypeValueName
     * ()
     */
    @Override
    protected String getRepositoryTypeValueName() {
        return PROPERTY_TYPE_IMPLICIT_CONTEXT_REPOSITORY_PROPERTY_TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.relationship.AbstractJobParameterInRepositoryRelationshipHandler#getRepositoryRelationType
     * ()
     */
    @Override
    protected String getRepositoryRelationType() {
        return RelationshipItemBuilder.PROPERTY_RELATION;
    }

    @Override
    protected String getUsedCondition() {
        return IMPLICIT_TCONTEXTLOAD;
    }

}
