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

import net.ljcomputing.sr.fx.converter.WbsConverter;
import net.ljcomputing.sr.model.Activity;
import net.ljcomputing.sr.model.WorkBreakdownStructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * A JavaFX activity data entry dialog.
 * 
 * @author James G. Willmore
 *
 */
public class ActivityDataDialog
    extends AbstractModelDataDialog<Activity> {

  /** The logger. */
  @SuppressWarnings("unused")
  private final Logger logger = LoggerFactory.getLogger(ActivityDataDialog.class);
  
  /** The wbs list. */
  private ObservableList<WorkBreakdownStructure> wbsList;
  
  /** The lbl wbs. */
  private Label lblWbs = new Label("WBS: ");
  
  /** The lbl name. */
  private Label lblName = new Label("Name: ");
  
  /** The lbl description. */
  private Label lblDescription = new Label("Description: ");
  
  /** The wbs. */
  private ComboBox<WorkBreakdownStructure> wbsField;
  
  /** The name field. */
  private TextField nameField;
  
  /** The description field. */
  private TextField descriptionField;
  
  /**
   * Instantiates a new activity data dialog.
   *
   * @param wbsList the wbs list
   * @throws Exception the exception
   */
  public ActivityDataDialog(ObservableList<WorkBreakdownStructure> wbsList) throws Exception {
    super();
    this.wbsList = wbsList;
  }

  /**
   * @see net.ljcomputing.sr.fx.dialog.ModelDataEntry#createDialog(net.ljcomputing.sr.model.Model)
   */
  public void createDialog(Activity item) throws Exception {
    nameField = new TextField(item.getName());
    descriptionField = new TextField(item.getDescription());
    wbsField = new ComboBox<WorkBreakdownStructure>();

    wbsField.setItems(wbsList);
    wbsField.setConverter(new WbsConverter(wbsList));
    wbsField.setValue(getSelectedWbs(item.getWbs()));

    addContent(lblWbs, wbsField, lblName, nameField, lblDescription, descriptionField);
  }
  
  /**
   * @see net.ljcomputing.sr.fx.dialog.ModelDataEntry#getItem()
   */
  public Activity getItem() {
    item = new Activity(nameField.getText(), wbsField.getValue().getId());
    item.setDescription(descriptionField.getText());

    if (!"".equals(idField.getText())) {
      item.setId(Integer.valueOf(idField.getText()));
    }

    return item;
  }
  
  /**
   * Gets the selected wbs.
   *
   * @param selected the selected
   * @return the selected wbs
   */
  private WorkBreakdownStructure getSelectedWbs(Integer selected) {
    for(WorkBreakdownStructure item : wbsList) {
      if(selected.equals(item.getId())) {
        return item;
      }
    }
    
    return null;
  }
}
