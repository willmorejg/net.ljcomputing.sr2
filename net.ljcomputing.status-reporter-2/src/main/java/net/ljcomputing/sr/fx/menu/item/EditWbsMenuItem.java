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


package net.ljcomputing.sr.fx.menu.item;

import net.ljcomputing.fx.alert.ErrorAlert;
import net.ljcomputing.model.Model;
import net.ljcomputing.sr.fx.controller.ActivityDataEntryController;
import net.ljcomputing.sr.fx.treeview.WbsTreeView;
import net.ljcomputing.sr.model.Activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;

/**
 * The menu item to edit a new work breakdown structure.
 * 
 * @author James G. Willmore
 *
 */
public class EditWbsMenuItem extends AbstractWbsMenuItem {
  
  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(EditWbsMenuItem.class);
  
  /** The item. */
  private Model item;
  
  /** The tree view. */
  private WbsTreeView treeView;
  
  /**
   * Instantiates a new adds the activity menu item.
   *
   * @param item the item
   * @param treeView the tree view
   */
  public EditWbsMenuItem(Model item, WbsTreeView treeView) {
    super();
    this.treeView = treeView;
    this.item = item;
    
    if (item instanceof Activity) {
      textProperty().bind(Bindings.format("Edit %s", getItemText(item)));
      setOnAction(event -> {
        try {
          initializeactivityDataEntry();
        } catch (Exception e) {
          new ErrorAlert().show(e);
        }
      });
    }
  }

  /**
   * Initialize activity data entry.
   */
  private void initializeactivityDataEntry() {
    try {
      new ActivityDataEntryController((Activity) item, treeView);
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
  }
}
