/*
 * ========================================================================
 *
 * qemoon - a gui frontend for the qemu emulator written in the java programming language with the eclipse rcp framework.
 * Copyright (C) 2006 Eric Bellard.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * ========================================================================
 */
package org.bellard.qemoon;

import java.util.HashMap;
import java.util.Map;

import org.bellard.qemoon.constants.PreferenceConstants;
import org.bellard.qemoon.model.VM;
import org.bellard.qemoon.monitor.QEmuMonitor;
import org.bellard.qemoon.network.PortManager;
import org.bellard.qemoon.resources.MessageBundle;
import org.bellard.qemoon.resources.PreferenceDefaultBundle;
import org.bellard.qemoon.runtime.QEmuManager;
import org.bellard.qemoon.runtime.impl.QEmuManagerImpl;
import org.bellard.qemoon.views.VMNavigationView;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	private final static String OPTIONS = "options.properties";

	// The plug-in ID
	public static final String PLUGIN_ID = "qemoon";

	// The shared instance
	private static Activator plugin;

	private PreferenceStore options;

	private ImageManager imageManager;

	private PortManager portManager;

	private QEmuMonitor qemuMonitor;

	private MessageBundle messages;

	private PreferenceDefaultBundle preferenceDefault;

	private Map<String, QEmuManager> qemuManagers = new HashMap<String, QEmuManager>();

	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;

	}

	public PreferenceStore getOptions() {
		return options;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		preferenceDefault = new PreferenceDefaultBundle();
		options = new PreferenceStore();
		options.load(FileLocator.openStream(getBundle(), new Path(OPTIONS),
				false));
		imageManager = new ImageManager();
		portManager = new PortManager(getPreferenceStore().getInt(
				PreferenceConstants.PREFERENCES_MONITOR_PORT_VALUE));
		qemuMonitor = new QEmuMonitor();
		messages = new MessageBundle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		imageManager = null;
		portManager = null;
		qemuMonitor = null;
		messages = null;
		preferenceDefault = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public ImageManager getImageManager() {
		return imageManager;
	}

	/**
	 * @return the portManager
	 */
	public PortManager getPortManager() {
		return portManager;
	}

	/**
	 * @return the qemuMonitor
	 */
	public QEmuMonitor getQemuMonitor() {
		return qemuMonitor;
	}

	public VMNavigationView getVMNavigationView() {
		return (VMNavigationView) Activator.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().findView(
						VMNavigationView.ID);
	}

	/**
	 * @return the messages
	 */
	public MessageBundle getMessages() {
		return messages;
	}

	/**
	 * @return the preferenceDefault
	 */
	public PreferenceDefaultBundle getPreferenceDefault() {
		return preferenceDefault;
	}

	public IPath getPlatformPath() {
		return new Path(Platform.getInstallLocation().getURL().getFile()
				.toString());
	}

	public QEmuManager createQEmuManager(VM vm) {
		QEmuManager manager = new QEmuManagerImpl(vm);
		qemuManagers.put(vm.getName(), manager);
		return manager;
	}

	public QEmuManager getQEmuManager(VM vm) {
		QEmuManager manager = qemuManagers.get(vm.getName());
		if (manager == null) {
			manager = createQEmuManager(vm);
		}
		return manager;
	}

}
