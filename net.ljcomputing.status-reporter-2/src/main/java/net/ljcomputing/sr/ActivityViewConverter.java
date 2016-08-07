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


package net.ljcomputing.sr;

import net.ljcomputing.sr.model.ActivityViewModel;

import javafx.collections.ObservableList;
import javafx.util.StringConverter;

/**
 * Activity view model converter.
 * <p>
 * Used with a ComboBox to convert an ActivityViewModel.
 * 
 * @author James G. Willmore
 *
 */
public class ActivityViewConverter extends StringConverter<ActivityViewModel> {
  
  /** The list. */
  private ObservableList<ActivityViewModel> list;
  
  /**
   * Instantiates a new activity view converter.
   *
   * @param list the list
   */
  public ActivityViewConverter(ObservableList<ActivityViewModel> list) {
    this.list = list;
  }

  /**
   * @see javafx.util.StringConverter#toString(java.lang.Object)
   */
  @Override
  public String toString(ActivityViewModel object) {
    return String.format("Activity %s (WBS %s)", object.getName(), object.getWbsName());
  }

  /**
   * @see javafx.util.StringConverter#fromString(java.lang.String)
   */
  @Override
  public ActivityViewModel fromString(String string) {
    for(ActivityViewModel item : list) {
      String formatedName = String.format("Activity %s (WBS %s)", item.getName(), item.getWbsName());
      if(formatedName.equals(string)) {
        return item;
      }
    }
    
    return null;
  }
}
