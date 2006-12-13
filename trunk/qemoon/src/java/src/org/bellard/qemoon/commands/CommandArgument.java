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
package org.bellard.qemoon.commands;

import org.bellard.qemoon.utils.ValidatorUtils;

/**
 * @author France Telecom
 * @Copyright France Telecom S.A. 2006
 */
public class CommandArgument {

	private String argumentPrefix = "-";

	private String argumentSeparator = " ";

	private String nameValueSeparator = " ";

	private String commandLabel;

	private String key;

	private Object value;

	public CommandArgument(String commandlabel, String key, Object value) {
		setCommandLabel(commandlabel);
		setKey(key);
		setValue(value);
	}

	public String getNameValueSeparator() {
		return nameValueSeparator;
	}

	public String getArgumentPrefix() {
		return argumentPrefix;
	}

	public String getArgumentSeparator() {
		return argumentSeparator;
	}

	public String getCommandArgument() {
		return getArgumentPrefix() + getCommandLabel()
				+ getNameValueSeparator() + getValue();
	}

	public String[] getCommandArguments() {
		if (ValidatorUtils.isEmptyOrNull(getCommandLabel())) {
			return new String[] { getValue().toString() };
		}

		return new String[] { getArgumentPrefix() + getCommandLabel(),
				getValue().toString() };
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	/**
	 * @return the commandLabel
	 */
	public String getCommandLabel() {
		return this.commandLabel;
	}

	/**
	 * @param commandLabel
	 *            the commandLabel to set
	 */
	public void setCommandLabel(String label) {
		this.commandLabel = label;
	}

	/**
	 * @param argumentPrefix
	 *            the argumentPrefix to set
	 */
	public void setArgumentPrefix(String argumentPrefix) {
		this.argumentPrefix = argumentPrefix;
	}

	/**
	 * @param argumentSeparator
	 *            the argumentSeparator to set
	 */
	public void setArgumentSeparator(String argumentSeparator) {
		this.argumentSeparator = argumentSeparator;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String name) {
		this.key = name;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @param nameValueSeparator
	 *            the nameValueSeparator to set
	 */
	public void setNameValueSeparator(String nameValueSeparator) {
		this.nameValueSeparator = nameValueSeparator;
	}

	public String toString() {
		return this.getClass().getSimpleName() + "(key=" + getKey()
				+ ", commandLabel=" + commandLabel + ", value=" + value + ")";
	}

}
