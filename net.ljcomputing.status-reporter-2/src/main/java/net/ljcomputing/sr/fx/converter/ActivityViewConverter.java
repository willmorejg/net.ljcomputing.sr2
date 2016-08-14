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


package net.ljcomputing.sr.fx.converter;

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
  private transient final ObservableList<ActivityViewModel> list;
  
  /**
   * Instantiates a new activity view converter.
   *
   * @param list the list
   */
  public ActivityViewConverter(final ObservableList<ActivityViewModel> list) {
    super();
    this.list = list;
  }

  /**
   * @see javafx.util.StringConverter#toString(java.lang.Object)
   */
  @Override
  public String toString(final ActivityViewModel object) {
    return String.format("Activity %s (WBS %s)", object.getName(), object.getWbsName());
  }

  /**
   * @see javafx.util.StringConverter#fromString(java.lang.String)
   */
  @Override
  public ActivityViewModel fromString(final String string) {
    ActivityViewModel result = null;
    
    for(final ActivityViewModel item : list) {
      final String name = item.getName();
      final String wbsName = item.getWbsName();
      final String formatedName = String.format("Activity %s (WBS %s)", name, wbsName);
      
      if(formatedName.equals(string)) {
        result = item;
        break;
      }
    }
    
    return result;
  }
}
