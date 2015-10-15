# Introduction #

This is the qemoon wikipage. This page is in early development.

It will contain:
  * documentation (installation, usage)
  * features
  * TODO
  * Known problems.
  * Some links

# Documentation #

## Desription ##
[qemoon](http://qemoon.org) is a gui for the [qemu](http://qemu.org) emulator. It allows to use qemu without knowing all command-line options.
qemoon is in a development state. It is not suitable in production use or for advance qemu users.

## Installation ##
  * Get and install a recent [JRE version](http://java.com/java/download/index.jsp?cid=jdp84244") (1.5 or greater).
  * Download the right [qemoon distribution](http://ebellard.free.fr/qemoon/download.html) for your os:
    * [qemoon 0.0.3 windows](http://ebellard.free.fr/qemoon/qemoon-windows-0.0.3.zip)
    * [qemoon 0.0.3 linux](http://ebellard.free.fr/qemoon/qemoon-linux-gtk-0.0.3.zip)
  * Unzip the qemoon distribution
  * Launch the qemoon executable : _qemoon.exe_ for windows, _qemoon_ for linux.
  * Rock&Roll!!!

## Usage ##

### Create a new vm from scratch ###
  1. Create a new project : **File > New > VM Configuration Project** then **Next**
  1. Choose a unique name then **Next**
  1. Choose the memory size (a 4 multiple) and the hard drive size then **OK**
  1. double-click on the project to open the vm configuration
  1. Now you can:
    * control the vm: run, stop, pause
    * edit the vm configuration
    * clone the vm
  1. Simply click on the play button to run the virtual machine

### Create a new vm from an existing image ###
It consists in creating a project which uses an existing hard drive with a linux os.
  1. Create a new example project : **File > New > VM Example Configuration Project** then **Next**
  1. Choose a unique name then **OK**.
  1. Now you can:
    * control the vm: run, stop, pause
    * edit the vm configuration
    * clone the vm

### Delete a project ###
  * Select a project in the navigation view
  * Click left then select delete in the contextual menu then **YES**.

### Delete a project ###
  * Select a project in the navigation view
  * Click left then select rename in the contextual menu
  * Choose a new non-existing name then **OK**.

### Preferences ###
Access to the preferences with Windows > Preferences
The preferences allow to:
  * Choose a custom qemu and qemu-img installation
  * Choose the qemu communication port
  * Choose whether qemu or qemoon open the server socket for communication (keep default unless you have a good reason)

# Features #

  * Create/execute/control/manage qemu configuration
  * Multiplateform: windows, linux, macosx (soon)
  * Easy to install : unzip & go!
  * Easy to use (I hope :-)

# TODO #

  * Better documentation : user guide, technical guide
  * Include a console to display qemu output
  * Monitor qemu with all possible command (already in the low-level code, must be include in the gui)
  * OSX port (I need a mac :-)
  * Autodetect cdrom
  * Better hard drive management
  * Add toolbar
  * nsis installer for windows
  * improve everything!

# Known problems #
  * Can't lunch qemoon under linux:
    * Check the **qemoon** execution right is set
    * Check **qemu** execution right is set
    * Check **qemu-img** execution right is set
  * Some label are not centered with the text

# Some links #
  * [qemoon main site](http://qemoon.org)
  * the [qemu](http://qemu.org) official website
  * [Fabrice's site](http://www.bellard.org)
  * [Eric blog](http://www.jroller.com/page/ebellard) : beware it's in french ;-)
  * [Eclipse RCP homepage](http://wiki.eclipse.org/index.php/Rich_Client_Platform)
  * Other qemu gui :
    * [JQEMU](http://www.exprofesso.com/jqemu/) : a java gui for qemu
    * [q](http://www.kju-app.org/) : a  (nice) gui for macosx