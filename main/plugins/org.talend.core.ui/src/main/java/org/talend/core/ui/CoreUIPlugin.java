// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.IService;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.core.ui.services.IDesignerCoreUIService;
import org.talend.core.ui.services.IOpenJobScriptActionService;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.repository.model.IMetadataService;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryService;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class CoreUIPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "org.talend.core.ui"; //$NON-NLS-1$

    // The shared instance
    private static CoreUIPlugin plugin;

    public CoreUIPlugin() {
        plugin = this;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext contextP) throws Exception {
        super.start(contextP);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext contextP) throws Exception {
        plugin = null;
        super.stop(contextP);
    }

    public static CoreUIPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in relative path.
     * 
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    public IProxyRepositoryFactory getProxyRepositoryFactory() {
        IRepositoryService service = CoreRuntimePlugin.getInstance().getRepositoryService();
        return service.getProxyRepositoryFactory();
    }

    public IRunProcessService getRunProcessService() {
        IService service = GlobalServiceRegister.getDefault().getService(IRunProcessService.class);
        return (IRunProcessService) service;
    }

    public IDesignerCoreService getDesignerCoreService() {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
            IService service = GlobalServiceRegister.getDefault().getService(IDesignerCoreService.class);
            return (IDesignerCoreService) service;
        }
        return null;
    }

    public IDesignerCoreUIService getDesignerCoreUIService() {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreUIService.class)) {
            IService service = GlobalServiceRegister.getDefault().getService(IDesignerCoreUIService.class);
            return (IDesignerCoreUIService) service;
        }
        return null;
    }

    public IOpenJobScriptActionService getOpenJobScriptActionService() {
        return (IOpenJobScriptActionService) GlobalServiceRegister.getDefault().getService(IOpenJobScriptActionService.class);
    }

    public IMetadataService getMetadataService() {
        IService service = GlobalServiceRegister.getDefault().getService(IMetadataService.class);
        return (IMetadataService) service;
    }
}
