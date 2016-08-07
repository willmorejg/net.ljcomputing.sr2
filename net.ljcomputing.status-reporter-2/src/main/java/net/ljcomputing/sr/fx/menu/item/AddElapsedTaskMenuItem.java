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
import net.ljcomputing.fx.control.time.TimeControl;
import net.ljcomputing.model.Model;
import net.ljcomputing.sr.fx.controller.TaskTableController;
import net.ljcomputing.sr.fx.treeview.WbsTreeView;
import net.ljcomputing.sr.model.Task;
import net.ljcomputing.sr.service.SrModelServiceFactory;
import net.ljcomputing.sr.service.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The menu item to add a new task to task table.
 * 
 * @author James G. Willmore
 *
 */
public class AddElapsedTaskMenuItem extends AbstractWbsMenuItem {
  
  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(AddElapsedTaskMenuItem.class);
  
  /**
   * Instantiates a new adds the wbs menu item.
   *
   * @param item the item
   * @param treeView the tree view
   */
  public AddElapsedTaskMenuItem(Model item, WbsTreeView treeView, TaskTableController taskTableController) {
    super();
    
    textProperty().bind(Bindings.format("Add new Task with end time for %s", getItemText(item)));
    setOnAction(event -> {
        try {
          TaskService service = (TaskService) SrModelServiceFactory.Task.getServiceInstance();
          TimeControl endTimeControl = new TimeControl(service.newTaskStartTime());
          Label instructions = new Label(String.format("Set end time for %s", getItemText(item)));
          VBox vbox = new VBox(instructions,endTimeControl);
          Alert endTimeDialog = new Alert(AlertType.NONE);

          endTimeDialog.setTitle(String.format("Add %s", getItemText(item)));
          endTimeDialog.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
          endTimeDialog.getDialogPane().setContent(vbox);

          LocalDateTime end = endTimeControl.spinnerProperty().getValue().atDate(LocalDate.now());
          LocalDateTime start = LocalTime.now().atDate(LocalDate.now());
          endTimeDialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(!start.isBefore(end));

          
          endTimeControl.spinnerProperty().valueProperty().addListener((obs, oldValue, newValue) -> {
            Button okButton = (Button) endTimeDialog.getDialogPane().lookupButton(ButtonType.OK);
            
            if(null == newValue) {
              okButton.setDisable(false);
              return;
            }
            
            LocalDateTime startDt = LocalTime.now().atDate(LocalDate.now());

            okButton.setDisable(!endTimeControl.spinnerProperty().isAfter(startDt));
          });

          Optional<ButtonType> response = endTimeDialog.showAndWait();
          
          if (response.get().equals(ButtonType.OK)) {
            service.endOpenTasks(endTimeControl.getLocalTimeAsDate());
            Task model = new Task(item.getId());
            model.setStartTime(endTimeControl.getLocalTimeAsDate());
            service.create(model);
            taskTableController.populateTaskTask();
          }
        } catch (Exception e) {
          new ErrorAlert().show(e);
        }
    });
  }
}
