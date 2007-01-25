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
package org.bellard.qemoon.forms;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.bellard.qemoon.Activator;
import org.bellard.qemoon.constants.Configuration2Constants;
import org.bellard.qemoon.editors.ConfigurationFormEditor;
import org.bellard.qemoon.manager.VMConfigurationPreferenceManager;
import org.bellard.qemoon.model.VM;
import org.bellard.qemoon.monitor.NetworkMonitor;
import org.bellard.qemoon.resources.RessourcesUtils;
import org.bellard.qemoon.runtime.QEmuManager;
import org.bellard.qemoon.runtime.QemuThread;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class ConfigurationFormVMPage extends FormPage implements IFormPage {

	/**
	 * 
	 */
	public static final String ICONS_QE_22X22_PNG = "icons/qe-22x22.png";

	private final static Logger logger = Logger
			.getLogger(ConfigurationFormVMPage.class);

	public static final String VM_STATE_STARTED = "started";

	public static final String VM_STATE_PAUSED = "paused";

	public static final String VM_STATE_STOPPED = "stopped";

	public static final String VM_CONFIGURATION_NOTES_VALUE = "vm.configuration.notes.value";

	public final static String ID = "qemoon.ConfigurationFormVMPage";

	private Label statusLabel;

	private Label guestosLabel;

	private Label configurationfileLabel;

	private QemuThread qemuthread;

	private Text qemuImagePath;

	private Button qemuImageCheck;

	private Label memoryLabel;

	private Label harddiskLabel;

	private Label cdromLabel;

	private Label floppyLabel;

	private Label usbLabel;

	private Label ethernetLabel;

	private Label audioLabel;

	private Text configText;

	private Label imageLabel;

	private ImageHyperlink startlink;

	private ImageHyperlink pauselink;

	private Activator activator = Activator.getDefault();

	private boolean isStarted = false;

	private boolean isPaused = false;

	public ConfigurationFormVMPage(FormEditor editor) {
		super(editor, ID, Activator.getDefault().getMessages().getString(
				"configuration.vm.title"));
	}

	public ConfigurationFormEditor getConfigurationFormEditor() {
		return (ConfigurationFormEditor) getEditor();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {

		final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();

		form.setText(getConfigurationFormEditor().getVMConfiguration()
				.getName());

		Composite body = form.getBody();
		TableWrapLayout layout = new TableWrapLayout();
		layout.bottomMargin = 10;
		layout.topMargin = 5;
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;
		body.setLayout(layout);

		Composite first = toolkit.createComposite(body);
		layout = new TableWrapLayout();
		layout.numColumns = 2;
		first.setLayout(layout);
		first.setLayoutData(new TableWrapData(TableWrapData.FILL));

		// status
		toolkit.createLabel(first, Activator.getDefault().getMessages()
				.getString("configuration.vm.state"));
		// TODO get value from file
		String vmState = VM_STATE_STOPPED;
		if (getConfigurationFormEditor().getVMConfiguration().getVim()
				.isStarted()) {
			vmState = VM_STATE_STARTED;
		}
		statusLabel = toolkit.createLabel(first, vmState);

		// guestos
		toolkit.createLabel(first, Activator.getDefault().getMessages()
				.getString("configuration.vm.guestos"));

		String guestosValue = Platform.getOS();
		guestosLabel = toolkit.createLabel(first, guestosValue);

		// configuration file
		toolkit.createLabel(first, Activator.getDefault().getMessages()
				.getString("configuration.vm.configurationfile"));
		configurationfileLabel = toolkit.createLabel(first,
				getConfigurationFormEditor().getConfigFile().getFullPath()
						.toOSString());

		toolkit.createLabel(body, "");

		// left
		Composite left = toolkit.createComposite(body);
		layout = new TableWrapLayout();
		left.setLayout(layout);
		left.setLayoutData(new TableWrapData(TableWrapData.FILL));

		// create command panel
		createCommandPanel(form, toolkit, left);

		// toolkit.createLabel(left, "");

		// TODO to better for multi line

		// right panel
		// Composite right = toolkit.createComposite(body);
		// layout = new TableWrapLayout();
		// layout.verticalSpacing = 20;
		// right.setLayout(layout);
		// right.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		// device composite
		createDevicePanel(form, toolkit, left);

		// notes
		// createNotesPanel(form, toolkit, left);

	}

	/**
	 * @param form
	 * @param toolkit
	 * @param composite
	 */
	private void createDevicePanel(final ScrolledForm form,
			FormToolkit toolkit, Composite composite) {
		Composite deviceComposite = createSection(toolkit, form, composite,
				"configuration.vm.device.title",
				"configuration.vm.device.description", 2);

		// memory link
		ImageHyperlink memoryLink = createImageHyperlink(deviceComposite,
				toolkit, "configuration.vm.device.memory",
				"icons/22x22/memory.png");
		memoryLabel = toolkit.createLabel(deviceComposite,
				getConfigurationFormEditor().getPreferenceStore().getString(
						Configuration2Constants.MEMORY_VALUE), SWT.WRAP);
		getConfigurationFormEditor().getPreferenceStore()
				.addPropertyChangeListener(new IPropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getProperty().equals(
								Configuration2Constants.MEMORY_VALUE)) {
							memoryLabel.setText(event.getNewValue().toString());
							memoryLabel.pack();
						}

					}
				});
		memoryLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("Memory Link activated!");
				createAndDisplayPreferenceDialog(form,
						VMConfigurationPreferenceManager.MEMORY);
			}

		});

		// harddisk link
		ImageHyperlink harddiskLink = createImageHyperlink(deviceComposite,
				toolkit, "configuration.vm.device.harddisk",
				"icons/22x22/harddisk.png");
		harddiskLabel = toolkit.createLabel(deviceComposite, "");
		harddiskLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("Harddisk Link activated!");
				createAndDisplayPreferenceDialog(form,
						VMConfigurationPreferenceManager.HARDDISK);
			}
		});

		// cdrom link
		ImageHyperlink cdromLink = createImageHyperlink(deviceComposite,
				toolkit, "configuration.vm.device.cdrom",
				"icons/22x22/cdrom.png");
		cdromLabel = toolkit.createLabel(deviceComposite, "");
		cdromLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("cdrom Link activated!");
				createAndDisplayPreferenceDialog(form,
						VMConfigurationPreferenceManager.CDROM);
			}
		});

		// floppy link
		ImageHyperlink floppyLink = createImageHyperlink(deviceComposite,
				toolkit, "configuration.vm.device.floppy",
				"icons/22x22/floppy.png");
		floppyLabel = toolkit.createLabel(deviceComposite, "");
		floppyLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("floppy Link activated!");
				createAndDisplayPreferenceDialog(form,
						VMConfigurationPreferenceManager.FLOPPY);
			}
		});

		// ethernet link
		// ImageHyperlink ethernetLink = toolkit.createImageHyperlink(
		// deviceComposite, SWT.WRAP);
		// ethernetLabel = toolkit.createLabel(deviceComposite, "");
		// ethernetLink.addHyperlinkListener(new HyperlinkAdapter() {
		// public void linkActivated(HyperlinkEvent e) {
		// logger.info("ethernet Link activated!");
		// // TODO add ethernet preferences
		// // TODO add ethernet icon
		// }
		// });
		// // TODO add ethernet preferences
		// // TODO add ethernet icon
		// ethernetLink.setText(Activator.getDefault().getMessages()
		// .getString("configuration.vm.device.ethernet.title"));
		// ethernetLink.setImage(Activator.getImageDescriptor("icons/gear.png")
		// .createImage());
		// ethernetLink.setToolTipText(Activator.getDefault().getMessages()
		// .getString("configuration.vm.device.ethernet.description"));

		// usb link
		// ImageHyperlink usbLink =
		// toolkit.createImageHyperlink(deviceComposite,
		// SWT.WRAP);
		// usbLabel = toolkit.createLabel(deviceComposite, "");
		// usbLink.addHyperlinkListener(new HyperlinkAdapter() {
		// public void linkActivated(HyperlinkEvent e) {
		// logger.info("Resume Link activated!");
		// // TODO add usb preferences
		// // TODO add usb icon
		// }
		// });
		// // TODO add usb preferences
		// // TODO add usb icon
		// usbLink
		// .setText(Activator.getDefault().getMessages()
		// .getString("configuration.vm.device.usb.title"));
		// usbLink.setImage(Activator.getImageDescriptor("icons/gear.png")
		// .createImage());
		// usbLink.setToolTipText(Activator.getDefault().getMessages()
		// .getString("configuration.vm.device.usb.description"));

		// audio link
		ImageHyperlink audioLink = createImageHyperlink(deviceComposite,
				toolkit, "configuration.vm.device.audio",
				"icons/22x22/sound.png");
		String audioText = "";
		if (getConfigurationFormEditor().getPreferenceStore().getBoolean(
				Configuration2Constants.AUDIO_ENABLE)) {
			audioText = getConfigurationFormEditor().getPreferenceStore()
					.getString(Configuration2Constants.AUDIO_VALUE);
		}
		audioLabel = toolkit.createLabel(deviceComposite, audioText, SWT.WRAP);
		getConfigurationFormEditor().getPreferenceStore()
				.addPropertyChangeListener(new IPropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent event) {
						if (getConfigurationFormEditor().getPreferenceStore()
								.getBoolean(
										Configuration2Constants.AUDIO_ENABLE)) {
							if (event.getProperty().equals(
									Configuration2Constants.AUDIO_VALUE)
									|| event
											.getProperty()
											.equals(
													Configuration2Constants.AUDIO_ENABLE)) {
								audioLabel.setText(event.getNewValue()
										.toString());
							}
						} else {
							audioLabel.setText("");
						}
						audioLabel.pack();
					}
				});

		audioLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("audio Link activated!");
				createAndDisplayPreferenceDialog(form,
						VMConfigurationPreferenceManager.AUDIO);
			}
		});

		// image link
		ImageHyperlink imageLink = createImageHyperlink(deviceComposite,
				toolkit, "configuration.vm.device.image", ICONS_QE_22X22_PNG);
		String imageText = "";
		if (getConfigurationFormEditor().getPreferenceStore().getBoolean(
				Configuration2Constants.IMAGE_CUSTOM)) {
			imageText = getConfigurationFormEditor().getPreferenceStore()
					.getString(Configuration2Constants.IMAGE_VALUE);
			imageText = imageText.substring(imageText.lastIndexOf("/") + 1);
		}
		imageLabel = toolkit.createLabel(deviceComposite, imageText, SWT.WRAP);
		getConfigurationFormEditor().getPreferenceStore()
				.addPropertyChangeListener(new IPropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getProperty().equals(
								Configuration2Constants.IMAGE_CUSTOM)
								|| event.getProperty().equals(
										Configuration2Constants.IMAGE_VALUE)) {
							if (getConfigurationFormEditor()
									.getPreferenceStore()
									.getBoolean(
											Configuration2Constants.IMAGE_CUSTOM)) {
								String imageText = event.getNewValue()
										.toString();
								imageText = imageText.substring(imageText
										.lastIndexOf("/") + 1);
								imageLabel.setText(imageText);
							} else {
								imageLabel.setText("");
							}
							imageLabel.pack();
						}

					}
				});

		imageLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("image Link activated!");
				createAndDisplayPreferenceDialog(form,
						VMConfigurationPreferenceManager.QEMU_IMAGE);
			}
		});

		// keyboard
		// ImageHyperlink keyboardLink = createImageHyperlink(deviceComposite,
		// toolkit, "configuration.vm.device.keyboard",
		// "icons/22x22/keyboard.png");
		// toolkit.createLabel(deviceComposite, "");
		// keyboardLink.addHyperlinkListener(new HyperlinkAdapter() {
		// public void linkActivated(HyperlinkEvent e) {
		// logger.info("keyboard Link activated!");
		// createAndDisplayPreferenceDialog(form,
		// VMConfigurationPreferenceManager.KEYBOARD);
		// }
		// });
	}

	/**
	 * @param form
	 * @param toolkit
	 * @param composite
	 */
	private void createNotesPanel(final ScrolledForm form, FormToolkit toolkit,
			Composite composite) {
		Composite notesComposite = createSection(toolkit, form, composite,
				"configuration.vm.notes.title",
				"configuration.vm.notes.description", 1);

		notesComposite.setLayout(new FillLayout());
		configText = new Text(notesComposite, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		configText.setText(getConfigurationFormEditor().getPreferenceStore()
				.getString(VM_CONFIGURATION_NOTES_VALUE));
		configText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				getConfigurationFormEditor().getPreferenceStore().setValue(
						VM_CONFIGURATION_NOTES_VALUE, configText.getText());
				getConfigurationFormEditor().editorDirtyStateChanged();
			}

		});
	}

	/**
	 * @param form
	 * @param toolkit
	 * @param composite
	 */
	private void createCommandPanel(final ScrolledForm form,
			FormToolkit toolkit, Composite composite) {
		// // create commands section
		Composite commandsComposite = createSection(toolkit, form, composite,
				"configuration.vm.commands.title",
				"configuration.vm.commands.description", 3);

		// start link
		startlink = toolkit.createImageHyperlink(commandsComposite, SWT.WRAP);
		// separator
		toolkit.createLabel(commandsComposite, "");
		toolkit.createLabel(commandsComposite, "");

		// pause link
		pauselink = toolkit.createImageHyperlink(commandsComposite, SWT.WRAP);

		startlink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("start link activated!");
				if (isStarted) {
					stopVM();
					isStarted = false;
					isPaused = false;
				} else {
					startVM();
					isStarted = true;
					isPaused = false;
				}
			}
		});
		startlink.setText(Activator.getDefault().getMessages().getString(
				"configuration.vm.commands.start"));
		startlink.setImage(activator.getImageManager().getStartImage22x22());
		startlink.setToolTipText(Activator.getDefault().getMessages()
				.getString("configuration.vm.commands.start"));

		pauselink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("pause Link activated!");
				if (isPaused) {
					resumeVM();
					isPaused = false;
				} else {
					pauseVM();
					isPaused = true;
				}

			}
		});
		pauselink.setText(Activator.getDefault().getMessages().getString(
				"configuration.vm.commands.pause"));
		// pauselink.setImage(activator.getImageManager().getPauseImage22x22());
		pauselink.setToolTipText(Activator.getDefault().getMessages()
				.getString("configuration.vm.commands.pause"));
		setEnablePauseLink(false);

		// separator
		toolkit.createLabel(commandsComposite, "");
		toolkit.createLabel(commandsComposite, "");

		// edit config link
		ImageHyperlink editConfiglink = toolkit.createImageHyperlink(
				commandsComposite, SWT.WRAP);
		editConfiglink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("Edit configuration Link activated!");
				createAndDisplayPreferenceDialog(form,
						VMConfigurationPreferenceManager.MEMORY);
			}
		});
		editConfiglink.setText(Activator.getDefault().getMessages().getString(
				"configuration.vm.commands.editconfig"));
		editConfiglink.setImage(Activator.getImageDescriptor(
				"icons/22x22/config.png").createImage());
		editConfiglink.setToolTipText(Activator.getDefault().getMessages()
				.getString("configuration.vm.commands.editconfig"));
		// separator
		toolkit.createLabel(commandsComposite, "");
		toolkit.createLabel(commandsComposite, "");

		// clone vm link
		ImageHyperlink clonevmlink = toolkit.createImageHyperlink(
				commandsComposite, SWT.WRAP);
		clonevmlink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				logger.info("Edit configuration Link activated!");
				// clone vm
				RessourcesUtils.cloneProject(getConfigurationFormEditor()
						.getVMConfiguration().getVim());
			}
		});
		clonevmlink.setText(Activator.getDefault().getMessages().getString(
				"configuration.vm.commands.clonevm"));
		clonevmlink.setImage(Activator.getImageDescriptor(
				"icons/22x22/clone.png").createImage());
		clonevmlink.setToolTipText(Activator.getDefault().getMessages()
				.getString("configuration.vm.commands.clonevm"));
		// separator
		toolkit.createLabel(commandsComposite, "");
		toolkit.createLabel(commandsComposite, "");
	}

	/**
	 * @param toolkit
	 * @param composite
	 * @param form
	 */
	protected Composite createSection(FormToolkit toolkit,
			final ScrolledForm form, Composite composite, String title,
			String tooltip, int numcol) {

		Section section = toolkit.createSection(composite, Section.DESCRIPTION
				| Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED);

		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 20;
		composite.setLayout(layout);

		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(td);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});

		section.setText(Activator.getDefault().getMessages().getString(title));
		section.setToolTipText(Activator.getDefault().getMessages().getString(
				tooltip));

		Composite sectionClient = toolkit.createComposite(section);
		layout = new TableWrapLayout();
		sectionClient.setLayout(layout);
		layout.numColumns = numcol;
		section.setClient(sectionClient);
		return sectionClient;
	}

	/**
	 * @param form
	 */
	private void createAndDisplayPreferenceDialog(final ScrolledForm form,
			String pageId) {

		VMConfigurationPreferenceManager manager = new VMConfigurationPreferenceManager();
		PreferenceDialog preferenceDialog = new PreferenceDialog(form
				.getShell(), manager);
		preferenceDialog.setPreferenceStore(getConfigurationFormEditor()
				.getPreferenceStore());
		preferenceDialog.setSelectedNode(pageId);
		preferenceDialog.open();
	}

	/**
	 * 
	 */
	protected void startVM() {

		// get the vm configuration
		VM vim = getConfigurationFormEditor().getVMConfiguration().getVim();

		// TODO manage exceptions
		// TODO see if fix image is still necessary
		// fix image
		boolean imageenable = getConfigurationFormEditor().getPreferenceStore()
				.getBoolean(Configuration2Constants.IMAGE_CUSTOM);
		if (imageenable) {
			vim.setQemuImagePath(getConfigurationFormEditor()
					.getPreferenceStore().getString(
							Configuration2Constants.IMAGE_VALUE));
		} else {
			// TODO see if the vm can still be launched
		}

		vim.setExecutionObserver(createQemuThreadObserver());

		QEmuManager manager = Activator.getDefault().getQEmuManager(vim);
		manager.start();

		becomeStop();
		setEnablePauseLink(true);

	}

	protected Observer createQemuThreadObserver() {
		Observer observer = new Observer() {
			public void update(Observable o, Object arg) {
				getSite().getShell().getDisplay().syncExec(new Runnable() {
					public void run() {
						becomeStart();
						becomePause();
						setEnablePauseLink(false);
						VM vim = getConfigurationFormEditor()
								.getVMConfiguration().getVim();
						NetworkMonitor monitor = activator.getQemuMonitor()
								.getNetworkMonitor(vim.getName());
						monitor.deinit();
					}
				});
			}
		};
		return observer;
	}

	/**
	 * 
	 */
	protected void stopVM() {
		VM vim = getConfigurationFormEditor().getVMConfiguration().getVim();
		QEmuManager manager = Activator.getDefault().getQEmuManager(vim);
		manager.stop();
	}

	/**
	 * 
	 */
	protected void resumeVM() {
		VM vim = getConfigurationFormEditor().getVMConfiguration().getVim();
		QEmuManager manager = Activator.getDefault().getQEmuManager(vim);
		manager.resume();
		becomePause(true);
	}

	/**
	 * 
	 */
	protected void pauseVM() {
		VM vim = getConfigurationFormEditor().getVMConfiguration().getVim();
		QEmuManager manager = Activator.getDefault().getQEmuManager(vim);
		manager.pause();
		becomeResume();
	}

	/**
	 * 
	 * 
	 */
	private void becomeStart() {
		// start link
		startlink.setImage(activator.getImageManager().getStartImage22x22());
		startlink.setText(Activator.getDefault().getMessages().getString(
				"configuration.vm.commands.start"));
		startlink.setToolTipText(Activator.getDefault().getMessages()
				.getString("configuration.vm.commands.start"));
		startlink.pack();
		setEnablePauseLink(false);
		// manage status => stopped
		statusLabel.setText(VM_STATE_STOPPED);
		statusLabel.pack();

	}

	/**
	 * 
	 * 
	 */
	private void becomeStop() {
		startlink.setImage(activator.getImageManager().getStopImage22x22());
		startlink.setText(Activator.getDefault().getMessages().getString(
				"configuration.vm.commands.stop"));
		startlink.setToolTipText(Activator.getDefault().getMessages()
				.getString("configuration.vm.commands.stop"));
		startlink.pack();
		// manage status
		statusLabel.setText(VM_STATE_STARTED);
		statusLabel.pack();

	}

	/**
	 * 
	 * 
	 */
	private void becomeResume() {
		pauselink.setText(Activator.getDefault().getMessages().getString(
				"configuration.vm.commands.resume"));
		pauselink.setImage(activator.getImageManager().getResumeImage22x22());
		pauselink.setToolTipText(Activator.getDefault().getMessages()
				.getString("configuration.vm.commands.resume"));
		pauselink.pack();
		// manage status
		statusLabel.setText(VM_STATE_PAUSED);
		statusLabel.pack();

	}

	private void becomePause() {
		becomePause(false);
	}

	/**
	 * 
	 * 
	 */
	private void becomePause(boolean status) {
		pauselink.setText(Activator.getDefault().getMessages().getString(
				"configuration.vm.commands.pause"));
		pauselink.setImage(activator.getImageManager().getPauseImage22x22());
		pauselink.setToolTipText(Activator.getDefault().getMessages()
				.getString("configuration.vm.commands.pause"));
		pauselink.pack();
		if (status) {
			// manage status
			statusLabel.setText(VM_STATE_STARTED);
			statusLabel.pack();
		}
	}

	/**
	 * 
	 * @param enabled
	 */
	private void setEnablePauseLink(boolean enabled) {
		pauselink.setEnabled(enabled);
		Image pauseimage = activator.getImageManager().getPauseImage22x22();
		if (enabled) {
			pauselink.setImage(pauseimage);
		} else {
			pauselink.setImage(new Image(pauseimage.getDevice(), pauseimage,
					SWT.IMAGE_DISABLE));
		}
		pauselink.pack();
	}

	/**
	 * 
	 * @param deviceComposite
	 * @param toolkit
	 * @param keystart
	 * @param image
	 * @return
	 */
	public ImageHyperlink createImageHyperlink(Composite deviceComposite,
			FormToolkit toolkit, String keystart, String image) {
		ImageHyperlink hyperlink = toolkit.createImageHyperlink(
				deviceComposite, SWT.WRAP);
		hyperlink.setText(Activator.getDefault().getMessages().getString(
				keystart + ".title"));
		hyperlink.setImage(Activator.getImageDescriptor(image).createImage());
		hyperlink.setToolTipText(Activator.getDefault().getMessages()
				.getString(keystart + ".description"));

		return hyperlink;
	}

}
