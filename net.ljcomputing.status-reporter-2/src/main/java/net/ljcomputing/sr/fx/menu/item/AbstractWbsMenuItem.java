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

import net.ljcomputing.sr.model.Activity;
import net.ljcomputing.sr.model.WorkBreakdownStructure;

import javafx.scene.control.MenuItem;

/**
 * Abstract implementation of a work breakdown structure menu item.
 * 
 * @author James G. Willmore
 *
 */
abstract class AbstractWbsMenuItem extends MenuItem {
  
  /**
   * Gets the item text.
   *
   * @param item the item
   * @return the item text
   */
  protected String getItemText(Object item) {
    switch(item.getClass().getSimpleName()) {
      case "WorkBreakdownStructure":
        return ((WorkBreakdownStructure)item).getName();
      case "Activity":
        return ((Activity)item).getName();
      default:
        return "";
    }
  }

}
