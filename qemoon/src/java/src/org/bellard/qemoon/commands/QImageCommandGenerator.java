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

import java.util.ArrayList;
import java.util.List;

import org.bellard.qemoon.utils.ValidatorUtils;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class QImageCommandGenerator {

	/** command */
	public final static int CREATE_COMMAND = 1;

	public final static int COMMIT_COMMAND = 2;

	public final static int CONVERT_COMMAND = 3;

	public final static int INFO_COMMAND = 4;

	/** format */
	public final static int DEFAULT_FORMAT = 0;

	public final static int RAW_FORMAT = 1;

	public final static int QCOW_FORMAT = 2;

	public final static int COW_FORMAT = 3;

	public final static int VMDK_FORMAT = 4;

	public final static int CLOOP_FORMAT = 5;

	protected int command;

	protected int size;

	protected String filename;

	protected String qemuimgpath;

	protected List<String> options;

	protected QImageCommandGenerator(String qemuimgpath, int command,
			List<String> options) {
		this.qemuimgpath = qemuimgpath;
		this.command = command;
		this.options = options;
	}

	public String[] buildCommandArgument() {
		List<String> arguments = new ArrayList<String>();

		arguments.add(qemuimgpath);
		switch (command) {
		case CREATE_COMMAND:
			arguments.add("create");
			break;
		case COMMIT_COMMAND:
			arguments.add("commit");
			break;
		case CONVERT_COMMAND:
			arguments.add("convert");
			break;
		case INFO_COMMAND:
			arguments.add("info");
			break;
		default:
		}

		arguments.addAll(options);

		return arguments.toArray(new String[] {});
	}

	public final static QImageCommandGenerator buildCreateCommand(
			String qemuimgpath, int size, String filename, int format,
			boolean encrypt, String baseimage) {
		List<String> options = new ArrayList<String>();
		/* `create [-e] [-b base_image] [-f fmt] filename [size]' */

		if (encrypt) {
			options.add("-e");
		}
		if (!ValidatorUtils.isEmptyOrNull(baseimage)) {
			options.add("-b");
			options.add(baseimage);
		}

		options.addAll(buildFormat(format));

		options.add(filename);

		if (size != 0) {
			options.add("" + size + "M");
		}

		QImageCommandGenerator imageGenerator = new QImageCommandGenerator(
				qemuimgpath, CREATE_COMMAND, options);
		return imageGenerator;
	}

	protected final static List<String> buildFormat(int format) {
		List<String> formats = new ArrayList<String>();
		if (format == DEFAULT_FORMAT) {
			return formats;

		}
		formats.add("-f");
		switch (format) {
		case RAW_FORMAT:
			formats.add("raw");
			break;
		case QCOW_FORMAT:
			formats.add("qcow");
			break;
		case COW_FORMAT:
			formats.add("cow");
			break;
		case VMDK_FORMAT:
			formats.add("vmdk");
			break;
		case CLOOP_FORMAT:
			formats.add("cloop");
			break;
		}
		return formats;
	}

	public final static QImageCommandGenerator buildCommitCommand(
			String qemuimgpath, String filename, int format) {

		/* `commit [-f fmt] filename' */

		List<String> options = buildFormat(format);
		options.add(filename);
		QImageCommandGenerator imageGenerator = new QImageCommandGenerator(
				qemuimgpath, COMMIT_COMMAND, options);
		return imageGenerator;
	}

	public final static QImageCommandGenerator buildInfoCommand(
			String qemuimgpath, String filename, int format) {

		/* `info [-f fmt] filename' */
		List<String> options = buildFormat(format);
		options.add(filename);
		QImageCommandGenerator imageGenerator = new QImageCommandGenerator(
				qemuimgpath, INFO_COMMAND, options);
		return imageGenerator;
	}

	public final static QImageCommandGenerator buildConvertCommand(
			String qemuimgpath, String filename, int format) {

		/* `convert [-c] [-e] [-f fmt] filename [-O output_fmt] output_filename' */

		// TODO implement buildConvertCommand
		return null;

	}

}
