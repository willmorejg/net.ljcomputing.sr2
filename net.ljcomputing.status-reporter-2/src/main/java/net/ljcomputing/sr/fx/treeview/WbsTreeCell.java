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

package net.ljcomputing.sr.fx.treeview;

import net.ljcomputing.model.Model;
import net.ljcomputing.sr.fx.menu.item.AddElapsedTaskMenuItem;
import net.ljcomputing.sr.fx.menu.item.AddNewWbsMenuItem;
import net.ljcomputing.sr.fx.menu.item.AddTaskMenuItem;
import net.ljcomputing.sr.fx.menu.item.AddWbsMenuItem;
import net.ljcomputing.sr.fx.menu.item.DeleteWbsMenuItem;
import net.ljcomputing.sr.fx.menu.item.EditWbsMenuItem;
import net.ljcomputing.sr.model.Activity;
import net.ljcomputing.sr.model.WorkBreakdownStructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;

/**
 * @author James G. Willmore
 *
 */
class WbsTreeCell extends TreeCell<Model> {
  @SuppressWarnings("unused")
  private final Logger logger = LoggerFactory.getLogger(WbsTreeCell.class);
  
  /**
   * Instantiates a new wbs tree cell.
   *
   * @param treeView the tree view
   */
  WbsTreeCell(WbsTreeView treeView) {
    super();

    selectedProperty().addListener(listener -> {
      ContextMenu contextMenu = new ContextMenu();
      
      if (treeView.getRoot().getValue().equals(getItem())) {
        contextMenu.getItems().addAll(new AddNewWbsMenuItem(treeView));
      }
      
      if (getItem() instanceof WorkBreakdownStructure && !treeView.getRoot().getValue().equals(getItem())) {
        contextMenu.getItems().addAll(new AddWbsMenuItem(getItem(), treeView));
        contextMenu.getItems().addAll(new DeleteWbsMenuItem(getItem(), treeView));
      }

      if (getItem() instanceof Activity) {
        contextMenu.getItems().addAll(new AddTaskMenuItem(getItem(), treeView, treeView.getTaskTableController()));
        contextMenu.getItems().addAll(new AddElapsedTaskMenuItem(getItem(), treeView, treeView.getTaskTableController()));
        contextMenu.getItems().addAll(new EditWbsMenuItem(getItem(), treeView));
        contextMenu.getItems().addAll(new DeleteWbsMenuItem(getItem(), treeView));
      }
      
      setContextMenu(contextMenu);
    });
  }

  @Override
  public void updateItem(Model item, boolean empty) {
    super.updateItem(item, empty);
    if(!empty) {
      setText(getItemText(item));
    } else {
      //set graphic to null for empty cells - rendering gets fouled if it is not set
      setGraphic(null);
      //set text to null for empty cells - rendering gets fouled if it is not set
      setText(null);
    }
  }
  
  /**
   * Gets the item text.
   *
   * @param item the item
   * @return the item text
   */
  private String getItemText(Object item) {
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
