# Introduction #

The purpose of this page is to provide useful information
on how to use the Subversive Plugin with the Eclipse IDE.

# Details #

## Installation Steps ##
  * Download latest version of eclipse [here](http://www.eclipse.org)
  * Unzip eclipse to a folder on hard drive.
  * Run eclipse.exe in eclipse directory to start the application.
  * Follow steps [here](http://www.eclipse.org/subversive/documentation/gettingStarted/aboutSubversive/install.php) to install subversive
  * Restart Eclipse

## Steps to Connect to easyx10 Repository ##
  * Start Eclipse
  * Create SVN Perspective by (Select Window->Open Perspective -> Other)
  * Choose SVN Repository Exploring from the dialog
  * Change to SVN Perspective if not already shown (Select Window->Open Perspective ....)
  * Click the File Menu-> New -> Repository Location.  A SVN repository dialog will open.
  * Enter the following data:
    * URL: https://easyx10.googlecode.com/svn
    * User: Your google account id
    * Password: Go to the easyx10 project page and retrieve your password from the source tab.
  * Select 'Finish'
  * Now you should be connected to our SVN Repository.
  * From here you can either check out an existing directory structure or share your own.

## How to Checkout from Google Code ##
  * Open the SVN Perspective
  * Right Click on a directory structure you wish to have access to
  * Select 'Checkout' - This will copy the selected directory structure to your computer
  * Now, select Window->Open Perspective->Java
  * You should now see the checked out directories.  You can work with the selected directories.

## Adding/Modifying Files in Source Control ##
  * New files can be added to your local copy of the directory through the eclipse Java perspective.  Once the file is ready to be added right click on the file, select team->add to version control. (Then commit your changes from the team menu)
  * To update a file that was modified, right click on the file, select team->commit

## Useful Links ##
  * Subversion eBook - http://svnbook.red-bean.com/
  * Subversive Homepage - http://www.polarion.org/index.php?page=overview&project=subversive