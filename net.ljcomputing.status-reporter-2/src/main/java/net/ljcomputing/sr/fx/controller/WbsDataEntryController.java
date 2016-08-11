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


package net.ljcomputing.sr.fx.controller;

import net.ljcomputing.fx.alert.ErrorAlert;
import net.ljcomputing.model.Model;
import net.ljcomputing.sr.fx.dialog.impl.WbsDataDialog;
import net.ljcomputing.sr.fx.treeview.WbsTreeView;
import net.ljcomputing.sr.model.WorkBreakdownStructure;
import net.ljcomputing.sr.service.SrModelServiceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;

/**
 * JavaFX work breakdown structure controller.
 * 
 * @author James G. Willmore
 *
 */
public class WbsDataEntryController {
  
  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(WbsDataEntryController.class);
  
  /**
   * Instantiates a new wbs data entry controller.
   *
   * @param item the item
   * @param treeView the tree view
   */
  @SuppressWarnings("unchecked")
  public WbsDataEntryController(WorkBreakdownStructure item, WbsTreeView treeView) {
    try {
      WbsDataDialog dialog = new WbsDataDialog();
      Optional<ButtonType> response = dialog.showAndWait(item);
      
      if(response.get().equals(ButtonType.OK)) {
        item = dialog.getItem();
        
        if(item.getId() != null) {
          SrModelServiceFactory.WorkBreakdownStructure.getServiceInstance().update(item);
        } else {
          SrModelServiceFactory.WorkBreakdownStructure.getServiceInstance().create(item);
        }
        
        TreeItem<Model> ti = treeView
            .getTreeItem(treeView.getSelectionModel().getSelectedIndex());

        ti.getChildren().add(new TreeItem<Model>(item));
      }
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
  }
}
