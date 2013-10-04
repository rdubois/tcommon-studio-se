package org.talend.librariesmanager.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.librariesmanager.model.ModulesNeededProvider;
import org.talend.librariesmanager.ui.dialogs.ExternalModulesInstallDialogWithProgress;
import org.talend.librariesmanager.ui.i18n.Messages;
import org.talend.osgi.hook.notification.JarMissingObservable;
import org.talend.osgi.hook.notification.JarMissingObservable.JarMissingEvent;

/**
 * created by sgandon on 17 sept. 2013 This register a listener to be notified when a jar is missing when activating an
 * OSGI bundle. When the notification happend, it show the Modules dialog box to allow the user to download the required
 * jars.
 * 
 */
public class InitializeMissingJarHandler implements IStartup, Observer {

    private static Logger log = Logger.getLogger(InitializeMissingJarHandler.class);

    private List<ModuleNeeded> allModulesNeededExtensionsForPlugin;

    @Override
    public void earlyStartup() {
        setupMissingJarLoadingObserver();
    }

    /**
     * looks for the OSGI service that notify that a jar is missing when loading a bundle and register this as a
     * listener
     */
    @SuppressWarnings("restriction")
    private void setupMissingJarLoadingObserver() {
        BundleContext bundleContext = getBundleContext();
        if (bundleContext != null) {
            ServiceReference serviceReference = bundleContext.getServiceReference(JarMissingObservable.class.getCanonicalName());
            if (serviceReference != null) {
                JarMissingObservable missingJarObservable = (JarMissingObservable) bundleContext.getService(serviceReference);
                missingJarObservable.addObserver(this);
            } else {// could not find the hook registry service so log it
                log.error("Could not find a registered OSGI service for : " + "java.util.Observable");
            }
        } else {// bundleContext is null should never happend but log it
            log.error("Could not get bundle context for : " + this.getClass());
        }
    }

    /**
     * @return the current bundle BundleContext
     */
    public BundleContext getBundleContext() {
        Bundle bundle = FrameworkUtil.getBundle(this.getClass());
        BundleContext bundleContext = bundle.getBundleContext();
        return bundleContext;
    }

    /**
     * called when the jar loadingin hook has failed to find the jar this is never called in a GUI thread.
     * 
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg instanceof JarMissingEvent) {
            final JarMissingEvent jarMissingEvent = (JarMissingEvent) arg;
            showMissingModuleDialog(jarMissingEvent);
        } else {// notification is not expected so log it
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(
                    "was expecting a type :" + JarMissingEvent.class.getCanonicalName()); //$NON-NLS-1$
            illegalArgumentException.fillInStackTrace();
            log.error("Could not find the proper JarMissing values", illegalArgumentException); //$NON-NLS-1$
        }
    }

    /**
     * look for all the required modules for a given bundle, and let the user decide to download it. this method is
     * blocked until the dialog box is closed.
     * 
     * @param jarMissingEvent, must never be null
     */
    protected void showMissingModuleDialog(final JarMissingEvent jarMissingEvent) {
        if (allModulesNeededExtensionsForPlugin == null) {
            this.allModulesNeededExtensionsForPlugin = ModulesNeededProvider.getAllModulesNeededExtensionsForPlugin();
        }
        List<ModuleNeeded> requiredModulesForBundle = ModulesNeededProvider.filterRequiredModulesForBundle(
                jarMissingEvent.getBundleSymbolicName(), allModulesNeededExtensionsForPlugin);
        final List<String> requiredJars = new ArrayList<String>(requiredModulesForBundle.size());
        // filter the jar that are already installed
        for (ModuleNeeded module : requiredModulesForBundle) {
            String moduleName = module.getModuleName();
            if (!new File(jarMissingEvent.getExpectedLibFolder(), moduleName).exists()) {
                requiredJars.add(moduleName);
            }// else jar already installed to filter it by ignoring it.
        }
        if (!requiredJars.isEmpty()) {
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    ExternalModulesInstallDialogWithProgress dialog = new ExternalModulesInstallDialogWithProgress(
                            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                            Messages.getString("ExternalModulesInstallDialog_Title_Missing_jars_for_plugin"), //$NON-NLS-1$
                            Messages.getString("ExternalModulesInstallDialog_description_jars_to_be_installed_in"), SWT.APPLICATION_MODAL); //$NON-NLS-1$
                    dialog.showDialog(true, requiredJars.toArray(new String[requiredJars.size()]));
                }
            });
        }// else there is not extension point defining the required bundles so do not ask the user ignor.
    }
}
