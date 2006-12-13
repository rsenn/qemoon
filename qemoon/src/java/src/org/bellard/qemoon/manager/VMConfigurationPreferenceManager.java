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

import org.bellard.qemoon.preferences.AudioPreferencePage;
import org.bellard.qemoon.preferences.CDROMPreferencePage;
import org.bellard.qemoon.preferences.FloppyPreferencePage;
import org.bellard.qemoon.preferences.HardDiskPreferencePage;
import org.bellard.qemoon.preferences.MemoryPreferencePage;
import org.bellard.qemoon.preferences.QemuImagePreferencePage;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class VMConfigurationPreferenceManager extends PreferenceManager {

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

		PreferenceNode audionode = new PreferenceNode(AUDIO,
				new AudioPreferencePage());
		PreferenceNode memorynode = new PreferenceNode(MEMORY,
				new MemoryPreferencePage());
		PreferenceNode hardisknode = new PreferenceNode(HARDDISK,
				new HardDiskPreferencePage());
		PreferenceNode cdromnode = new PreferenceNode(CDROM,
				new CDROMPreferencePage());
		PreferenceNode floppynode = new PreferenceNode(FLOPPY,
				new FloppyPreferencePage());
		PreferenceNode imagenode = new PreferenceNode(QEMU_IMAGE,
				new QemuImagePreferencePage());
		// PreferenceNode keyboardnode = new PreferenceNode(KEYBOARD,
		// new KeyboardPreferencePage());

		addToRoot(memorynode);
		addToRoot(hardisknode);
		addToRoot(cdromnode);
		addToRoot(floppynode);
		addToRoot(audionode);
		addToRoot(imagenode);
		// addToRoot(keyboardnode);
	}

}
