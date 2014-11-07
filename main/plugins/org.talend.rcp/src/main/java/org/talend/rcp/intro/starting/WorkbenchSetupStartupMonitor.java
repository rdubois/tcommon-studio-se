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
package org.talend.rcp.intro.starting;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.osgi.service.runnable.StartupMonitor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.talend.rcp.intro.PerspectiveReviewUtil;

/**
 * created by sgandon on 6 nov. 2014 Detailled comment
 *
 */
public class WorkbenchSetupStartupMonitor implements StartupMonitor {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.osgi.service.runnable.StartupMonitor#update()
     */
    @Override
    public void update() {
        //
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.osgi.service.runnable.StartupMonitor#applicationRunning()
     */
    @Override
    public void applicationRunning() {
        if (!PlatformUI.isWorkbenchRunning()) { // if not running, nothing to do.
            return;
        }
        IWorkbench workbench = PlatformUI.getWorkbench();
        PerspectiveReviewUtil perspectiveReviewUtil = new PerspectiveReviewUtil();
        IEclipseContext activeContext = ((IEclipseContext) workbench.getService(IEclipseContext.class)).getActiveLeaf();
        ContextInjectionFactory.inject(perspectiveReviewUtil, activeContext);

        perspectiveReviewUtil.checkPerspectiveDisplayItems();
    }

}
