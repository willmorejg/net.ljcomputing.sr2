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

package net.ljcomputing.sr.fx.table.cell;

import net.ljcomputing.sr.model.Activity;
import net.ljcomputing.sr.model.WorkBreakdownStructure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.ComboBoxTableCell;

/**
 * Implementation of a work breakdown structure combo box table cell.
 * 
 * @author James G. Willmore
 *
 */
public class WbsComboTableCell extends ComboBoxTableCell<Activity, String> {

  /**
   * Instantiates a new wbs combo table cell.
   *
   * @param wbsList the wbs list
   */
  public WbsComboTableCell(ObservableList<WorkBreakdownStructure> wbsList) {
    ObservableList<String> list = FXCollections.observableArrayList();

    for (WorkBreakdownStructure wbs : wbsList) {
      list.add(wbs.getName());
    }

    this.getItems().addAll(list);
  }
}
