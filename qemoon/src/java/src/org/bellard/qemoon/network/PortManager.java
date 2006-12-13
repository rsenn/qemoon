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
package org.bellard.qemoon.network;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class PortManager {

	private int nextPort;

	private Map<String, Integer> namePort = new HashMap<String, Integer>();

	public PortManager(int nextport) {
		this.nextPort = nextport;
	}

	public int getNextPort(String processname) {
		int port = nextPort;
		// TODO check that the port is free
		if (namePort.get(processname)!=null) {
			port = namePort.get(processname);
		} else {
			nextPort++;
			namePort.put(processname, port);
		}
		return port;
	}
	
	
}
