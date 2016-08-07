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

package net.ljcomputing.sr.initialize;

/**
 * Interface containing the DDL statements used to create the Status Reporter tables.
 * 
 * @author James G. Willmore
 *
 */
public interface DdlStatements {
  
  /** The WBS table DDL statement. */
  static final String WBS_DDL = "create table wbs ("
      + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"
      + ",name varchar(255)" + ",description varchar(255))";

  /** The ACTIVITY table DDL statement. */
  static final String ACTIVITY_DDL = "create table activity ("
      + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"
      + ",name varchar(255)" + ",description varchar(255)" + ",wbs integer not null" + ")";

  /** The Constant ACTIVITY_ALTER_DDL. */
  static final String ACTIVITY_ALTER_DDL = "alter table activity "
      + "add foreign key (wbs) "
      + "references wbs(id) "
      + "on delete cascade";

  /** The TASK table DDL statement. */
  static final String TASK_DDL = "create table task ("
      + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"
      + ",start_time timestamp" + ",end_time timestamp" + ",comments varchar(255)" + ",activity integer not null" + ")";

  /** The Constant TASK_ALTER_DDL. */
  static final String TASK_ALTER_DDL = "alter table activity "
      + "add foreign key (acivity) "
      + "references activity(id) "
      + "on delete cascade";

  /** The TASK_VIEW DDL statement. */
  static final String TASK_VIEW_DDL = "create view task_view ("
      + "id, start_time, end_time, comments, activity_id, "
      + "activity_name, activity_description, "
      + "wbs_id, wbs_name, wbs_description) as "
      + "select t.id as id, t.start_time as start_time, t.end_time as end_time, t.comments as comments, "
      + "a.id as activity_id, a.name as activity_name, a.description as activity_description, "
      + "w.id as wbs_id, w.name as wbs_name, w.description as wbs_description "
      + "from task t "
      + "join activity a on t.activity=a.id "
      + "join wbs w on a.wbs=w.id";

  static final String ACTIVITY_VIEW_DDL = "create view activity_view ("
      + "id, name, description, "
      + "wbs_id, wbs_name, wbs_description) as "
      + "select a.id as id, a.name as name, a.description as activity_description, "
      + "w.id as wbs_id, w.name as wbs_name, w.description as wbs_description "
      + "from activity a  "
      + "join wbs w on a.wbs=w.id";
}
