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
import net.ljcomputing.sr.model.WorkBreakdownStructure;
import net.ljcomputing.sr.service.impl.WorkBreakdownStructureServiceImpl;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;

/**
 * @author James G. Willmore
 *
 */
public class WbsTableView extends TableView<WorkBreakdownStructure> {
  private TableColumn<WorkBreakdownStructure, Integer> idCol = new TableColumn<WorkBreakdownStructure, Integer>("ID");
  private TableColumn<WorkBreakdownStructure, String> nameCol = new TableColumn<WorkBreakdownStructure, String>("Name");
  private TableColumn<WorkBreakdownStructure, String> descriptionCol = new TableColumn<WorkBreakdownStructure, String>("Description");

  public WbsTableView(ObservableList<WorkBreakdownStructure> list) {
    setItems(list);
    setEditable(true);
    initColumns();

    StackPane sp = new StackPane(this);
    
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Data Management");
    alert.setHeaderText("Work Breakdown Structures");
    alert.getDialogPane().setContent(sp);
    alert.setResizable(true);
    alert.showAndWait();
  }
  
  @SuppressWarnings("unchecked")
  private void initColumns() {
    idCol.setCellValueFactory(new PropertyValueFactory<WorkBreakdownStructure, Integer>("id"));

    nameCol.setCellValueFactory(new PropertyValueFactory<WorkBreakdownStructure, String>("name"));
    
    nameCol.setEditable(true);
    nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    
    nameCol.setOnEditCommit((CellEditEvent<WorkBreakdownStructure, String> e) -> {
      WorkBreakdownStructure current = e.getRowValue();//((WorkBreakdownStructure) e.getTableView().getItems().get(e.getTablePosition().getRow()));
      WorkBreakdownStructure model = new WorkBreakdownStructure(e.getNewValue(), current.getDescription());
      model.setId(current.getId());
      
      try {
        new WorkBreakdownStructureServiceImpl().update(model);
      } catch (Exception e1) {
        new ErrorAlert().show(e1);
      }
      
    });

    descriptionCol.setCellValueFactory(new PropertyValueFactory<WorkBreakdownStructure, String>("description"));
    
    descriptionCol.setEditable(true);
    descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
    
    descriptionCol.setOnEditCommit((CellEditEvent<WorkBreakdownStructure, String> e) -> {
      WorkBreakdownStructure model = e.getTableView().getItems().get(e.getTablePosition().getRow());
      model.setDescription(e.getNewValue());
      
      try {
        new WorkBreakdownStructureServiceImpl().update(model);
      } catch (Exception e1) {
        new ErrorAlert().show(e1);
      }
      
    });

    getColumns().addAll(idCol, nameCol, descriptionCol);
  }
}
