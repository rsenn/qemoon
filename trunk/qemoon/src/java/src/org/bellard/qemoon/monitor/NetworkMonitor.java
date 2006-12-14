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
package org.bellard.qemoon.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class NetworkMonitor {

	private final static Logger logger = Logger.getLogger(NetworkMonitor.class);

	private ServerSocket serverSocket = null;

	private Socket clientSocket = null;

	private PrintWriter out = null;

	private BufferedReader in = null;

	private int port;

	private boolean serversocket;

	public NetworkMonitor(int port) {
		this(port, true);
	}

	public NetworkMonitor(int port, boolean serversocket) {
		this.port = port;
		this.serversocket = serversocket;
		init();
	}

	public void init() {
		if (port == 0) {
			String message = "the port must be filled.";
			logger.debug(message);
			throw new IllegalStateException(message);
		}
	}

	public void start() {

		// case server socket : create server socket
		if (serversocket) {
			this.serverSocket = createServerSocket(this.port);
		}

		Runnable r = new Runnable() {
			public void run() {

				// case server socket : accept connection
				if (serversocket) {
					try {
						clientSocket = serverSocket.accept();
					} catch (IOException e) {
						logger.error("Accept failed : 4444");
						// TODO throws an exception when pb accepting
						// connection
					}
				}
				// case client socket connect to the server
				else {
					clientSocket = createSocket("localhost", port);
				}
				try {
					out = new PrintWriter(clientSocket.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(clientSocket
							.getInputStream()));
				} catch (IOException e) {
					String message = "Pb when creating the streams";
					logger.debug(message, e);
				}
			}
		};

		Thread t = new Thread(r);
		t.start();

	}

	public void deinit() {
		try {
			out.close();
			in.close();
			clientSocket.close();
			if (serversocket) {
				serverSocket.close();
			}
		} catch (IOException e) {
			String message = "pb when closing streams and sockets...";
			logger.debug(message, e);
			// nothing to do
		}

	}

	/**
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * @param in
	 *            the in to set
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}

	/**
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}

	/**
	 * @param out
	 *            the out to set
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the serverSocket
	 */
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	/**
	 * @param serverSocket
	 *            the serverSocket to set
	 */
	public void setServerSocket(ServerSocket socket) {
		this.serverSocket = socket;
	}

	public void executeCommand(String command) {
		this.out.println(command);
	}

	protected final static Socket createSocket(String host, int port) {
		int i = 1;
		Socket socket = null;
		sleep(800);
		while (socket == null && i < 10) {
			try {
				socket = new Socket(host, port);
			} catch (Exception e) {
				String message = i + ".Pb in creating serverSocket : host="
						+ host + " port=" + port;
				logger.debug(message, e);
			}
			sleep(300);
			i++;
		}
		return socket;
	}

	protected final static ServerSocket createServerSocket(int port) {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
		} catch (Exception e) {
			String message = "Pb in creating server serverSocket : port="
					+ port;
			logger.debug(message, e);
		}
		return socket;
	}

	private final static void sleep(int t) {
		logger.debug("sleep t=" + t);
		try {
			Thread.sleep(t);
		} catch (InterruptedException e1) {
			String message = "Pb when sleeping";
			logger.debug(message, e1);
		}

	}

}
