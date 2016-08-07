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

import net.ljcomputing.model.Model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author James G. Willmore
 *
 */
public class ModelTableCells<T extends Model> {
  private ObservableList<T> list = FXCollections.observableArrayList();
  
  public void add(T model) {
    list.add(model);
  }
  
  public void addAll(List<T> models) {
    list.addAll(models);
  }
  
  public T get(int index) {
    return list.get(index);
  }
  
  public ObservableList<T> getAll() {
    return list;
  }
  
  public T remove(int index) {
    return list.remove(index);
  }
  
  public T find(Integer id) {
    for(T model : list) {
      if(model.getId().equals(id)) {
        return model;
      }
    }
    
    return null;
  }
}