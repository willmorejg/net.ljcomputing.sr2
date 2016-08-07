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

import net.ljcomputing.sr.model.WorkBreakdownStructure;

import javafx.collections.ObservableList;
import javafx.util.StringConverter;

/**
 * Work breakdown structure converter.
 * <p>
 * Used with a ComboBox to convert a WorkBreakdownStructure.
 * 
 * @author James G. Willmore
 *
 */
public class WbsConverter extends StringConverter<WorkBreakdownStructure> {
  
  /** The list. */
  private ObservableList<WorkBreakdownStructure> list;
  
  /**
   * Instantiates a new wbs converter.
   *
   * @param list the list
   */
  public WbsConverter(ObservableList<WorkBreakdownStructure> list) {
    this.list = list;
  }

  /**
   * @see javafx.util.StringConverter#toString(java.lang.Object)
   */
  @Override
  public String toString(WorkBreakdownStructure object) {
    return object.getName();
  }

  /**
   * @see javafx.util.StringConverter#fromString(java.lang.String)
   */
  @Override
  public WorkBreakdownStructure fromString(String string) {
    for(WorkBreakdownStructure item : list) {
      if(item.getName().equals(string)) {
        return item;
      }
    }
    
    return null;
  }
}
