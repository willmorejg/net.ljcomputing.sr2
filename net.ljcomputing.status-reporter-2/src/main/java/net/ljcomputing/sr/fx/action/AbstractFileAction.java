/**
           Copyright 2016, James G. Willmore

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */


package net.ljcomputing.sr.fx.action;

import net.ljcomputing.fx.alert.ErrorAlert;
import net.ljcomputing.fx.alert.FileSaveSuccessfulAlert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Abstract implementation of file save action.
 * 
 * @author James G. Willmore
 *
 */
public abstract class AbstractFileAction {
  
  /** The error alert. */
  protected ErrorAlert errorAlert = new ErrorAlert();
  
  /** The success alert. */
  protected FileSaveSuccessfulAlert successAlert = new FileSaveSuccessfulAlert();
  
  /**
   * Show file chooser.
   *
   * @param title the title
   * @param showAllFileTypes the show all file types
   * @return the file
   */
  public File showFileChooser(String title, boolean showAllFileTypes) {
    FileChooser chooser = new FileChooser();

    chooser.setTitle(title);
    chooser.setInitialDirectory(new File(System.getProperty("user.home")));
    chooser.getExtensionFilters().addAll(getTextFileExtensions());
    
    if(showAllFileTypes) {
      chooser.getExtensionFilters().addAll(getAllFileTypesExtension());
    }
    
    return chooser.showOpenDialog(null);
  }
  
  /**
   * Gets the all file types extension.
   *
   * @return the all file types extension
   */
  protected List<ExtensionFilter> getAllFileTypesExtension() {
    List<ExtensionFilter> textFileExtensions = new ArrayList<ExtensionFilter>();
    
    textFileExtensions.add(new ExtensionFilter("All file types (*.*)", "*.*"));

    return textFileExtensions;
  }
  
  /**
   * Gets the text file extensions.
   *
   * @return the text file extensions
   */
  protected List<ExtensionFilter> getTextFileExtensions() {
    List<ExtensionFilter> textFileExtensions = new ArrayList<ExtensionFilter>();
    
    textFileExtensions.add(new ExtensionFilter("Text files (*.txt)", "*.txt"));
    textFileExtensions.add(new ExtensionFilter("CSV files (*.csv)", "*.cvs"));

    return textFileExtensions;
  }

}
