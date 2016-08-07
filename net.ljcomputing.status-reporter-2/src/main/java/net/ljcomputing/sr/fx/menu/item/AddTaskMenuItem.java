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
import net.ljcomputing.sr.fx.controller.TaskTableController;
import net.ljcomputing.sr.fx.treeview.WbsTreeView;
import net.ljcomputing.sr.model.Task;
import net.ljcomputing.sr.service.SrModelServiceFactory;
import net.ljcomputing.sr.service.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;

/**
 * The menu item to add a new task to task table.
 * 
 * @author James G. Willmore
 *
 */
public class AddTaskMenuItem extends AbstractWbsMenuItem {
  
  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(AddTaskMenuItem.class);
  
  /**
   * Instantiates a new adds the wbs menu item.
   *
   * @param item the item
   * @param treeView the tree view
   */
  public AddTaskMenuItem(Model item, WbsTreeView treeView, TaskTableController taskTableController) {
    super();
    
    textProperty().bind(Bindings.format("Add new Task for %s", getItemText(item)));
    setOnAction(event -> {
        try {
          TaskService service = (TaskService) SrModelServiceFactory.Task.getServiceInstance();
          service.endOpenTasks(service.newTaskStartTime());
          Task model = new Task(item.getId());
          model.setStartTime(service.newTaskStartTime());
          service.create(model);
          taskTableController.populateTaskTask();
        } catch (Exception e) {
          new ErrorAlert().show(e);
        }
    });
  }
}
