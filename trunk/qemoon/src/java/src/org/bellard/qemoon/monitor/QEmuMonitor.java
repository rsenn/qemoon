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
package org.bellard.qemoon.monitor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class QEmuMonitor {

	public final static String QUIT_COMMAND = "quit";

	public final static String HELP_COMMAND = "help";

	public final static String COMMIT_COMMAND = "commit";

	public final static String INFO_COMMAND = "info";

	public final static String EJECT_COMMAND = "eject";

	public final static String CHANGE_COMMAND = "change";

	public final static String SCREENDUMP_COMMAND = "screendump";

	public final static String WAVCAPTURE_COMMAND = "wavcapture";

	public final static String LOG_COMMAND = "log";

	public final static String LOADVM_COMMAND = "loadvm";

	public final static String SAVEVM_COMMAND = "savevm";

	public final static String STOP_COMMAND = "stop";

	public final static String RESUME_COMMAND = "cont";

	public final static String GDBSERVER_COMMAND = "gdbserver";

	public final static String VMEMORYDUMP_COMMAND = "x/fmt";

	public final static String PMEMORYDUMP_COMMAND = "xp/fmt";

	public final static String PRINT_COMMAND = "print";

	public final static String SENDKEY_COMMAND = "sendkey";

	public final static String SYSTEMRESET_COMMAND = "system_reset";

	public final static String USBADD_COMMAND = "usb_add";

	public final static String USBDEL_COMMAND = "usb_del";

	public final static String FORCE_ARGUMENT = "-f";

	public final static String NETWORK_ARGUMENT = "network";

	public final static String BLOCK_ARGUMENT = "block";

	public final static String HISTORY_ARGUMENT = "history";

	public final static String REGISTERS_ARGUMENT = "registers";

	public final static String PCI_ARGUMENT = "pci";

	public final static String USB_ARGUMENT = "usb";

	public final static String USBHOST_ARGUMENT = "usbhost";

	public final static String CAPTURE_ARGUMENT = "capture";

	private Map<String, NetworkMonitor> map = new HashMap<String, NetworkMonitor>();

	public QEmuMonitor() {

	}

	public void putNetworkMonitor(String name, NetworkMonitor monitor) {
		map.put(name, monitor);
	}

	public NetworkMonitor removeNetworkMonitor(String name) {
		return map.remove(name);
	}

	public NetworkMonitor getNetworkMonitor(String name) {
		return map.get(name);
	}

	public NetworkMonitor createNetworkMonitor(String name, 
			int port) {
		NetworkMonitor monitor = new NetworkMonitor(port);
		putNetworkMonitor(name, monitor);
		return monitor;
	}

}
