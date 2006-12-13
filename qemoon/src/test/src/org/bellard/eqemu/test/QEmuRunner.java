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
package org.bellard.eqemu.test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class QEmuRunner implements Runnable {

	private final static Logger logger = Logger.getLogger(QEmuRunner.class);

	public final static String QEMU_PATH = "/usr/bin/qemu";

	public final static String FREEDOS_PATH = "/home/eric/dev/jqemu/freedos.dsk";

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// try {
		//			
		// Process p = Runtime.getRuntime().exec(new String[]{QEMU_PATH,
		// FREEDOS_PATH});
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(p.getInputStream()));
		// PrintWriter pw = new PrintWriter(new
		// BufferedOutputStream(p.getOutputStream()));
		//			
		// Thread.currentThread().sleep(10000);
		// System.out.println(br.readLine());
		// System.out.println(br.readLine());
		// System.out.println(br.readLine());
		// System.out.println(br.readLine());
		// System.out.println(br.readLine());
		//			
		//			
		// } catch (Exception e) {
		// // TODO add specific exception handling
		// String message = "pb during qemu launch";
		// logger.debug(message, e);
		// // decide what to do with the exception
		// }

		try {

			String[] command = { "/usr/local/bin/qemu", "-monitor",
					"tcp:127.0.0.1:7000,server",
					"/media/sda2/home/eric/dev/jqemu/freedos.dsk" };

			Process process = Runtime.getRuntime().exec(command);

			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			String host = "127.0.0.1";
			int port = 7000;
			try {
				echoSocket = new Socket(host, port);
				out = new PrintWriter(echoSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(echoSocket
						.getInputStream()));
			} catch (UnknownHostException e) {
				System.err.println("Don't know about host: " + host + ".");
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Couldn't get I/O for "
						+ "the connection to: " + host + ".");
				System.exit(1);
			}

			System.out.println(in.readLine());
			out.println("q");
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			
//			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
//					System.in));
//			String userInput;
//
//			while ((userInput = stdIn.readLine()) != null) {
//				out.println(userInput);
//				System.out.println("echo: " + in.readLine());
//			}

			out.close();
			in.close();
			//stdIn.close();
			echoSocket.close();

			PrintWriter pw = new PrintWriter(new BufferedOutputStream(process
					.getOutputStream()));
			pw.write("help\n");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			System.out.println(br.readLine());
			System.out.println(br.readLine());
			System.out.println("fin....");
		} catch (Exception e) {
			// TODO add specific exception handling
			String message = "pb during run";
			logger.debug(message, e);
			// decide what to do with the exception
		}

	}

}
