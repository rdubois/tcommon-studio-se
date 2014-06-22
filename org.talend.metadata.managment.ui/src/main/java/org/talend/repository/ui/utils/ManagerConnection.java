// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.ui.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ILibraryManagerService;
import org.talend.core.database.EDatabase4DriverClassName;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.database.conn.version.EDatabaseVersion4Drivers;
import org.talend.core.model.general.Project;
import org.talend.core.model.metadata.IMetadataConnection;
import org.talend.core.model.metadata.builder.database.ExtractMetaDataFromDataBase;
import org.talend.core.model.metadata.builder.database.ExtractMetaDataUtils;
import org.talend.core.model.metadata.builder.database.JDBCDriverLoader;
import org.talend.core.model.metadata.builder.database.hive.EmbeddedHiveDataBaseMetadata;
import org.talend.core.model.metadata.connection.hive.HiveConnVersionInfo;
import org.talend.core.repository.ConnectionStatus;
import org.talend.core.repository.IDBMetadataProvider;
import org.talend.core.repository.model.ResourceModelUtils;
import org.talend.metadata.managment.ui.i18n.Messages;
import org.talend.repository.ProjectManager;

/**
 * @author ocarbone
 * 
 */
public class ManagerConnection {

    private static Logger log = Logger.getLogger(ManagerConnection.class);

    private boolean isValide = false;

    String messageException = null;

    // Strings to save

    String dbVersionString;

    String dbTypeString;

    String urlConnectionString;

    String server;

    String username;

    String password;

    String port;

    String sidOrDatabase;

    String datasource;

    String sqlSyntax;

    String strQuote;

    String nullChar;

    String filePath;

    Integer id = null;

    String additionalParams;

    private String schemaOracle;

    private String dbRootPath;

    private String driverClassName;

    private String driverJarPath;

    private Map<String, Object> otherParameters;

    IMetadataConnection oldConnection;

    /**
     * setValue.
     * 
     * @param id
     * @param dbType
     * @param sidStr
     * @param connectionString
     * @param server
     * @param username
     * @param password
     */
    public void setValue(Integer id, final String dbType, final String url, final String server, final String username,
            final String password, final String sidOrDatabase, final String port, final String file, final String datasource,
            final String schemaOracle, final String additionalParams, final String driverClassName, final String driverJarPath,
            final String dbVersionString) {
        this.id = id;
        this.dbTypeString = dbType;
        this.urlConnectionString = url;
        this.server = server;
        this.username = username;
        this.password = password;
        this.sidOrDatabase = sidOrDatabase;
        this.port = port;
        this.filePath = file;
        this.datasource = datasource;
        this.schemaOracle = schemaOracle;
        this.additionalParams = additionalParams;
        this.driverClassName = driverClassName;
        this.driverJarPath = driverJarPath;
        this.dbVersionString = dbVersionString;

    }

    public void setValue(Integer id, final String dbType, final String url, final String server, final String username,
            final String password, final String sidOrDatabase, final String port, final String file, final String datasource,
            final String schemaOracle, final String additionalParams, final String driverClassName, final String driverJarPath,
            final String dbVersionString, Map<String, Object> otherParameters) {
        setValue(id, dbType, url, server, username, password, sidOrDatabase, port, file, datasource, schemaOracle,
                additionalParams, driverClassName, driverJarPath, dbVersionString);
        this.otherParameters = otherParameters;
    }

    public void setValueProperties(final String sqlSyntax, final String strQuote, final String nullChar) {
        this.sqlSyntax = sqlSyntax;
        this.strQuote = strQuote;
        this.nullChar = nullChar;
    }

    /**
     * 
     * Added by Marvin Wang on Nov 22, 2012.
     * 
     * @param metadataConn
     * @return
     */
    public boolean checkForHive(IMetadataConnection metadataConn) {
        dbTypeString = metadataConn.getDbType();
        dbVersionString = metadataConn.getDbVersionString();
        urlConnectionString = metadataConn.getUrl();
        username = metadataConn.getUsername();
        password = metadataConn.getPassword();
        JDBCDriverLoader loader = new JDBCDriverLoader();
        List<String> jarPathList = fetchEmbeddedHiveDriverJars(metadataConn);
        jarPathList.addAll(fetchJarRequiredByMetastoreDB(metadataConn));

        try {
            List<?> list = loader.getConnection(jarPathList.toArray(new String[jarPathList.size()]),
                    EDatabase4DriverClassName.HIVE.getDriverClass(), urlConnectionString, username, password, dbTypeString,
                    dbVersionString, additionalParams);

            if (list != null) {
                Connection conn = (Connection) list.get(0);
                return checkHiveEmbeddedConnFromMetaDataDatabase(conn, metadataConn);
            } else {
                return checkHiveEmbeddedConnFromMetaDataDatabase(null, metadataConn);
            }
        } catch (Exception e) {
            return checkHiveEmbeddedConnFromMetaDataDatabase(null, metadataConn);
        }
    }

    /**
     * For hive embedded mode, it should use this instead of checking hive connection directly. Added by Marvin Wang on
     * Dec 4, 2012.
     * 
     * @param conn
     * @param metadataConn
     * @return
     */
    private boolean checkHiveEmbeddedConnFromMetaDataDatabase(Connection conn, IMetadataConnection metadataConn) {
        DatabaseMetaData dbMetaData = null;
        if (conn == null) {
            // Added by Marvin Wang on Dec.4, 2012. For Hive Embedded mode, in fact, sometimes there is no connenction
            // for Hive DatabaseMetadata.
            // Refer to EmbeddedHiveDataBaseMetadata that is a "fake" class.
            if (EDatabaseTypeName.HIVE.getDisplayName().equals(metadataConn.getDbType())
                    && EDatabaseVersion4Drivers.HIVE_EMBEDDED.getVersionDisplay().equals(metadataConn.getDbVersionString())) {
                dbMetaData = new EmbeddedHiveDataBaseMetadata(null);
            }
        } else {
            dbMetaData = ExtractMetaDataUtils.getDatabaseMetaData(conn, dbTypeString);
        }
        if (dbMetaData == null) {
            return false;
        }
        try {
            dbMetaData.getTables(null, null, "%", new String[] { "TABLE", "VIEW", "SYSTEM_TABLE" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            isValide = true;
            messageException = Messages.getString("ExtractMetaDataFromDataBase.connectionSuccessful"); //$NON-NLS-1$  
        } catch (SQLException e) {
            isValide = false;
            messageException = Messages.getString("ManagerConnection.connectionFailed"); //$NON-NLS-1$
            ExceptionHandler.process(e);
        }
        return isValide;
    }

    /**
     * 
     * Added by Marvin Wang on Nov 22, 2012.
     * 
     * @param metadataConn
     * @return
     */
    private List<String> fetchJarRequiredByMetastoreDB(IMetadataConnection metadataConn) {
        String jars = (String) metadataConn.getParameter(ConnParameterKeys.CONN_PARA_KEY_METASTORE_CONN_DRIVER_JAR);
        List<String> requiredJars = new ArrayList<String>();
        if (jars == null) {
            return requiredJars;
        }
        if (jars.contains(";")) { //$NON-NLS-1$
            String[] splittedPath = jars.split(";"); //$NON-NLS-1$
            for (String jarName : splittedPath) {
                requiredJars.add(jarName);
            }
        } else {
            requiredJars.add(jars);
        }
        return requiredJars;
    }

    /**
     * 
     * Added by Marvin Wang on Nov 22, 2012.
     * 
     * @param metadataConn
     * @return
     */
    private List<String> fetchEmbeddedHiveDriverJars(IMetadataConnection metadataConn) {
        String dbType = metadataConn.getDbType();
        String dbVersion = metadataConn.getDbVersionString();
        ILibraryManagerService librairesManagerService = (ILibraryManagerService) GlobalServiceRegister.getDefault().getService(
                ILibraryManagerService.class);

        List<String> jarPathList = new ArrayList<String>();
        List<String> driverNames = EDatabaseVersion4Drivers.getDrivers(dbType, dbVersion);
        if (driverNames != null) {
            for (String jar : driverNames) {
                if (!new File(getJavaLibPath() + jar).exists()) {
                    librairesManagerService.retrieve(jar, getJavaLibPath(), new NullProgressMonitor());
                }
                jarPathList.add(getJavaLibPath() + jar);
            }
        }
        return jarPathList;
    }

    /**
     * 
     * DOC YeXiaowei Comment method "getJavaLibPath".
     * 
     * @return
     */
    public static String getJavaLibPath() {
        Project project = ProjectManager.getInstance().getCurrentProject();
        IProject physProject;
        String tmpFolder = System.getProperty("user.dir"); //$NON-NLS-1$
        try {
            physProject = ResourceModelUtils.getProject(project);
            tmpFolder = physProject.getFolder("temp").getLocation().toPortableString(); //$NON-NLS-1$
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        tmpFolder = tmpFolder + "/dbWizard"; //$NON-NLS-1$
        File file = new File(tmpFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return tmpFolder + "/"; //$NON-NLS-1$
    }

    /**
     * Check connexion from the fields form.
     * 
     * @return isValide
     */
    public boolean check() {
        messageException = null;
        ConnectionStatus testConnection = null;
        try {
            EDatabaseTypeName type = EDatabaseTypeName.getTypeFromDbType(dbTypeString);
            /* use provider for the databse didn't use JDBC,for example: HBase */
            if (type.isUseProvider()) {
                IDBMetadataProvider extractorToUse = ExtractMetaDataFromDataBase.getProviderByDbType(dbTypeString);
                if (extractorToUse != null) {
                    testConnection = extractorToUse.testConnection(dbTypeString, urlConnectionString, username, password,
                            schemaOracle, server, port, driverClassName, driverJarPath, dbVersionString, additionalParams,
                            otherParameters);
                }
            } else {
                // MOD xqliu 2012-01-05 TDQ-4162
                // get the real schema name
                String schemaName = schemaOracle;
                if (EDatabaseTypeName.TERADATA.equals(type)) {
                    schemaName = sidOrDatabase;
                }
                // test the connection
                testConnection = ExtractMetaDataFromDataBase.testConnection(dbTypeString, urlConnectionString, username,
                        password, schemaName, driverClassName, driverJarPath, dbVersionString, additionalParams);
            }
            isValide = testConnection.getResult();
            messageException = testConnection.getMessageException();
        } catch (Exception e) {
            log.error(Messages.getString("CommonWizard.exception") + "\n" + e.toString()); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return isValide;
    }

    /**
     * DOC cantoine : Check connexion from IMetadataConnection comment. Detailled comment.
     * 
     * @return isValide
     */
    public boolean check(IMetadataConnection metadataConnection, boolean... onlyIfNeeded) {
        messageException = null;

        ConnectionStatus testConnection = null;
        // qli
        // check the same connection.
        // if same. just return true.
        if (metadataConnection == null) {
            return false;
        }
        if (onlyIfNeeded != null && onlyIfNeeded.length > 0 && onlyIfNeeded[0] == true) {
            if (metadataConnection.equals(oldConnection)) {
                return true;
            }
        }
        if (metadataConnection.getDbRootPath() != null && !metadataConnection.getDbRootPath().equals("")) { //$NON-NLS-1$
            setDbRootPath(metadataConnection.getDbRootPath());
        }

        try {
            EDatabaseTypeName type = EDatabaseTypeName.getTypeFromDbType(metadataConnection.getDbType());
            /* use extractor for the databse didn't use JDBC,for example: HBase */
            if (type.isUseProvider()) {
                IDBMetadataProvider extractorToUse = ExtractMetaDataFromDataBase.getProviderByDbType(metadataConnection
                        .getDbType());
                if (extractorToUse != null) {
                    testConnection = extractorToUse.testConnection(metadataConnection.getDbType(), metadataConnection.getUrl(),
                            metadataConnection.getUsername(), metadataConnection.getPassword(), metadataConnection.getSchema(),
                            metadataConnection.getServerName(), metadataConnection.getPort(),
                            metadataConnection.getDriverClass(), metadataConnection.getDriverJarPath(),
                            metadataConnection.getDbVersionString(), metadataConnection.getAdditionalParams(),
                            metadataConnection.getOtherParameters());
                }
            } else {
                if (EDatabaseTypeName.HIVE.getDisplayName().equals(metadataConnection.getDbType())) {
                    String key = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HIVE_MODE);
                    if (HiveConnVersionInfo.MODE_EMBEDDED.getKey().equals(key)) {
                        System.setProperty("hive.metastore.local", "false");
                        System.setProperty("hive.metastore.uris", "thrift://" + metadataConnection.getServerName() + ":"
                                + metadataConnection.getPort());
                    }
                }
                testConnection = ExtractMetaDataFromDataBase.testConnection(metadataConnection.getDbType(),
                        metadataConnection.getUrl(), metadataConnection.getUsername(), metadataConnection.getPassword(),
                        metadataConnection.getSchema(), metadataConnection.getDriverClass(),
                        metadataConnection.getDriverJarPath(), metadataConnection.getDbVersionString(),
                        metadataConnection.getAdditionalParams());
            }
            // qli
            // record this metadataConnection as old connection.
            oldConnection = metadataConnection;

            isValide = testConnection.getResult();
            messageException = testConnection.getMessageException();
        } catch (Exception e) {
            log.error(Messages.getString("CommonWizard.exception") + "\n" + e.toString()); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return isValide;
    }

    /**
     * Getter for IsValide.
     * 
     * @return isValide
     */
    public boolean getIsValide() {
        return isValide;
    }

    /**
     * Getter for messageException.
     * 
     * @return the messageException
     */
    public String getMessageException() {
        return this.messageException;
    }

    /**
     * Sets the messageException.
     * 
     * @param messageException the messageException to set
     */
    public void setMessageException(final String messageException) {
        this.messageException = messageException;
    }

    /**
     * Getter for dbRootPath.
     * 
     * @return the dbRootPath
     */
    public String getDbRootPath() {
        return this.dbRootPath;
    }

    /**
     * Sets the dbRootPath.
     * 
     * @param dbRootPath the dbRootPath to set
     */
    public void setDbRootPath(String dbRootPath) {
        if (dbRootPath != null) {
            System.setProperty("derby.system.home", dbRootPath); //$NON-NLS-1$
        }
        this.dbRootPath = dbRootPath;
    }

    public void setUrlConnectionString(String urlConnectionString) {
        this.urlConnectionString = urlConnectionString;
    }

}