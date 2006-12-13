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
package org.bellard.qemoon.model;


/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class VMConfiguration {

	private VM vim;

	public VMConfiguration() {
	}

	public String getName() {
		String name = "";
		if (vim != null) {
			name = vim.getName();
		}
		return name;
	}

	public void setName(String n) {

		if (vim == null) {
			VM vm = new VM(n);
			vm.setConfiguration(this);
		} else {
			vim.setName(n);
		}
	}

	/**
	 * @return the vim
	 */
	public VM getVim() {
		return vim;
	}

	/**
	 * @param vim
	 *            the vim to set
	 */
	public void setVim(VM vim) {
		this.vim = vim;
	}

	public String toString() {
		String s = "";
		if (vim != null)
			;
		s = vim.toString();
		return getClass().getSimpleName() + "(name=" + getName() + ",vim=" + s
				+ ")";
	}

}
