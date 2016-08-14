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

package net.ljcomputing.sr.repository.impl;

import net.ljcomputing.exception.PersistenceException;
import net.ljcomputing.repository.impl.AbstractRepository;
import net.ljcomputing.sr.configuration.Tables;
import net.ljcomputing.sr.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Task repository implementation.
 * 
 * @author James G. Willmore
 *
 */
public class TaskRepositoryImpl extends AbstractRepository<Task> {

  /**
   * Instantiates a new task repository impl.
   *
   * @throws PersistenceException the persistence exception
   */
  public TaskRepositoryImpl() throws PersistenceException {
    super(Tables.TASK);
  }

  /**
   * Maximum start time as a String.
   *
   * @return the string
   * @throws PersistenceException the persistence exception
   */
  public String maxStartTime() throws PersistenceException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(maxStartTimeDate());
  }
  
  /**
   * Maximum start time as a Date.
   *
   * @return the date
   * @throws PersistenceException the persistence exception
   */
  public Date maxStartTimeDate() throws PersistenceException {
    String sql = "select max(start_time) from " + table.getTableName();
    Date now = new Date();
    Date maxTimestamp = now;
    ResultSet rs = null;
    
    try {
      preparedStatement = obtainPreparedStatement(sql);
      
      rs = preparedStatement.executeQuery();

      while(rs.next()) {
        if(rs.getTimestamp(1) != null && now.before(rs.getTimestamp(1))) {
          maxTimestamp = rs.getTimestamp(1);
        }
      }
      
      closePreparedStatement();
      closeConnection();
    } catch(SQLException exception) {
      throw new PersistenceException(exception);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (SQLException e) {
        //do nothing
      }
    }
    
    return maxTimestamp;
  }

  /**
   * End open tasks.
   *
   * @param endTime the end time
   * @throws PersistenceException the persistence exception
   */
  public void endOpenTasks(Date endTime) throws PersistenceException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String sql = "update " + table.getTableName() + " set end_time = '"
        + sdf.format(endTime) + "' where end_time is null";

    try {
      preparedStatement = obtainPreparedStatement(sql);

      preparedStatement.executeUpdate();
      
      closePreparedStatement();
      closeConnection();
    } catch(SQLException exception) {
      throw new PersistenceException(exception);
    }
  }
}
