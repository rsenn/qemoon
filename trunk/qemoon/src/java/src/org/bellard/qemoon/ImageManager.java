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
package org.bellard.qemoon;

import org.eclipse.swt.graphics.Image;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class ImageManager {

	private Image startImage;

	private Image stopImage;

	private Image pauseImage;

	private Image resumeImage;

	public Image getStartImage22x22() {
		if (startImage == null)
			startImage = Activator.getImageDescriptor(
					"icons/22x22/az-start.png").createImage();

		return startImage;
	}

	public Image getStopImage22x22() {
		if (stopImage == null)
			stopImage = Activator.getImageDescriptor("icons/22x22/az-stop.png")
					.createImage();
		return stopImage;
	}

	public Image getPauseImage22x22() {
		if (pauseImage == null)
			pauseImage = Activator.getImageDescriptor(
					"icons/22x22/az-pause.png").createImage();
		return pauseImage;
	}

	public Image getResumeImage22x22() {
		if (resumeImage == null)
			resumeImage = Activator.getImageDescriptor("icons/22x22/resume.png")
					.createImage();
		return resumeImage;
	}
}
