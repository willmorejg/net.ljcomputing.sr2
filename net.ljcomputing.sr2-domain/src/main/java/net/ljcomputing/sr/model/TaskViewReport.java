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

package net.ljcomputing.sr.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Task view report (used in exporting data to CSV).
 * 
 * @author James G. Willmore
 *
 */
public class TaskViewReport extends SrConstants {

  /** The header. */
  public static final String[] CVS_RECORD_HEADER = { "WBS Name", "Activity Name", "Duration" };

  /** The activity name. */
  private transient final String activityName;

  /** The wbs name. */
  private transient final String wbsName;

  /** The elapsed time in hours. */
  private transient double elapsedHours;

  /**
   * Instantiates a new task view report.
   *
   * @param model the model
   */
  public TaskViewReport(final TaskViewModel model) {
    super();
    wbsName = model.getWbsName();
    activityName = model.getActivityName();
    addElapsedHours(model.getElapsedHours());
  }

  /**
   * Adds the elapsed hours.
   *
   * @param elapsedHours the elapsed hours
   */
  public void addElapsedHours(final double elapsedHours) {
    this.elapsedHours += elapsedHours;
  }

  /**
   * To values list.
   *
   * @return the list
   */
  public List<String> toValuesList() {
    final List<String> values = new ArrayList<String>();

    values.add(wbsName);
    values.add(activityName);
    values.add(DECIMAL_FORMAT.format(elapsedHours));

    return values;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
