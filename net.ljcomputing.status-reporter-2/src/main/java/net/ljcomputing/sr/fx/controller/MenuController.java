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

import net.ljcomputing.fx.action.ExitAction;
import net.ljcomputing.fx.alert.AboutAlert;
import net.ljcomputing.fx.alert.ErrorAlert;
import net.ljcomputing.sr.fx.table.ActivityTableView;
import net.ljcomputing.sr.fx.table.ModelTableCells;
import net.ljcomputing.sr.fx.table.WbsTableView;
import net.ljcomputing.sr.model.Activity;
import net.ljcomputing.sr.model.TaskViewModel;
import net.ljcomputing.sr.model.WorkBreakdownStructure;
import net.ljcomputing.sr.service.SrModelServiceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The menu bar controller.
 * 
 * @author James G. Willmore
 *
 */
public class MenuController implements Initializable {
  /** The logger. */
  private static Logger LOGGER = LoggerFactory.getLogger(MenuController.class);
  
//  /** The file editor id. */
//  private String fileEditorId;
  
  /** The CTRL-A keyboard event. */
  public KeyCodeCombination ctrlA = new KeyCodeCombination(KeyCode.A,KeyCombination.CONTROL_DOWN);
  
  /** The CTRL-E keyboard event. */
  public KeyCodeCombination ctrlE = new KeyCodeCombination(KeyCode.E,KeyCombination.CONTROL_DOWN);
  
  /** The CTRL-S keyboard event. */
  public KeyCodeCombination ctrlS = new KeyCodeCombination(KeyCode.S,KeyCombination.CONTROL_DOWN);
  
  /** The CTRL-O keyboard event. */
  public KeyCodeCombination ctrlO = new KeyCodeCombination(KeyCode.O,KeyCombination.CONTROL_DOWN);
  
  /** The CTRL-X keyboard event. */
  public KeyCodeCombination ctrlX = new KeyCodeCombination(KeyCode.X,KeyCombination.CONTROL_DOWN);

  /** The menu bar. */
  @FXML
  private MenuBar menuBar;
  
  /** The about alert. */
  private AboutAlert aboutAlert = new AboutAlert("Status Reporter");
  
  /** The close action. */
  private ExitAction exitAction = new ExitAction();

  /**
   * Handle action related to "About" menu item.
   * 
   * @param event Event on "About" menu item.
   */
  @FXML
  private void handleAboutAction(final ActionEvent event) {
    provideAboutFunctionality();
  }

  /**
   * Handle data wbs action.
   *
   * @param event the event
   */
  @FXML
  private void handleDataWbsAction(final ActionEvent event) {
    provideDataWbsFunctionality();
  }

  /**
   * Handle data activity action.
   *
   * @param event the event
   */
  @FXML
  private void handleDataActivityAction(final ActionEvent event) {
    provideDataActivityFunctionality();
  }

  /**
   * Handle export action.
   *
   * @param event the event
   */
  @FXML
  private void handleExportAction(final ActionEvent event) {
    provideExportFunctionality();
  }

  /**
   * Handle exit action.
   *
   * @param event the event
   */
  @FXML
  private void handleExitAction(final ActionEvent event) {
    exitAction.exit();
    System.exit(0);
  }

  /**
   * Handle action related to input.
   * 
   * @param event Input event.
   */
  @FXML
  public void handleKeyInput(final InputEvent event) {
    if (event instanceof KeyEvent) {
      final KeyEvent keyEvent = (KeyEvent) event;

      if (keyEvent.isControlDown() && ctrlA.match(keyEvent)) {
        provideAboutFunctionality();
      }

      if (keyEvent.isControlDown() && ctrlX.match(keyEvent)) {
        exitAction.exit();
      }
    }
  }

  //TODO - refactor
  /**
   * Gets the task table.
   *
   * @return the task table
   */
  @SuppressWarnings("unchecked")
  private TableView<TaskViewModel> getTaskTable() {
    TableView<TaskViewModel> taskTable = null;
    Stage stage = (Stage) menuBar.getScene().getWindow();
    Parent scene = stage.getScene().getRoot();
    
    for (Node node : scene.getChildrenUnmodifiable()) {
      
      if ("borderPane".equals(node.getId())) {
        BorderPane bp = (BorderPane) node;
        
        for (Node node1 : bp.getChildrenUnmodifiable()) {
          
          if ("taskTable".equals(node1.getId())) {
            taskTable = (TableView<TaskViewModel>) node1;
          }

        }
      
      }

    }
    
    return taskTable;
  }
  
  /**
   * Provide export functionality.
   */
  private void provideExportFunctionality() {
    LOGGER.debug("  BEGIN export ...");
    
    TableView<TaskViewModel> taskTable = getTaskTable();

    LOGGER.debug(" taskTable: {}", taskTable);
    
    for(TaskViewModel taskItem : taskTable.getItems()) {
      LOGGER.debug("  taskItem: {}", taskItem.toString());
    }
    
    LOGGER.debug("  END export ...");
  }

  /**
   * Perform functionality associated with "About" menu selection or CTRL-A.
   */
  private void provideAboutFunctionality() {
    aboutAlert.show();
  }
  
  /**
   * Provide data wbs functionality.
   */
  @SuppressWarnings("unchecked")
  private void provideDataWbsFunctionality() {
    ModelTableCells<WorkBreakdownStructure> cells = new ModelTableCells<WorkBreakdownStructure>();

    try {
      cells.addAll(SrModelServiceFactory.WorkBreakdownStructure.getServiceInstance().readAll());
      new WbsTableView(cells.getAll());
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
  }

  /**
   * Provide data activity functionality.
   */
  @SuppressWarnings("unchecked")
  private void provideDataActivityFunctionality() {
    ModelTableCells<Activity> cells = new ModelTableCells<Activity>();

    try {
      cells.addAll(SrModelServiceFactory.Activity.getServiceInstance().readAll());
      new ActivityTableView(cells.getAll());
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
  }

  /**
   * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
   */
  public void initialize(URL url, ResourceBundle resourceBundle) {
    menuBar.setFocusTraversable(true);
  }

}
