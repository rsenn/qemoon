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
package org.bellard.qemoon.runtime;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class RuntimeWrapper {

	private String pid;

	private Logger logger = null;

	private Process qemuProcess = null;
	
	public static void main(String[] args) throws Exception {
		new RuntimeWrapper().exec(args);
	}

	public RuntimeWrapper() {
		this(null, Logger.getRootLogger());
	}

	public RuntimeWrapper(String pid, Logger logger) {
		if (pid == null) {
			this.pid = Thread.currentThread().getName();
		} else {
			this.pid = pid;
		}
		this.logger = logger;
	}

	public int exec(String[] cmdarray) throws IOException, InterruptedException {
		return exec(cmdarray, null);
	}

	public int exec(String[] cmdarray, String[] envp) throws IOException,
			InterruptedException {
		StringBuffer cmdString = new StringBuffer();
		for (int i = 0; i < cmdarray.length; i++) {
			cmdString.append(cmdarray[i]).append(' ');
		}
		logger.info(pid + " " + cmdString.toString());
		qemuProcess = null;
		if (envp != null) {
			qemuProcess = Runtime.getRuntime().exec(cmdarray, envp);
		} else {
			qemuProcess = Runtime.getRuntime().exec(cmdarray);
		}
		StreamThread in = new StreamThread(pid, logger, Level.INFO, qemuProcess
				.getInputStream());
		StreamThread err = new StreamThread(pid, logger, Level.WARN, qemuProcess
				.getErrorStream());
		in.start();
		err.start();
		int exitValue = qemuProcess.waitFor();
		String msg = "Shell exited with status " + exitValue;
		if (exitValue != 0) {
			logger.error(pid + " " + msg);
		} else {
			logger.info(pid + " " + msg);
		}
		return exitValue;
	}

	/**
	 * @return the qemuProcess
	 */
	public Process getQemuProcess() {
		return qemuProcess;
	}

	/**
	 * @param qemuProcess the qemuProcess to set
	 */
	public void setQemuProcess(Process qemuProcess) {
		this.qemuProcess = qemuProcess;
	}

}
