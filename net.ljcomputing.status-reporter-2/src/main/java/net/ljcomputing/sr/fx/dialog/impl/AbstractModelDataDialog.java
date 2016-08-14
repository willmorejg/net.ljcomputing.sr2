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

import net.ljcomputing.model.Model;
import net.ljcomputing.sr.fx.dialog.ModelDataEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Abstract JavaFX data entry dialog implementation.
 * 
 * @author James G. Willmore
 *
 */
abstract class AbstractModelDataDialog<T extends Model> implements ModelDataEntry<T> {
  @SuppressWarnings("unused")
  private final Logger logger = LoggerFactory.getLogger(AbstractModelDataDialog.class);

  /** The item. */
  protected T item;

  /** The lbl id. */
  private Label lblId = new Label("ID");

  /** The id field. */
  protected TextField idField = new TextField();

  /** The pane. */
  private GridPane pane = new GridPane();

  /** The alert. */
  protected Alert alert = new Alert(AlertType.NONE);

  /**
   * Instantiates a new abstract model data dialog.
   *
   * @throws Exception the exception
   */
  public AbstractModelDataDialog() throws Exception {
    super();
  }

  /**
   * Adds the id nodes.
   *
   * @return the node[]
   */
  private Node[] addIdNodes() {
    idField.setEditable(false);
    idField.setDisable(true);
    return new Node[] { lblId, idField };
  }

  /**
   * Adjust nodes.
   *
   * @param nodes the nodes
   * @return the node[]
   */
  private Node[] adjustNodes(Node... nodes) {
    Node[] newNodes = new Node[nodes.length + 2];

    for (int n = 0; n < addIdNodes().length; n++) {
      newNodes[n] = addIdNodes()[n];
    }

    for (int n = 2; (n - 2) < nodes.length; n++) {
      newNodes[n] = nodes[n - 2];
    }

    return newNodes;
  }

  /**
   * Adds the content.
   *
   * @param nodes the nodes
   */
  protected void addContent(Node... nodes) {
    Node[] newNodes = adjustNodes(nodes);

    int r = 1;
    for (int n = 0; n < newNodes.length; n++) {
      pane.add(newNodes[n], ((n % 2) + 1), r);
      if ((n % 2) != 0) {
        r++;
      }
    }

    alert.getDialogPane().setContent(pane);
    alert.getDialogPane().getButtonTypes().addAll(ButtonType.OK,
        ButtonType.CANCEL);
  }

  /**
   * @see net.ljcomputing.sr.fx.dialog.ModelDataEntry#showAndWait(net.ljcomputing.sr.model.Model)
   */
  public Optional<ButtonType> showAndWait(T item) throws Exception {
    createDialog(item);
    
    if(item.getId() != null) {
      idField.setText(item.getId().toString());
    }
    return alert.showAndWait();
  }
}
