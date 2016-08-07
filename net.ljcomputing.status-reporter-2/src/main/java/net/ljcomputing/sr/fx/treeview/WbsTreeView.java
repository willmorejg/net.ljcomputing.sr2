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

import net.ljcomputing.fx.alert.ErrorAlert;
import net.ljcomputing.model.Model;
import net.ljcomputing.sr.fx.controller.TaskTableController;
import net.ljcomputing.sr.fx.tree.item.WbsTreeItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;

/**
 * Work breakdown structure tree view.
 * 
 * @author James G. Willmore
 *
 */
public class WbsTreeView extends TreeView<Model> {
  @SuppressWarnings("unused")
  private final Logger logger = LoggerFactory.getLogger(WbsTreeView.class);
  
  /** The task table controller. */
  private TaskTableController taskTableController;
  
  /**
   * Instantiates a new wbs tree view.
   */
  public WbsTreeView() {
    super();

    setCellFactory(tv -> {
      return new WbsTreeCell(this);
    });
  }
  
  public void init() {
    getRoot().addEventHandler(WbsTreeItem.childrenModificationEvent(), new EventHandler<TreeItem.TreeModificationEvent<Model>>() {

      /**
       * @see javafx.event.EventHandler#handle(javafx.event.Event)
       */
      @Override
      public void handle(TreeModificationEvent<Model> event) {
        if(null != taskTableController) {
          try {
            taskTableController.populateTaskTask();
          } catch (Exception e) {
            new ErrorAlert().show(e);
          }
        }
      }
      
    });
  }
  
  /**
   * Gets the task table controller.
   *
   * @return the task table controller
   */
  public TaskTableController getTaskTableController() {
    return taskTableController;
  }
  
  /**
   * Sets the task table controller.
   *
   * @param taskTableController the new task table controller
   */
  public void setTaskTableController(TaskTableController taskTableController) {
    this.taskTableController = taskTableController;
  }
}
