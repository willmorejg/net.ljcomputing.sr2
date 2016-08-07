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

package net.ljcomputing.sr.fx.dialog.impl;

import net.ljcomputing.sr.model.WorkBreakdownStructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * A work breakdown structure data entry dialog.
 * 
 * @author James G. Willmore
 *
 */
public class WbsDataDialog extends
    AbstractModelDataDialog<WorkBreakdownStructure> {

  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(WbsDataDialog.class);

  /** The lbl name. */
  private Label lblName = new Label("Name: ");

  /** The lbl description. */
  private Label lblDescription = new Label("Description: ");

  /** The name field. */
  private TextField nameField;

  /** The description field. */
  private TextField descriptionField;
  
  /**
   * Instantiates a new wbs data dialog.
   *
   * @throws Exception the exception
   */
  public WbsDataDialog() throws Exception {
    super();
  }
  
  /**
   * @see net.ljcomputing.sr.fx.dialog.ModelDataEntry#createDialog(net.ljcomputing.sr.model.Model)
   */
  public void createDialog(WorkBreakdownStructure item) throws Exception {
    nameField = new TextField(item.getName());
    descriptionField = new TextField(item.getDescription());

    addContent(lblName, nameField, lblDescription, descriptionField);
  }
  
  /**
   * @see net.ljcomputing.sr.fx.dialog.ModelDataEntry#getItem()
   */
  public WorkBreakdownStructure getItem() {
    item = new WorkBreakdownStructure(nameField.getText());
    item.setDescription(descriptionField.getText());

    if (!"".equals(idField.getText())) {
      item.setId(Integer.valueOf(idField.getText()));
    }

    return item;
  }
}
