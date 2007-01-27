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
		if (logger.isDebugEnabled()) {
			logger.debug("start...");
		}
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
		
		// start
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#blockInfo()
	 */
	public void blockInfo() {
		monitor.executeCommand(QEmuMonitor.INFO_COMMAND + " "
				+ QEmuMonitor.BLOCK_ARGUMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#captureInfo()
	 */
	public void captureInfo() {
		monitor.executeCommand(QEmuMonitor.INFO_COMMAND + " "
				+ QEmuMonitor.CAPTURE_ARGUMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#changeDevice(java.lang.String)
	 */
	public void changeDevice(String filename) {
		monitor.executeCommand(QEmuMonitor.CHANGE_COMMAND + " " + filename);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#commit()
	 */
	public void commit() {
		monitor.executeCommand(QEmuMonitor.COMMIT_COMMAND);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#eject(java.lang.String,
	 *      boolean)
	 */
	public void eject(String device, boolean force) {
		String command = QEmuMonitor.EJECT_COMMAND;
		if (force) {
			command += " " + "-f";
		}
		command += " " + device;
		monitor.executeCommand(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#gdbserver(int)
	 */
	public void gdbserver(int port) {
		int p = port;
		if (p == 0) {
			p = 1234;
		}
		monitor.executeCommand(QEmuMonitor.GDBSERVER_COMMAND + " " + p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#help()
	 */
	public void help() {
		monitor.executeCommand(QEmuMonitor.HELP_COMMAND);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#historyInfo()
	 */
	public void historyInfo() {
		monitor.executeCommand(QEmuMonitor.INFO_COMMAND + " "
				+ QEmuMonitor.HISTORY_ARGUMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#loadVM(java.lang.String)
	 */
	public void loadVM(String filename) {
		monitor.executeCommand(QEmuMonitor.LOADVM_COMMAND + " " + filename);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#log(java.lang.String)
	 */
	public void log(String item) {
		monitor.executeCommand(QEmuMonitor.LOG_COMMAND + " " + item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#networkInfo()
	 */
	public void networkInfo() {
		monitor.executeCommand(QEmuMonitor.INFO_COMMAND + " "
				+ QEmuMonitor.NETWORK_ARGUMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#pciInfo()
	 */
	public void pciInfo() {
		monitor.executeCommand(QEmuMonitor.INFO_COMMAND + " "
				+ QEmuMonitor.PCI_ARGUMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#physicalMemoryDump(java.lang.String,
	 *      java.lang.String)
	 */
	public void physicalMemoryDump(String format, String addr) {
		monitor.executeCommand(QEmuMonitor.PMEMORYDUMP_COMMAND + format + " "
				+ addr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#print(java.lang.String,
	 *      java.lang.String)
	 */
	public void print(String format, String expression) {
		monitor.executeCommand(QEmuMonitor.PRINT_COMMAND + format + " "
				+ expression);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#registersInfo()
	 */
	public void registersInfo() {
		monitor.executeCommand(QEmuMonitor.INFO_COMMAND + " "
				+ QEmuMonitor.REGISTERS_ARGUMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#saveVM(java.lang.String)
	 */
	public void saveVM(String filename) {
		monitor.executeCommand(QEmuMonitor.SAVEVM_COMMAND + " " + filename);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#screenDump(java.lang.String)
	 */
	public void screenDump(String filename) {
		monitor.executeCommand(QEmuMonitor.SCREENDUMP_COMMAND + " " + filename);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#sendKey(java.lang.String)
	 */
	public void sendKey(String keyCode) {
		monitor.executeCommand(QEmuMonitor.SENDKEY_COMMAND + " " + keyCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#stopCapture(int)
	 */
	public void stopCapture(int index) {
		monitor.executeCommand(QEmuMonitor.STOP_CAPTURE_COMMAND + " " + index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#systemReset()
	 */
	public void systemReset() {
		monitor.executeCommand(QEmuMonitor.SYSTEMRESET_COMMAND);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#usbAdd(java.lang.String)
	 */
	public void usbAdd(String deviceName) {
		monitor.executeCommand(QEmuMonitor.USBADD_COMMAND + " " + deviceName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#usbDell(java.lang.String)
	 */
	public void usbDell(String deviceName) {
		monitor.executeCommand(QEmuMonitor.USBDEL_COMMAND + " " + deviceName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#usbhostIfo()
	 */
	public void usbhostIfo() {
		monitor.executeCommand(QEmuMonitor.INFO_COMMAND
				+ QEmuMonitor.USBHOST_ARGUMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#usbInfo()
	 */
	public void usbInfo() {
		monitor.executeCommand(QEmuMonitor.INFO_COMMAND
				+ QEmuMonitor.USB_ARGUMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#virtualMemoryDump(java.lang.String,
	 *      java.lang.String)
	 */
	public void virtualMemoryDump(String format, String addr) {
		monitor.executeCommand(QEmuMonitor.VMEMORYDUMP_COMMAND + format + " "
				+ addr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.runtime.QEmuManager#waveCapture(java.lang.String,
	 *      int, int, int)
	 */
	public void waveCapture(String filename, int frequency, int bits,
			int channels) {
		monitor.executeCommand(QEmuMonitor.WAVCAPTURE_COMMAND + " " + filename
				+ " " + frequency + " " + bits + " " + channels);

	}

}
