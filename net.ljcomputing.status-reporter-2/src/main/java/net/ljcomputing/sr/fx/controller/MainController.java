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
import net.ljcomputing.fx.control.time.TimeControl;
import net.ljcomputing.sr.fx.dialog.impl.TaskDataDialog;
import net.ljcomputing.sr.fx.treeview.WbsTreeView;
import net.ljcomputing.sr.model.ActivityViewModel;
import net.ljcomputing.sr.model.Task;
import net.ljcomputing.sr.model.TaskViewModel;
import net.ljcomputing.sr.service.ActivityViewService;
import net.ljcomputing.sr.service.SrModelServiceFactory;
import net.ljcomputing.sr.service.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Status Reporter main JavaFX controller.
 * 
 * @author James G. Willmore
 *
 */
public class MainController implements Initializable {

  /** The logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
  
  /** The main pane. */
  @FXML
  private VBox mainPane;

  /** The from date selector. */
  @FXML
  private DatePicker fromDateSelector;
  
  /** The from time control. */
  @FXML
  private TimeControl fromTimeControl;

  /** The to date selector. */
  @FXML
  private DatePicker toDateSelector;
  
  /** The to time control. */
  @FXML
  private TimeControl toTimeControl;
  
  /** The refresh data. */
  @FXML
  private Button refreshData;

  /** The wbs tree pane. */
  @FXML
  private Pane wbsTreePane;
  
  /** The wbs tree. */
  @FXML
  private WbsTreeView wbsTree;
  
  /** The task table. */
  @FXML
  private TableView<TaskViewModel> taskTable;
  
  /** The status bar. */
  @FXML
  private TextField statusBar;
  
  /** The wbs tree view controller. */
  private WbsTreeViewController wbsTreeViewController;
  
  /** The task table controller. */
  private TaskTableController taskTableController;

  /**
   * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
   */
  public void initialize(URL arg0, ResourceBundle arg1) {
    initTimeControls();
    initTaskTable();    
    wbsTreeViewController = new WbsTreeViewController(wbsTree);
    wbsTree.setTaskTableController(taskTableController);
    populateTreeRoot();
    
    refreshData.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        populateTreeRoot();
        populateTaskTable();
      }
    });
    
    statusBar.setText("Initialized");
    
    LOGGER.debug("initialized");
  }
  
  /**
   * Initializes the time controls.
   */
  private void initTimeControls() {
    LocalDate today = LocalDate.now();

    fromDateSelector.setValue(today);
    fromTimeControl.setLocalTime(LocalTime.of(0, 0, 0));
    toDateSelector.setValue(today.plusDays(1));
    toTimeControl.setLocalTime(LocalTime.of(0, 0, 0));
  }
  
  /**
   * Initializes the task table.
   */
  private void initTaskTable() {
    DateTimeControls fromDateTimeControls = new DateTimeControls(fromDateSelector, fromTimeControl);
    DateTimeControls toDateTimeControls = new DateTimeControls(toDateSelector, toTimeControl);
    
    taskTableController = new TaskTableController(taskTable, fromDateTimeControls, toDateTimeControls);
    populateTaskTable();
    taskTable.setRowFactory(addContextMenu());    
  }
  
  private Callback<TableView<TaskViewModel>, TableRow<TaskViewModel>> addContextMenu() {
    return new Callback<TableView<TaskViewModel>, TableRow<TaskViewModel>>() {
      
      @Override
      public TableRow<TaskViewModel> call(TableView<TaskViewModel> param) {
        final TableRow<TaskViewModel> row = new TableRow<>();
        final ContextMenu menu = new ContextMenu();
        final MenuItem deleteMenu = new MenuItem("Delete selected Task");
        
        deleteMenu.setOnAction(event -> {
          try {
            TaskService service = (TaskService) SrModelServiceFactory.Task.getServiceInstance();
            service.delete(row.getItem().getId());
            taskTable.getItems().remove(row.getItem());
            taskTableController.populateTaskTask();
          } catch (Exception e) {
            new ErrorAlert().show(e);
          }
        });

        final MenuItem editMenu = new MenuItem("Edit selected Task");
        
        editMenu.setOnAction(event -> {
          try {
            ActivityViewService activityViewService = (ActivityViewService) SrModelServiceFactory.ActivityView.getServiceInstance();
            ObservableList<ActivityViewModel> activityViewList = FXCollections.observableArrayList(activityViewService.readAll());
            int previousIndex = row.getIndex() + 1;
            TaskViewModel preiousItem = (taskTable.getItems().size() == previousIndex) ? null : taskTable.getItems().get(previousIndex);
            TaskDataDialog dialog = new TaskDataDialog(activityViewList, preiousItem);
            Optional<ButtonType> response = dialog.showAndWait(row.getItem());

            if(response.get().equals(ButtonType.OK)) {
              Task model = dialog.getItem().getTask();
              TaskService service = (TaskService) SrModelServiceFactory.Task.getServiceInstance();
              service.update(model);
              taskTableController.populateTaskTask();
            }
          } catch (Exception e) {
            new ErrorAlert().show(e);
          }
        });
        
        menu.getItems().addAll(editMenu, deleteMenu);
        
        row.contextMenuProperty().bind(
            Bindings.when(row.emptyProperty())
            .then((ContextMenu)null)
            .otherwise(menu));
        
        return row;
      }
    };
  }

  /**
   * Populate tree root.
   */
  private void populateTreeRoot() {
    try {
      wbsTreeViewController.refreshComponents();
      wbsTree.init();
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
  }

  /**
   * Populate task table.
   */
  private void populateTaskTable() {
    try {
      taskTableController.populateTaskTask();
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
  }
}
