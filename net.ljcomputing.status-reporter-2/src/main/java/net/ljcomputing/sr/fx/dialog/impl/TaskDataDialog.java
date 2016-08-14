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

import net.ljcomputing.fx.control.time.TimeControl;
import net.ljcomputing.fx.control.time.TimeSpinner;
import net.ljcomputing.sr.fx.converter.ActivityViewConverter;
import net.ljcomputing.sr.model.ActivityViewModel;
import net.ljcomputing.sr.model.Task;
import net.ljcomputing.sr.model.TaskViewModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

/**
 * A JavaFX task data entry dialog.
 * 
 * @author James G. Willmore
 *
 */
public class TaskDataDialog
    extends AbstractModelDataDialog<TaskViewModel> {

  /** The logger. */
  @SuppressWarnings("unused")
  private final Logger logger = LoggerFactory.getLogger(TaskDataDialog.class);
  
  /** The activity list. */
  private ObservableList<ActivityViewModel> activityViewList;
  
  /** The previous item. */
  private TaskViewModel previousItem;
  
  /** The lbl activity. */
  private Label lblActivity = new Label("Activity: ");

  /** The activity field. */
  private ComboBox<ActivityViewModel> activityField;
  
  /** The start date field. */
  private DatePicker startDateField;
  
  /** The lbl start time. */
  private Label lblStartTime = new Label("Start Time: ");
  
  /** The start time field. */
  private TimeControl startTimeField;

  /** The end date field. */
  private DatePicker endDateField;
  
  /** The lbl end time. */
  private Label lblEndTime = new Label("End Time: ");
  
  /** The end time field. */
  private TimeControl endTimeField;
  
  /** The lbl comments. */
  private Label lblComments = new Label("Comments: ");
  
  /** The comments. */
  private TextArea commentsField;

  /**
   * Instantiates a new task data dialog.
   *
   * @param activityViewList the activity view list
   * @param previousItem the previous item
   * @throws Exception the exception
   */
  public TaskDataDialog(ObservableList<ActivityViewModel> activityViewList, TaskViewModel previousItem) throws Exception {
    super();
    this.activityViewList = activityViewList;
    this.previousItem = previousItem;
  }

  /**
   * @see net.ljcomputing.sr.fx.dialog.ModelDataEntry#createDialog(net.ljcomputing.sr.model.Model)
   */
  public void createDialog(TaskViewModel item) throws Exception {
    this.item = item;
    Task task = item.getTask();
    createDialog(task);
  }

  /**
   * Creates the dialog.
   *
   * @param item the item
   * @throws Exception the exception
   */
  private void createDialog(Task item) throws Exception {
    activityField = new ComboBox<ActivityViewModel>();

    activityField.setItems(activityViewList);
    activityField.setConverter(new ActivityViewConverter(activityViewList));
    activityField.setValue(getSelectedActivity(item.getActivity()));

    HBox startHbox = new HBox();
    startDateField = new DatePicker(LocalDateTime.ofInstant(item.getStartTime().toInstant(), ZoneId.systemDefault()).toLocalDate());
    startTimeField = new TimeControl(item.getStartTime());
    Button adjustStartTimeButton = new Button("Set start from previous Task");
    
    adjustStartTimeButton.setOnAction(new EventHandler<ActionEvent>() {
      
      @Override
      public void handle(ActionEvent event) {
        Date previousStart = previousItem.getEndTime();
        LocalDateTime sldt = LocalDateTime.ofInstant(previousStart.toInstant(), ZoneId.systemDefault());
        startDateField.setValue(sldt.toLocalDate());
        startTimeField.setLocalTime(sldt.toLocalTime());
      }
    });
    
    if(previousItem == null) {
      adjustStartTimeButton.setVisible(false);
    }
    
    startTimeField.spinnerProperty().valueProperty().addListener((obs, oldValue, newValue) -> {
      Button okButton = (Button) super.alert.getDialogPane().lookupButton(ButtonType.OK);
      TimeSpinner endSpinner = endTimeField.spinnerProperty();
      ReadOnlyProperty<LocalTime> endValue = endSpinner.valueProperty();
      LocalTime end = endValue.getValue();
      
      if(end == null) {
        okButton.setDisable(false);
        return;
      }

      okButton.setDisable(!endSpinner.isAfter(newValue.atDate(LocalDate.now())));
      
    });
    
    startDateField.valueProperty().addListener((obs, oldValue, newValue) -> {
      Button okButton = (Button) super.alert.getDialogPane().lookupButton(ButtonType.OK);

      if(endDateField.getValue() != null && newValue.isAfter(endDateField.getValue())) {
        okButton.setDisable(true);
      } else {
        okButton.setDisable(false);
      }
      
    });

    startHbox.getChildren().addAll(startDateField, startTimeField, adjustStartTimeButton);
    
    HBox endHbox = new HBox();
    LocalDate endDateLocal = item.getEndTime() != null ? LocalDateTime.ofInstant(item.getEndTime().toInstant(), ZoneId.systemDefault()).toLocalDate() : null;
    endDateField = new DatePicker();
    endDateField.setValue(endDateLocal);
    endTimeField = new TimeControl(item.getEndTime());
    
    Button clearButton = new Button("Clear end time");
    
    clearButton.setOnAction(e -> {
        endDateField.setValue(null);
        endTimeField.setLocalTime((LocalTime)null);
        endTimeField.setVisible(false);
    });
    
    if(item.getEndTime() == null) {
      endTimeField.setVisible(false);
      clearButton.setVisible(false);
    }
    
    endDateField.valueProperty().addListener((obs, oldValue, newValue) -> {
      if(newValue != null) {
        endTimeField.setVisible(true);
        endTimeField.setLocalTime(startTimeField.getLocalTime().plusSeconds(1));
        clearButton.setVisible(true);
      } else {
        endTimeField.setVisible(false);
        clearButton.setVisible(false);
      }
      
      Button okButton = (Button) super.alert.getDialogPane().lookupButton(ButtonType.OK);
      
      if(newValue != null && newValue.isBefore(startDateField.getValue())) {
        okButton.setDisable(true);
      } else {
        okButton.setDisable(false);
      }
      
    });
    
    endTimeField.spinnerProperty().valueProperty().addListener((obs, oldValue, newValue) -> {
      Button okButton = (Button) super.alert.getDialogPane().lookupButton(ButtonType.OK);
      TimeSpinner startSpinner = startTimeField.spinnerProperty();
      
      if(newValue == null) {
        okButton.setDisable(false);
        return;
      }

      okButton.setDisable(startSpinner.isAfter(newValue.atDate(LocalDate.now())));
    });
    
    endHbox.getChildren().addAll(endDateField, endTimeField, clearButton);

    commentsField = new TextArea(item.getComments());

    addContent(lblActivity, activityField, lblStartTime, startHbox, lblEndTime, endHbox, lblComments, commentsField);
  }

  /**
   * @see net.ljcomputing.sr.fx.dialog.ModelDataEntry#getItem()
   */
  public TaskViewModel getItem() {
    if (!"".equals(idField.getText())) {
      item.setId(Integer.valueOf(idField.getText()));
    }
    
    item.setActivityId(activityField.getSelectionModel().getSelectedItem().getId());
    
    item.setComments(commentsField.getText());
    
    LocalDateTime startLdt = LocalDateTime.of(startDateField.getValue(), startTimeField.getLocalTime());
    item.setStartTime(Date.from(startLdt.atZone(ZoneId.systemDefault()).toInstant()));

    if(endDateField.getValue() != null && endTimeField.getLocalTime() != null) {
      LocalDateTime endLdt = LocalDateTime.of(endDateField.getValue(), endTimeField.getLocalTime());
      item.setEndTime(Date.from(endLdt.atZone(ZoneId.systemDefault()).toInstant()));
    } else {
      item.setEndTime(null);
    }
    
    return item;
  }

  private ActivityViewModel getSelectedActivity(Integer selected) {
    for(ActivityViewModel item : activityViewList) {
      if(selected.equals(item.getId())) {
        return item;
      }
    }
    
    return null;
  }
}
