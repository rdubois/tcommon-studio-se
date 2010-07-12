/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.core.model.metadata.builder.connection.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.talend.core.model.metadata.builder.connection.*;
import org.talend.core.model.metadata.builder.connection.AbstractMetadataObject;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.ConnectionPackage;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.metadata.builder.connection.DelimitedFileConnection;
import org.talend.core.model.metadata.builder.connection.FileConnection;
import org.talend.core.model.metadata.builder.connection.GenericSchemaConnection;
import org.talend.core.model.metadata.builder.connection.LDAPSchemaConnection;
import org.talend.core.model.metadata.builder.connection.LdifFileConnection;
import org.talend.core.model.metadata.builder.connection.Metadata;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.builder.connection.PositionalFileConnection;
import org.talend.core.model.metadata.builder.connection.QueriesConnection;
import org.talend.core.model.metadata.builder.connection.Query;
import org.talend.core.model.metadata.builder.connection.RegexpFileConnection;
import org.talend.core.model.metadata.builder.connection.SchemaTarget;
import org.talend.core.model.metadata.builder.connection.XmlFileConnection;
import org.talend.core.model.metadata.builder.connection.XmlXPathLoopDescriptor;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 * @see org.talend.core.model.metadata.builder.connection.ConnectionPackage
 * @generated
 */
public class ConnectionAdapterFactory extends AdapterFactoryImpl {

    /**
     * The cached model package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected static ConnectionPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConnectionAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = ConnectionPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc --> This
     * implementation returns <code>true</code> if the object is either the model's package or is an instance object
     * of the model. <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ConnectionSwitch modelSwitch = new ConnectionSwitch() {
            public Object caseMetadata(Metadata object) {
                return createMetadataAdapter();
            }
            public Object caseConnection(Connection object) {
                return createConnectionAdapter();
            }
            public Object caseMetadataColumn(MetadataColumn object) {
                return createMetadataColumnAdapter();
            }
            public Object caseAbstractMetadataObject(AbstractMetadataObject object) {
                return createAbstractMetadataObjectAdapter();
            }
            public Object caseMetadataTable(MetadataTable object) {
                return createMetadataTableAdapter();
            }
            public Object caseFileConnection(FileConnection object) {
                return createFileConnectionAdapter();
            }
            public Object caseDelimitedFileConnection(DelimitedFileConnection object) {
                return createDelimitedFileConnectionAdapter();
            }
            public Object casePositionalFileConnection(PositionalFileConnection object) {
                return createPositionalFileConnectionAdapter();
            }
            public Object caseEbcdicConnection(EbcdicConnection object) {
                return createEbcdicConnectionAdapter();
            }
            public Object caseMDMConnection(MDMConnection object) {
                return createMDMConnectionAdapter();
            }
            public Object caseDatabaseConnection(DatabaseConnection object) {
                return createDatabaseConnectionAdapter();
            }
            public Object caseSAPConnection(SAPConnection object) {
                return createSAPConnectionAdapter();
            }
            public Object caseSAPFunctionUnit(SAPFunctionUnit object) {
                return createSAPFunctionUnitAdapter();
            }
            public Object caseSAPIDocUnit(SAPIDocUnit object) {
                return createSAPIDocUnitAdapter();
            }
            public Object caseSAPFunctionParameterColumn(SAPFunctionParameterColumn object) {
                return createSAPFunctionParameterColumnAdapter();
            }
            public Object caseSAPFunctionParameterTable(SAPFunctionParameterTable object) {
                return createSAPFunctionParameterTableAdapter();
            }
            public Object caseInputSAPFunctionParameterTable(InputSAPFunctionParameterTable object) {
                return createInputSAPFunctionParameterTableAdapter();
            }
            public Object caseOutputSAPFunctionParameterTable(OutputSAPFunctionParameterTable object) {
                return createOutputSAPFunctionParameterTableAdapter();
            }
            public Object caseRegexpFileConnection(RegexpFileConnection object) {
                return createRegexpFileConnectionAdapter();
            }
            public Object caseXmlFileConnection(XmlFileConnection object) {
                return createXmlFileConnectionAdapter();
            }
            public Object caseSchemaTarget(SchemaTarget object) {
                return createSchemaTargetAdapter();
            }
            public Object caseQueriesConnection(QueriesConnection object) {
                return createQueriesConnectionAdapter();
            }
            public Object caseQuery(Query object) {
                return createQueryAdapter();
            }
            public Object caseLdifFileConnection(LdifFileConnection object) {
                return createLdifFileConnectionAdapter();
            }
            public Object caseFileExcelConnection(FileExcelConnection object) {
                return createFileExcelConnectionAdapter();
            }
            public Object caseXmlXPathLoopDescriptor(XmlXPathLoopDescriptor object) {
                return createXmlXPathLoopDescriptorAdapter();
            }
            public Object caseGenericSchemaConnection(GenericSchemaConnection object) {
                return createGenericSchemaConnectionAdapter();
            }
            public Object caseLDAPSchemaConnection(LDAPSchemaConnection object) {
                return createLDAPSchemaConnectionAdapter();
            }
            public Object caseWSDLSchemaConnection(WSDLSchemaConnection object) {
                return createWSDLSchemaConnectionAdapter();
            }
            public Object caseSalesforceSchemaConnection(SalesforceSchemaConnection object) {
                return createSalesforceSchemaConnectionAdapter();
            }
            public Object caseCDCConnection(CDCConnection object) {
                return createCDCConnectionAdapter();
            }
            public Object caseCDCType(CDCType object) {
                return createCDCTypeAdapter();
            }
            public Object caseSubscriberTable(SubscriberTable object) {
                return createSubscriberTableAdapter();
            }
            public Object caseSAPTestInputParameterTable(SAPTestInputParameterTable object) {
                return createSAPTestInputParameterTableAdapter();
            }
            public Object caseConcept(Concept object) {
                return createConceptAdapter();
            }
            public Object caseConceptTarget(ConceptTarget object) {
                return createConceptTargetAdapter();
            }
            public Object caseHL7Connection(HL7Connection object) {
                return createHL7ConnectionAdapter();
            }
            public Object caseHeaderFooterConnection(HeaderFooterConnection object) {
                return createHeaderFooterConnectionAdapter();
            }
            public Object caseXMLFileNode(XMLFileNode object) {
                return createXMLFileNodeAdapter();
            }
            public Object defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    public Adapter createAdapter(Notifier target) {
        return (Adapter)modelSwitch.doSwitch((EObject)target);
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.Metadata <em>Metadata</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.Metadata
     * @generated
     */
    public Adapter createMetadataAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.Connection <em>Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.Connection
     * @generated
     */
    public Adapter createConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.MetadataTable <em>Metadata Table</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.MetadataTable
     * @generated
     */
    public Adapter createMetadataTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.MetadataColumn <em>Metadata Column</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.MetadataColumn
     * @generated
     */
    public Adapter createMetadataColumnAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.AbstractMetadataObject <em>Abstract Metadata Object</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.AbstractMetadataObject
     * @generated
     */
    public Adapter createAbstractMetadataObjectAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.FileConnection <em>File Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.FileConnection
     * @generated
     */
    public Adapter createFileConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.DelimitedFileConnection <em>Delimited File Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.DelimitedFileConnection
     * @generated
     */
    public Adapter createDelimitedFileConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.PositionalFileConnection <em>Positional File Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.PositionalFileConnection
     * @generated
     */
    public Adapter createPositionalFileConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.EbcdicConnection <em>Ebcdic Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.EbcdicConnection
     * @generated
     */
    public Adapter createEbcdicConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.MDMConnection <em>MDM Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.MDMConnection
     * @generated
     */
    public Adapter createMDMConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.DatabaseConnection <em>Database Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.DatabaseConnection
     * @generated
     */
    public Adapter createDatabaseConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.SAPConnection <em>SAP Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.SAPConnection
     * @generated
     */
    public Adapter createSAPConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.SAPFunctionUnit <em>SAP Function Unit</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.SAPFunctionUnit
     * @generated
     */
    public Adapter createSAPFunctionUnitAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.SAPIDocUnit <em>SAPI Doc Unit</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.SAPIDocUnit
     * @generated
     */
    public Adapter createSAPIDocUnitAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.SAPFunctionParameterColumn <em>SAP Function Parameter Column</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.SAPFunctionParameterColumn
     * @generated
     */
    public Adapter createSAPFunctionParameterColumnAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.SAPFunctionParameterTable <em>SAP Function Parameter Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.SAPFunctionParameterTable
     * @generated
     */
    public Adapter createSAPFunctionParameterTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.InputSAPFunctionParameterTable <em>Input SAP Function Parameter Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.InputSAPFunctionParameterTable
     * @generated
     */
    public Adapter createInputSAPFunctionParameterTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.OutputSAPFunctionParameterTable <em>Output SAP Function Parameter Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.OutputSAPFunctionParameterTable
     * @generated
     */
    public Adapter createOutputSAPFunctionParameterTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.RegexpFileConnection <em>Regexp File Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.RegexpFileConnection
     * @generated
     */
    public Adapter createRegexpFileConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.XmlFileConnection <em>Xml File Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.XmlFileConnection
     * @generated
     */
    public Adapter createXmlFileConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.SchemaTarget <em>Schema Target</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.SchemaTarget
     * @generated
     */
    public Adapter createSchemaTargetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.QueriesConnection <em>Queries Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.QueriesConnection
     * @generated
     */
    public Adapter createQueriesConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.Query <em>Query</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.Query
     * @generated
     */
    public Adapter createQueryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.LdifFileConnection <em>Ldif File Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.LdifFileConnection
     * @generated
     */
    public Adapter createLdifFileConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.FileExcelConnection <em>File Excel Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.FileExcelConnection
     * @generated
     */
    public Adapter createFileExcelConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.XmlXPathLoopDescriptor <em>Xml XPath Loop Descriptor</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.XmlXPathLoopDescriptor
     * @generated
     */
    public Adapter createXmlXPathLoopDescriptorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.GenericSchemaConnection <em>Generic Schema Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.GenericSchemaConnection
     * @generated
     */
    public Adapter createGenericSchemaConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.LDAPSchemaConnection <em>LDAP Schema Connection</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.LDAPSchemaConnection
     * @generated
     */
    public Adapter createLDAPSchemaConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.WSDLSchemaConnection <em>WSDL Schema Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.WSDLSchemaConnection
     * @generated
     */
    public Adapter createWSDLSchemaConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.SalesforceSchemaConnection <em>Salesforce Schema Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.SalesforceSchemaConnection
     * @generated
     */
    public Adapter createSalesforceSchemaConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.CDCConnection <em>CDC Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.CDCConnection
     * @generated
     */
    public Adapter createCDCConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.CDCType <em>CDC Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.CDCType
     * @generated
     */
    public Adapter createCDCTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.SubscriberTable <em>Subscriber Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.SubscriberTable
     * @generated
     */
    public Adapter createSubscriberTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.SAPTestInputParameterTable <em>SAP Test Input Parameter Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.SAPTestInputParameterTable
     * @generated
     */
    public Adapter createSAPTestInputParameterTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.Concept <em>Concept</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.Concept
     * @generated
     */
    public Adapter createConceptAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.ConceptTarget <em>Concept Target</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.ConceptTarget
     * @generated
     */
    public Adapter createConceptTargetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.HL7Connection <em>HL7 Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.HL7Connection
     * @generated
     */
    public Adapter createHL7ConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.HeaderFooterConnection <em>Header Footer Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.HeaderFooterConnection
     * @generated
     */
    public Adapter createHeaderFooterConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.core.model.metadata.builder.connection.XMLFileNode <em>XML File Node</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.core.model.metadata.builder.connection.XMLFileNode
     * @generated
     */
    public Adapter createXMLFileNodeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc --> This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // ConnectionAdapterFactory
