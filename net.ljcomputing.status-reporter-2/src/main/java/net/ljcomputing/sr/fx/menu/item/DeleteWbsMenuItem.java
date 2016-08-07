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
import net.ljcomputing.sr.fx.controller.DeleteModelController;
import net.ljcomputing.sr.fx.treeview.WbsTreeView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;
import javafx.scene.control.TreeItem;

/**
 * The menu item to delete a new work breakdown structure.
 * 
 * @author James G. Willmore
 *
 */
public class DeleteWbsMenuItem extends AbstractWbsMenuItem {
  
  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(DeleteWbsMenuItem.class);
  
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
  public DeleteWbsMenuItem(Model item, WbsTreeView treeView) {
    super();
    this.treeView = treeView;
    this.item = item;
    
    textProperty().bind(Bindings.format("Delete %s", getItemText(item)));
    setOnAction(event -> {
      try {
        deleteItem();
      } catch (Exception e) {
        new ErrorAlert().show(e);
      }
    });
  }

  private void deleteItem() {
    try {
      DeleteModelController controller = new DeleteModelController();
      controller.delete(item);
      
      TreeItem<Model> ti = treeView.getSelectionModel().getSelectedItem();
      ti.getParent().getChildren().remove(ti);
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
  }
}
