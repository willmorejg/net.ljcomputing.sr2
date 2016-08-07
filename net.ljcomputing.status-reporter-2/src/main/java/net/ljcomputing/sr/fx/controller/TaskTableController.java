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
import net.ljcomputing.fx.control.time.DateTimeControls;
import net.ljcomputing.sr.model.TaskViewModel;
import net.ljcomputing.sr.service.SrModelServiceFactory;
import net.ljcomputing.sr.service.TaskViewService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Controller for the task table.
 * 
 * @author James G. Willmore
 *
 */
public class TaskTableController {
  
  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(TaskTableController.class);
  
  /** The task table. */
  private TableView<TaskViewModel> taskTable;

  /** The from date time controls. */
  private DateTimeControls fromDateTimeControls;
  
  /** The to date time controls. */
  private DateTimeControls toDateTimeControls;
  
  /**
   * Instantiates a new task table controller.
   *
   * @param taskTable the task table
   * @param fromDateTimeControls the from date time controls
   * @param toDateTimeControls the to date time controls
   */
      public TaskTableController(TableView<TaskViewModel> taskTable,
          DateTimeControls fromDateTimeControls,
          DateTimeControls toDateTimeControls) {
    this.taskTable = taskTable;
    this.fromDateTimeControls = fromDateTimeControls;
    this.toDateTimeControls = toDateTimeControls;
  }
  
  /**
   * Gets the tasks.
   *
   * @return the tasks
   */
  public ObservableList<TaskViewModel> getTasks() {
    List<TaskViewModel> list = new ArrayList<TaskViewModel>();
    
    try {
      TaskViewService service = (TaskViewService) SrModelServiceFactory.TaskView.getServiceInstance();
      list = service.readByTimes(fromDateTimeControls.getLocalDateTime(), toDateTimeControls.getLocalDateTime());
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
    
    return FXCollections.observableList(list);
  }
  
  /**
   * Populate task task.
   *
   * @throws Exception the exception
   */
  public void populateTaskTask() throws Exception {
    try {
      taskTable.getItems().clear();
      taskTable.getItems().addAll(getTasks());
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
  }
}
