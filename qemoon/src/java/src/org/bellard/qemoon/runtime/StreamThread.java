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
/**
 * 
 */
package org.bellard.qemoon.runtime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class StreamThread implements Runnable {

	protected Thread t;

	protected String pid;

	protected Logger logger;

	protected Level level;

	protected InputStream in;

	public StreamThread(String pid, Logger logger, Level level, InputStream in) {
		this.pid = pid;
		this.logger = logger;
		this.level = level;
		this.in = in;
	}

	public void start() {
		t = new Thread(this);
		t.start();
	}

	public void run() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.log(level, pid + " " + line);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				br.close();
			} catch (Exception e) {
			}
		}
	}

}
