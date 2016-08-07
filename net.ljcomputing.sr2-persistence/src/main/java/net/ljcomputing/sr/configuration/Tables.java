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


package net.ljcomputing.sr.configuration;

import net.ljcomputing.persistence.DataSourceTable;
import net.ljcomputing.sr.initialize.DdlStatements;
import net.ljcomputing.sr.model.Activity;
import net.ljcomputing.sr.model.ActivityViewModel;
import net.ljcomputing.sr.model.Task;
import net.ljcomputing.sr.model.TaskViewModel;
import net.ljcomputing.sr.model.WorkBreakdownStructure;

/**
 * @author James G. Willmore
 *
 */
public enum Tables implements DataSourceTable {
  WBS("wbs", DdlStatements.WBS_DDL, WorkBreakdownStructure.class), 
  ACTIVITY("activity", DdlStatements.ACTIVITY_DDL, Activity.class), 
  TASK("task", DdlStatements.TASK_DDL, Task.class),
  TASK_VIEW("task_view", DdlStatements.TASK_VIEW_DDL, TaskViewModel.class),
  ACTIVITY_VIEW("activity_view", DdlStatements.ACTIVITY_VIEW_DDL, ActivityViewModel.class);

  /**
   * Instantiates a new data source table.
   *
   * @param tableName the table name
   * @param ddl the DDL statement used to create the table
   * @param model the model class
   */
  private Tables(String tableName, String ddl, Class<?> model) {
    this.tableName = tableName;
    this.ddl = ddl;
    this.model = model;
  }

  /** The table name. */
  private String tableName;

  /** The DDL statement used to create the table. */
  private String ddl;

  /** The model class. */
  private Class<?> model;

  /**
   * Gets the table name.
   *
   * @return the table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * Gets the DDL statement used to create the table.
   *
   * @return the DDL statement
   */
  public String getDDl() {
    return ddl;
  }

  /**
   * Gets the model class.
   *
   * @return the model
   */
  public Class<?> getModel() {
    return model;
  }

}
