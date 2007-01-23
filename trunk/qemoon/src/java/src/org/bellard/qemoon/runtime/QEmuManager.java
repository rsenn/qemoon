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

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public interface QEmuManager {

	public abstract void start();

	public abstract void stop();

	public abstract void resume();

	public abstract void pause();

	public abstract void help();

	public abstract void commit();

	public void networkInfo();

	public void blockInfo();

	public void registersInfo();

	public void historyInfo();

	public void pciInfo();

	public void usbInfo();

	public void usbhostIfo();

	public void captureInfo();

	public void eject(String device, boolean force);

	public void changeDevice(String filename);

	public void screenDump(String filename);

	public void waveCapture(String filename, int frequency, int bits,
			int channels);

	public void stopCapture(int index);

	public void log(String item);

	public void saveVM(String filename);

	public void loadVM(String filename);

	public void gdbserver(int port);

	public void virtualMemoryDump(String format, String addr);

	public void physicalMemoryDump(String format, String addr);

	public void print(String format, String expression);
	
	public void sendKey(String keyCode);
	
	public void systemReset();
	
	public void usbAdd(String deviceName);
	
	public void usbDell(String deviceName);
}