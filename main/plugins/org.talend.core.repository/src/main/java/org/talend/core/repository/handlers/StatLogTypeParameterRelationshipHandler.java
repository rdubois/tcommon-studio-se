package org.talend.core.repository.handlers;

import org.talend.core.model.relationship.AbstractJobParameterInRepositoryRelationshipHandler;
import org.talend.core.model.relationship.RelationshipItemBuilder;

public class StatLogTypeParameterRelationshipHandler extends AbstractJobParameterInRepositoryRelationshipHandler {

    public static final String ON_DATABASE_FLAG = "ON_DATABASE_FLAG";

    public static final String PROPERTY_TYPE_PROPERTY_TYPE = "PROPERTY_TYPE:PROPERTY_TYPE";

    public static final String PROPERTY_TYPE_REPOSITORY_PROPERTY_TYPE = "PROPERTY_TYPE:REPOSITORY_PROPERTY_TYPE";

    public StatLogTypeParameterRelationshipHandler() {
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
        return PROPERTY_TYPE_PROPERTY_TYPE;
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
        return PROPERTY_TYPE_REPOSITORY_PROPERTY_TYPE;
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
        return ON_DATABASE_FLAG;
    }

}
