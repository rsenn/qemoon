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
package org.bellard.qemoon.constants;

/**
 * @author France Telecom
 * @Copyright France Telecom S.A. 2006
 */
public interface ConfigurationConstants {

	public final static String CONFIG_GENERAL_NAME = "configuration.general.name";
	public static final String CONFIGURATION_GENERAL_DESCRIPTION = "configuration.general.description";
	public static final String CONFIGURATION_GENERAL_TITLE = "configuration.general.title";
	public static final String CONFIGURATION_ADVANCED_W2KHACK = "configuration.advanced.w2khack";
	public static final String CONFIGURATION_ADVANCED_LOCALTIME = "configuration.advanced.localtime";
	public static final String CONFIGURATION_ADVANCED_DISABLEGRAPHICS = "configuration.advanced.disablegraphics";
	public static final String CONFIGURATION_ADVANCED_FULLSCREEN = "configuration.advanced.fullscreen";
	public static final String CONFIGURATION_ADVANCED_TEMPFILE = "configuration.advanced.tempfile";
	public static final String CONFIGURATION_DISK_SELECT = "configuration.disk.select";
	public static final String CONFIGURATION_ADVANCED_PID = "configuration.advanced.pid";
	public static final String CONFIGURATION_ADVANCED_PID_ENABLE = "configuration.advanced.pid.enable";
	public static final String CONFIGURATION_ADVANCED_CPUS = "configuration.advanced.cpus";
	public static final String CONFIGURATION_ADVANCED_SMP = "configuration.advanced.smp";
	public static final String CONFIGURATION_ADVANCED_AUDIO_VALUES = "configuration.advanced.audio.values";
	public static final String CONFIGURATION_ADVANCED_AUDIO_VALUE = "configuration.advanced.audio.value";
	public static final String CONFIGURATION_ADVANCED_AUDIO = "configuration.advanced.audio";
	public static final String CONFIGURATION_ADVANCED_DESCRIPTION = "configuration.advanced.description";
	public static final String CONFIGURATION_ADVANCED_TITLE = "configuration.advanced.title";
	public static final String CONFIGURATION_DISK_CDROM = "configuration.disk.cdrom";
	public static final String CONFIGURATION_DISK_HDD = "configuration.disk.hdd";
	public static final String CONFIGURATION_DISK_HDC = "configuration.disk.hdc";
	public static final String CONFIGURATION_DISK_HDB = "configuration.disk.hdb";
	public static final String CONFIGURATION_DISK_HDA = "configuration.disk.hda";
	public static final String CONFIGURATION_DISK_FDB = "configuration.disk.fdb";
	public static final String CONFIGURATION_DISK_FDA = "configuration.disk.fda";
	public static final String CONFIGURATION_GENERAL_KEYBOARD_VALUES = "configuration.general.keyboard.values";
	public static final String CONFIGURATION_GENERAL_KEYBOARD = "configuration.general.keyboard";
	public static final String CONFIGURATION_GENERAL_BOOT_VALUES = "configuration.general.boot.values";
	public static final String CONFIGURATION_GENERAL_BOOT = "configuration.general.boot";
	public static final String CONFIGURATION_GENERAL_MEMORY = "configuration.general.memory";
	public static final String CONFIGURATION_GENERAL_NAME = "configuration.general.name";
	public static final String CONFIGURATION_USB_UDBDEVICE = "configuration.usb.udbdevice";
	public static final String CONFIGURATION_USB_ENABLE = "configuration.usb.enable";
	public static final String CONFIGURATION_DEBUGEXPERT_LINUXBOOT_KERNELCOMMANDLINE = "configuration.debugexpert.linuxboot.kernelcommandline";
	public static final String CONFIGURATION_DEBUGEXPERT_LINUXBOOT_INITIALRAMDISK = "configuration.debugexpert.linuxboot.initialramdisk";
	public static final String CONFIGURATION_DEBUGEXPERT_LINUXBOOT_KERNELIMAGE = "configuration.debugexpert.linuxboot.kernelimage";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_PCALL = "configuration.debugexpert.logitems.pcall";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_CPU = "configuration.debugexpert.logitems.cpu";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_EXEC = "configuration.debugexpert.logitems.exec";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_INT = "configuration.debugexpert.logitems.int";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_OPOPT = "configuration.debugexpert.logitems.opopt";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_OP = "configuration.debugexpert.logitems.op";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_INASM = "configuration.debugexpert.logitems.inasm";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_OUTASM = "configuration.debugexpert.logitems.outasm";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_DESCRIPTION = "configuration.debugexpert.logitems.description";
	public static final String CONFIGURATION_DEBUGEXPERT_LOGITEMS_TITLE = "configuration.debugexpert.logitems.title";
	public static final String CONFIGURATION_DEBUGEXPERT_LINUXBOOT_DESCRIPTION = "configuration.debugexpert.linuxboot.description";
	public static final String CONFIGURATION_DEBUGEXPERT_LINUXBOOT_TITLE = "configuration.debugexpert.linuxboot.title";
	public static final String CONFIGURATION_DEBUGEXPERT_OUTPUTLOG = "configuration.debugexpert.outputlog";
	public static final String CONFIGURATION_DEBUGEXPERT_GDBCONNECT_PORT = "configuration.debugexpert.gdbconnect.port";
	public static final String CONFIGURATION_DEBUGEXPERT_GDBCONNECT = "configuration.debugexpert.gdbconnect";
	public static final String CONFIGURATION_DEBUGEXPERT_SIMUALTEVGA = "configuration.debugexpert.simualtevga";
	public static final String CONFIGURATION_DEBUGEXPERT_STARTCPU = "configuration.debugexpert.startcpu";
	public static final String CONFIGURATION_DEBUGEXPERT_DISABLEKQEMU = "configuration.debugexpert.disablekqemu";
	public static final String CONFIGURATION_DEBUGEXPERT_MONITOR_TYPE = "configuration.debugexpert.monitor.type";
	public static final String CONFIGURATION_DEBUGEXPERT_MONITOR_TYPEVALUES = "configuration.debugexpert.monitor.typevalues";
	public static final String CONFIGURATION_DEBUGEXPERT_PARALLEL_VALUE = "configuration.debugexpert.parallel.value";
	public static final String CONFIGURATION_DEBUGEXPERT_MONITOR = "configuration.debugexpert.monitor";
	public static final String CONFIGURATION_DEBUGEXPERT_MONITOR_VALUE = "configuration.debugexpert.monitor.value";
	public static final String CONFIGURATION_DEBUGEXPERT_PARALLEL_TYPE = "configuration.debugexpert.parallel.type";
	public static final String CONFIGURATION_DEBUGEXPERT_PARALLEL_TYPEVALUES = "configuration.debugexpert.parallel.typevalues";
	public static final String CONFIGURATION_DEBUGEXPERT_PARALLEL = "configuration.debugexpert.parallel";
	public static final String CONFIGURATION_DEBUGEXPERT_SERIAL_VALUE = "configuration.debugexpert.serial.value";
	public static final String CONFIGURATION_DEBUGEXPERT_SERIAL = "configuration.debugexpert.serial";
	public static final String CONFIGURATION_DEBUGEXPERT_SERIAL_TYPE = "configuration.debugexpert.serial.type";
	public static final String CONFIGURATION_DEBUGEXPERT_TITLE = "configuration.debugexpert.title";
	public static final String CONFIGURATION_DEBUGEXPERT_DESCRIPTION = "configuration.debugexpert.description";
	public static final String CONFIGURATION_DEBUGEXPERT_SERIAL_TYPEVALUES = "configuration.debugexpert.serial.typevalues";
}
