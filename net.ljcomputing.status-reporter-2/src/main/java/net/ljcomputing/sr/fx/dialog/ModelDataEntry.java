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

package net.ljcomputing.sr.fx.dialog;

import net.ljcomputing.model.Model;

import java.util.Optional;

import javafx.scene.control.ButtonType;

/**
 * Interface for a model data entry dialog.
 *
 * @author James G. Willmore
 * @param <T> the generic type
 */
public interface ModelDataEntry<T extends Model> {
  
  /**
   * Creates the dialog.
   *
   * @param model the model
   * @throws Exception the exception
   */
  public void createDialog(T model) throws Exception;

  /**
   * Show and wait.
   *
   * @param model the model
   * @return the optional
   * @throws Exception the exception
   */
  public Optional<ButtonType> showAndWait(T model) throws Exception;
  
  /**
   * Gets the item.
   *
   * @return the item
   */
  public T getItem();
}
