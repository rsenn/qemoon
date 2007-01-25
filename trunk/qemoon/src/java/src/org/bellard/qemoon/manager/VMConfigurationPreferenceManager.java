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
package org.bellard.qemoon.manager;

import org.apache.log4j.Logger;
import org.bellard.qemoon.Activator;
import org.bellard.qemoon.constants.PreferenceConstants;
import org.bellard.qemoon.preferences.AudioPreferencePage;
import org.bellard.qemoon.preferences.CDROMPreferencePage;
import org.bellard.qemoon.preferences.FloppyPreferencePage;
import org.bellard.qemoon.preferences.HardDiskPreferencePage;
import org.bellard.qemoon.preferences.KeyboardPreferencePage;
import org.bellard.qemoon.preferences.MemoryPreferencePage;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class VMConfigurationPreferenceManager extends PreferenceManager {

	private final static Logger logger = Logger
			.getLogger(VMConfigurationPreferenceManager.class);

	public static final String KEYBOARD = "keyboard";

	public static final String QEMU_IMAGE = "qemuimage";

	public static final String CDROM = "cdrom";

	public static final String HARDDISK = "harddisk";

	public static final String MEMORY = "memory";

	public static final String AUDIO = "audio";

	public static final String FLOPPY = "floppy";

	/**
	 * 
	 */
	public VMConfigurationPreferenceManager() {
		super();
		init();
	}

	public void init() {

		// memory
		createPreferenceNode(MEMORY,
				PreferenceConstants.PREFERENCES_MEMORY_TITLE,
				"icons/22x22/memory.png", MemoryPreferencePage.class);

		// hard disk
		createPreferenceNode(HARDDISK,
				PreferenceConstants.PREFERENCES_HARDDISK_TITLE,
				"icons/22x22/harddisk.png", HardDiskPreferencePage.class);

		// cdrom
		createPreferenceNode(CDROM,
				PreferenceConstants.PREFERENCES_CDROM_TITLE,
				"icons/22x22/cdrom.png", CDROMPreferencePage.class);

		// floppy
		createPreferenceNode(FLOPPY,
				PreferenceConstants.PREFERENCES_FLOPPY_TITLE,
				"icons/22x22/floppy.png", FloppyPreferencePage.class);

		// audio
		createPreferenceNode(AUDIO,
				PreferenceConstants.PREFERENCES_AUDIO_TITLE,
				"icons/22x22/sound.png", AudioPreferencePage.class);

		createPreferenceNode(KEYBOARD,
				PreferenceConstants.PREFERENCES_KEYBOARD_TITLE,
				"icons/22x22/keyboard.png", KeyboardPreferencePage.class);

		// // image
		// PreferenceNode imagenode = new PreferenceNode(QEMU_IMAGE,
		// Activator
		// .getDefault().getMessages().getString(
		// PreferenceConstants.PREFERENCES_IMAGE_TITLE),
		// Activator.getImageDescriptor("icons/qe-22x22.png"),
		// QemuImagePreferencePage.class.getName());
		// imagenode.setPage(new QemuImagePreferencePage());

	}

	protected PreferenceNode createPreferenceNode(String name, String title,
			String imagePath, Class clazz) {
		PreferenceNode node = new PreferenceNode(name, Activator.getDefault()
				.getMessages().getString(title), Activator
				.getImageDescriptor(imagePath), clazz.getName());
		IPreferencePage p = null;
		try {
			p = (IPreferencePage) clazz.newInstance();
		} catch (Exception e) {
			String message = "Pb during preference page instanciation";
			logger.debug(message, e);
			throw new RuntimeException(message, e);
		}
		node.setPage(p);

		addToRoot(node);
		return node;
	}

}
