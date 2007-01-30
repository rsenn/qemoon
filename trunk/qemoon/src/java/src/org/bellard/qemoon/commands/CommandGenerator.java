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
package org.bellard.qemoon.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bellard.qemoon.Activator;
import org.bellard.qemoon.constants.Configuration2Constants;
import org.bellard.qemoon.constants.PreferenceConstants;
import org.bellard.qemoon.utils.ValidatorUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;

public class CommandGenerator {

	public static final String CONFIGURATION_FDA = "configuration.fdb";

	public static final String CONFIGURATION_FDB = "configuration.fda";

	public static final String CONFIGURATION_HDA = "configuration.hda";

	public static final String CONFIGURATION_HDB = "configuration.hdb";

	public static final String CONFIGURATION_HDC = "configuration.hdc";

	public static final String CONFIGURATION_HDD = "configuration.hdd";

	public static final String CONFIGURATION_CDROM = "configuration.cdrom";

	public static final String CONFIGURATION_MEMORY = "configuration.memory";

	public static final String CONFIGURATION_AUDIO = "configuration.audio";

	public static final String CONFIGURATION_KEYBOARD = "configuration.keyboard";

	public static final String CONFIGURATION_MONITOR = "configuration.monitor";

	public static final String CONFIGURATION_SNAPSHOT = "configuration.snapshot";

	public static final String CONFIGURATION_QEMULIB = "configuration.qemulib";

	private final static Logger logger = Logger
			.getLogger(CommandGenerator.class);

	private IPreferenceStore store;

	private String qemuPath;

	private String vmName;

	public CommandGenerator(String qemuPath, IPreferenceStore store,
			String vmName) {
		this.store = store;
		this.qemuPath = qemuPath;
		this.vmName = vmName;
	}

	public List<CommandArgument> buildCommandArgumentList() {

		PreferenceStore p = Activator.getDefault().getOptions();
		List<CommandArgument> l = new ArrayList<CommandArgument>();
		CommandArgument c = null;

		// qemu path

		if (ValidatorUtils.isEmptyOrNull(qemuPath)) {
			return null;
		}
		c = new SimpleCommandArgument(qemuPath);
		l.add(c);

		// qemu libs (windows)
		boolean isQemuCustom = Activator.getDefault().getPreferenceStore()
				.getBoolean(Configuration2Constants.QEMU_CUSTOM);
		logger.debug("qemu custom = " + isQemuCustom);
		if (!isQemuCustom && Platform.OS_WIN32.equals(Platform.getOS())) {
			// TODO make it better
			String qemuPathDir = new File(qemuPath).getParent();
			c = new SimpleCommandArgument("-"
					+ p.getString(CONFIGURATION_QEMULIB));
			l.add(c);
			c = new SimpleCommandArgument(qemuPathDir);
			l.add(c);
		}

		// memory
		if (!ValidatorUtils.isEmptyOrNull(store
				.getString(Configuration2Constants.MEMORY_VALUE))) {
			c = new CommandArgument(p.getString(CONFIGURATION_MEMORY),
					Configuration2Constants.MEMORY_VALUE, store
							.getString(Configuration2Constants.MEMORY_VALUE));
			l.add(c);
		}

		// cdrom
		if (!ValidatorUtils.isEmptyOrNull(store
				.getString(Configuration2Constants.CD_VALUE))) {
			c = new CommandArgument(p.getString(CONFIGURATION_CDROM),
					Configuration2Constants.CD_VALUE, store
							.getString(Configuration2Constants.CD_VALUE));
			l.add(c);
		}

		// hard disk
		if (!ValidatorUtils.isEmptyOrNull(store
				.getString(Configuration2Constants.HDA_VALUE))) {
			c = new CommandArgument(p.getString(CONFIGURATION_HDA),
					Configuration2Constants.HDA_VALUE, store
							.getString(Configuration2Constants.HDA_VALUE));
			l.add(c);
		}
		if (!ValidatorUtils.isEmptyOrNull(store
				.getString(Configuration2Constants.HDB_VALUE))) {
			c = new CommandArgument(p.getString(CONFIGURATION_HDB),
					Configuration2Constants.HDB_VALUE, store
							.getString(Configuration2Constants.HDB_VALUE));
			l.add(c);
		}
		if (!ValidatorUtils.isEmptyOrNull(store
				.getString(Configuration2Constants.HDC_VALUE))) {
			c = new CommandArgument(p.getString(CONFIGURATION_HDC),
					Configuration2Constants.HDC_VALUE, store
							.getString(Configuration2Constants.HDC_VALUE));
			l.add(c);
		}
		if (!ValidatorUtils.isEmptyOrNull(store
				.getString(Configuration2Constants.HDD_VALUE))) {
			c = new CommandArgument(p.getString(CONFIGURATION_HDD),
					Configuration2Constants.HDD_VALUE, store
							.getString(Configuration2Constants.HDD_VALUE));
			l.add(c);
		}

		// fda
		if (!ValidatorUtils.isEmptyOrNull(store
				.getString(Configuration2Constants.FDA_VALUE))) {
			c = new CommandArgument(p.getString(CONFIGURATION_FDA),
					Configuration2Constants.FDA_VALUE, store
							.getString(Configuration2Constants.FDA_VALUE));
			l.add(c);
		}

		// fdb
		if (!ValidatorUtils.isEmptyOrNull(store
				.getString(Configuration2Constants.FDB_VALUE))) {
			c = new CommandArgument(p.getString(CONFIGURATION_FDB),
					Configuration2Constants.FDB_VALUE, store
							.getString(Configuration2Constants.FDB_VALUE));
			l.add(c);
		}

		// audio
		if (store.getBoolean(Configuration2Constants.AUDIO_ENABLE)) {

			if (!ValidatorUtils.isEmptyOrNull(store
					.getString(Configuration2Constants.AUDIO_VALUE))) {
				c = new CommandArgument(p.getString(CONFIGURATION_AUDIO),
						Configuration2Constants.AUDIO_VALUE, store
								.getString(Configuration2Constants.AUDIO_VALUE));
				l.add(c);

			}

		}

		// keyboard
		if (!ValidatorUtils.isEmptyOrNull(store
				.getString(Configuration2Constants.KEYBOARD_VALUE))) {
			c = new CommandArgument(p.getString(CONFIGURATION_KEYBOARD),
					Configuration2Constants.KEYBOARD_VALUE, store
							.getString(Configuration2Constants.KEYBOARD_VALUE));
			l.add(c);

		}

		// monitor
		// AUTOMATIC ASSIGNMENT : it allows to control qemu from qemoon
		// get a free port number
		int port = Activator.getDefault().getPortManager().getNextPort(vmName);
		boolean socketServer = Activator
				.getDefault()
				.getPreferenceStore()
				.getBoolean(
						PreferenceConstants.PREFERENCES_MONITOR_SOCKETSERVER_VALUE);
		String monitoroption = "tcp:127.0.0.1:" + port;
		if (!socketServer) {
			monitoroption += ",server";
		}
		c = new CommandArgument(p.getString(CONFIGURATION_MONITOR),
				Configuration2Constants.MONITOR_VALUE, monitoroption);
		l.add(c);

		// MUST BE THE LAST
		// image or snapshot image command
		String image = null;
		if (store.getBoolean(Configuration2Constants.IMAGE_CUSTOM)) {
			image = store.getString(Configuration2Constants.IMAGE_VALUE);
		} else {
			// use default image path
			image = store
					.getString(Configuration2Constants.IMAGE_DEFAULT_VALUE);
		}

		if (store.getBoolean(Configuration2Constants.SNAPSHOT_VALUE)) {
			c = new CommandArgument(p.getString(CONFIGURATION_SNAPSHOT),
					Configuration2Constants.IMAGE_VALUE, image);
		} else {
			c = new SimpleCommandArgument(image);

		}
		l.add(c);

		return l;
	}

	public String[] buidCommandArgument() {
		List<CommandArgument> l = buildCommandArgumentList();

		List<String> arguments = new ArrayList<String>();

		for (CommandArgument c : l) {
			String[] stab = c.getCommandArguments();
			for (String s : stab) {
				arguments.add(s);
			}
		}

		String[] commandLine = new String[arguments.size()];

		for (int i = 0; i < arguments.size(); i++) {
			commandLine[i] = arguments.get(i);
		}
		if (logger.isDebugEnabled()) {
			StringBuffer buffer = new StringBuffer();
			for (String s : commandLine)
				buffer.append(s + " ");
			logger.debug(buffer.toString());
		}
		return commandLine;
	}
}
