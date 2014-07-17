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
package org.talend.updates.runtime.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.core.runtime.jobs.Job;
import org.talend.updates.runtime.i18n.Messages;
import org.talend.updates.runtime.model.ExtraFeature;
import org.talend.updates.runtime.model.ExtraFeatureException;
import org.talend.updates.runtime.model.FeatureRepositories;
import org.talend.utils.io.FilesUtils;

/**
 * created by sgandon on 28 févr. 2013 Detailled comment
 * 
 */
public class InstallNewFeatureJob extends Job {

    private final Set<ExtraFeature> featuresToInstall;

    private final FeatureRepositories featureRepositories;

    /**
     * DOC sgandon InstallNewFeatureJob constructor comment.
     * 
     * @param name
     */
    public InstallNewFeatureJob(Set<ExtraFeature> featuresToInstall, FeatureRepositories featureRepositories) {
        super(Messages.getString("installing.talend.new.features")); //$NON-NLS-1$
        this.featuresToInstall = featuresToInstall;
        this.featureRepositories = featureRepositories;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected IStatus run(IProgressMonitor progress) {
        SubMonitor subMon = SubMonitor.convert(progress,
                Messages.getString("installing.talend.new.features"), featuresToInstall.size()); //$NON-NLS-1$
        MultiStatus multiStatus = new MultiStatus(Messages.getPlugiId(), IStatus.OK, null, null);
        try {
            File configIniBackupFile = copyConfigFile(null);
            try {
                // back the config.ini cause the p2 update will modify it but we do not want that
                for (ExtraFeature newFeature : featuresToInstall) {
                    try {
                        // launch the update
                        multiStatus.merge(newFeature.install(subMon.newChild(1), featureRepositories.getAllRepoUris(newFeature)));
                        if (subMon.isCanceled()) {// user canceled so stop the loop and return
                            multiStatus.add(Messages.createCancelStatus("user.cancel.installation.of.feature", //$NON-NLS-1$
                                    newFeature.getName()));
                            break;
                        }
                    } catch (ExtraFeatureException e) {
                        multiStatus.add(Messages.createErrorStatus(e, "failed.to.install", newFeature.getName())); //$NON-NLS-1$
                    } catch (URISyntaxException e) {
                        multiStatus.add(Messages.createErrorStatus(e, "failed.to.install", newFeature.getName())); //$NON-NLS-1$
                    }
                }
            } finally {
                // restore the config.ini
                try {
                    copyConfigFile(configIniBackupFile);
                } catch (IOException e) {
                    multiStatus.add(Messages.createErrorStatus(e, "InstallNewFeatureJob.restore.config.error")); //$NON-NLS-1$
                }
            }
        } catch (IOException e1) {
            multiStatus.add(Messages.createErrorStatus(e1, "InstallNewFeatureJob.back.config.error")); //$NON-NLS-1$
        }
        return multiStatus;
    }

    /**
     * copy the config.ini to a temporary file or vise versa is toRestore is not null
     * 
     * @param toResore file to be copied to config.ini
     * @return the temporary file to restore or null if toRestore is not null
     * @throws IOException
     */
    private File copyConfigFile(File toResore) throws IOException {
        File tempFile = null;
        try {
            URL configLocation = new URL("platform:/config/config.ini"); //$NON-NLS-1$
            URL fileUrl = FileLocator.toFileURL(configLocation);
            File configurationFile = URIUtil.toFile(new URI(fileUrl.getProtocol(), fileUrl.getPath(), fileUrl.getQuery()));
            if (toResore != null) {
                FilesUtils.copyFile(new FileInputStream(toResore), configurationFile);
            } else {
                tempFile = File.createTempFile("config.ini", null); //$NON-NLS-1$
                FilesUtils.copyFile(new FileInputStream(configurationFile), tempFile);
            }
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
        return tempFile;
    }

}
