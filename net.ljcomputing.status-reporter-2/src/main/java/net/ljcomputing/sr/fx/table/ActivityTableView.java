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

package net.ljcomputing.sr.fx.table;

import net.ljcomputing.fx.alert.ErrorAlert;
import net.ljcomputing.sr.fx.table.cell.WbsComboTableCell;
import net.ljcomputing.sr.model.Activity;
import net.ljcomputing.sr.model.WorkBreakdownStructure;
import net.ljcomputing.sr.service.SrModelServiceFactory;
import net.ljcomputing.sr.service.impl.ActivityServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * Implementation of an activity table view.
 * 
 * @author James G. Willmore
 *
 */
public class ActivityTableView extends TableView<Activity> {
  
  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(ActivityTableView.class);
  
  /** The wbs list. */
  private ObservableList<WorkBreakdownStructure> wbsList;

  /** The id col. */
  private TableColumn<Activity, Integer> idCol = new TableColumn<Activity, Integer>(
      "ID");
  
  /** The wbs col. */
  private TableColumn<Activity, String> wbsCol = new TableColumn<Activity, String>(
      "WBS");
  
  /** The name col. */
  private TableColumn<Activity, String> nameCol = new TableColumn<Activity, String>(
      "Name");
  
  /** The description col. */
  private TableColumn<Activity, String> descriptionCol = new TableColumn<Activity, String>(
      "Description");

  /**
   * Instantiates a new activity table view.
   *
   * @param list the list
   */
  public ActivityTableView(ObservableList<Activity> list) {
    setItems(list);
    setEditable(true);

    try {
      initWbsCombo();
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }
    
    initColumns();

    StackPane sp = new StackPane(this);

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Data Management");
    alert.setHeaderText("Work Breakdown Structures");
    alert.getDialogPane().setContent(sp);
    alert.setResizable(true);
    alert.showAndWait();
  }

  /**
   * Inits the columns.
   */
  @SuppressWarnings("unchecked")
  private void initColumns() {
    idCol
        .setCellValueFactory(new PropertyValueFactory<Activity, Integer>("id"));
    
    nameCol.setCellValueFactory(
        new PropertyValueFactory<Activity, String>("name"));

    nameCol.setEditable(true);
    nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

    nameCol.setOnEditCommit((CellEditEvent<Activity, String> e) -> {
      Activity current = e.getRowValue();
      Activity model = new Activity(e.getNewValue(), current.getId());
      model.setId(current.getId());

      try {
        SrModelServiceFactory.ActivityView.getServiceInstance().update(model);
      } catch (Exception e1) {
        new ErrorAlert().show(e1);
      }

    });

    descriptionCol.setCellValueFactory(
        new PropertyValueFactory<Activity, String>("description"));

    descriptionCol.setEditable(true);
    descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

    descriptionCol.setOnEditCommit((CellEditEvent<Activity, String> e) -> {
      Activity model = e.getTableView().getItems().get(e.getTablePosition().getRow());
      model.setDescription(e.getNewValue());
      

      try {
        new ActivityServiceImpl().update(model);
      } catch (Exception e1) {
        new ErrorAlert().show(e1);
      }

    });

    getColumns().addAll(idCol, wbsCol, nameCol, descriptionCol);
  }

  /**
   * Inits the wbs combo.
   *
   * @throws Exception the exception
   */
  private void initWbsCombo() throws Exception {
    wbsCol.setEditable(true);
    populateWbsCombo();
  }

  /**
   * Populate wbs combo.
   *
   * @throws Exception the exception
   */
  @SuppressWarnings("unchecked")
  private void populateWbsCombo() throws Exception {
    wbsList = FXCollections.observableArrayList(SrModelServiceFactory.WorkBreakdownStructure.getServiceInstance().readAll());

    wbsCol.setCellValueFactory(new Callback<CellDataFeatures<Activity, String>, ObservableValue<String>>() {
      
      @Override
      public ObservableValue<String> call(CellDataFeatures<Activity, String> param) {
          Activity item = param.getValue();
          Integer wbsId = item.getWbs();
          return new SimpleObjectProperty<String>(getWbsNameById(wbsId));
      }
    });
    
    wbsCol.setCellFactory(column -> {
      return new WbsComboTableCell(wbsList);
    });
    
    wbsCol.setOnEditCommit((CellEditEvent<Activity, String> e) -> {
      Activity model = e.getTableView().getItems().get(e.getTablePosition().getRow());
      String wbsName = e.getNewValue();
      
      Activity newActivity = new Activity(model.getName(), model.getDescription(), getWbsIdByName(wbsName));
      newActivity.setId(model.getId());

      try {
        SrModelServiceFactory.Activity.getServiceInstance().update(newActivity);
      } catch (Exception e1) {
        new ErrorAlert().show(e1);
      }

    });

  }
  
  /**
   * Gets the wbs id by name.
   *
   * @param name the name
   * @return the wbs id by name
   */
  private Integer getWbsIdByName(String name) {
    WorkBreakdownStructure wbs = getWbsByName(name);
    return null != wbs ? wbs.getId() : 0;
  }

  /**
   * Gets the wbs by name.
   *
   * @param name the name
   * @return the wbs by name
   */
  private WorkBreakdownStructure getWbsByName(String name) {
    for(WorkBreakdownStructure wbs : wbsList) {
      if(wbs.getName().equals(name)) {
        return wbs;
      }
    }
    
    return null;
  }

  /**
   * Gets the wbs name by id.
   *
   * @param id the id
   * @return the wbs name by id
   */
  private String getWbsNameById(Integer id) {
    WorkBreakdownStructure wbs = getWbsById(id);
    return null != wbs ? wbs.getName() : "";
  }

  /**
   * Gets the wbs by id.
   *
   * @param id the id
   * @return the wbs by id
   */
  private WorkBreakdownStructure getWbsById(Integer id) {
    for(WorkBreakdownStructure wbs : wbsList) {
      if(wbs.getId().equals(id)) {
        return wbs;
      }
    }
    
    return null;
  }
}
