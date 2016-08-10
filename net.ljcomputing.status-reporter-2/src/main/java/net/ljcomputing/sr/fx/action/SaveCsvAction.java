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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Save the CSV records to File.
 * 
 * @author James G. Willmore
 *
 */
public class SaveCsvAction extends AbstractFileAction {

  /** SLF4J logger. */
  @SuppressWarnings("unused")
  private static Logger LOGGER = LoggerFactory.getLogger(SaveCsvAction.class);
  
  /**
   * Instantiates a new file save action.
   */
  public SaveCsvAction() {
    super();
  }

  /**
   * Sets the text to file.
   *
   * @param title the title
   * @param showAllFileTypes the show all file types
   * @return the string
   */
  public File setTextToFile(String title, boolean showAllFileTypes) {
    File file = showFileChooser(title, showAllFileTypes);
    
    return file;
  }
}
