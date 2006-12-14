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
package org.bellard.qemoon.runtime.impl;

import org.apache.log4j.Logger;
import org.bellard.qemoon.Activator;
import org.bellard.qemoon.constants.PreferenceConstants;
import org.bellard.qemoon.model.VM;
import org.bellard.qemoon.monitor.NetworkMonitor;
import org.bellard.qemoon.monitor.QEmuMonitor;
import org.bellard.qemoon.runtime.QEmuManager;
import org.bellard.qemoon.runtime.QemuThread;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class QEmuManagerImpl implements QEmuManager {

	private final static Logger logger = Logger
			.getLogger(QEmuManagerImpl.class);

	private VM vm;

	private QemuThread qemuthread;

	private NetworkMonitor monitor;

	public QEmuManagerImpl(VM vim) {
		this.vm = vim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.impl.QEmuManage#start()
	 */
	public void start() {

		Activator activator = Activator.getDefault();
		String qemupath = activator.getPreferenceStore().getString(
				PreferenceConstants.PREFERENCES_QEMU_PATH_VALUE);

		qemuthread = new QemuThread(vm, qemupath, true, Logger.getLogger(vm
				.getName()));

		qemuthread.addObserver(vm.getExecutionObserver());

		boolean socketServer = activator.getPreferenceStore().getBoolean(
				PreferenceConstants.PREFERENCES_MONITOR_SOCKETSERVER_VALUE);

		monitor = activator.getQemuMonitor().createNetworkMonitor(vm.getName(),
				activator.getPortManager().getNextPort(vm.getName()),
				socketServer);
		if (socketServer) {
			monitor.start();
		}
		qemuthread.start();
		if (!socketServer) {
			monitor.start();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.impl.QEmuManage#stop()
	 */
	public void stop() {
		monitor.executeCommand(QEmuMonitor.QUIT_COMMAND);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.impl.QEmuManage#resume()
	 */
	public void resume() {
		monitor.executeCommand(QEmuMonitor.RESUME_COMMAND);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.impl.QEmuManage#pause()
	 */
	public void pause() {
		monitor.executeCommand(QEmuMonitor.STOP_COMMAND);

	}

}
