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
import net.ljcomputing.sr.model.TaskViewModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Task view model repository implementation.
 * 
 * @author James G. Willmore
 *
 */
public class TaskViewModelRepositoryImpl extends AbstractRepository<TaskViewModel> {

  /**
   * Instantiates a new task view model repository impl.
   *
   * @throws PersistenceException the persistence exception
   */
  public TaskViewModelRepositoryImpl() throws PersistenceException {
    super(Tables.TASK_VIEW);
  }

  /**
   * @see net.ljcomputing.repository.impl.AbstractRepository#create(net.ljcomputing.model.Model, java.lang.String[])
   */
  @Override
  public void create(TaskViewModel model, String... columns) throws PersistenceException {
    throw new PersistenceException("Not implemented");
  }

  /**
   * @see net.ljcomputing.repository.impl.AbstractRepository#update(net.ljcomputing.model.Model, java.lang.String[])
   */
  @Override
  public void update(TaskViewModel model, String... columns) throws PersistenceException {
    throw new PersistenceException("Not implemented");
  }

  /**
   * @see net.ljcomputing.repository.impl.AbstractRepository#delete(net.ljcomputing.model.Model)
   */
  @Override
  public void delete(TaskViewModel model) throws PersistenceException {
    throw new PersistenceException("Not implemented");
  }

  /**
   * @see net.ljcomputing.repository.impl.AbstractRepository#delete(java.lang.Integer)
   */
  @Override
  public void delete(Integer id) throws PersistenceException {
    throw new PersistenceException("Not implemented");
  }
  
  /**
   * Read tasks between the given by start and end times.
   *
   * @param startTime the start time
   * @param endTime the end time
   * @return the list
   * @throws PersistenceException the persistence exception
   */
  public List<TaskViewModel> readByTimes(Timestamp startTime, Timestamp endTime) throws PersistenceException {
    String sql = "select * from " + table.getTableName() + 
        " where start_time >= ? and "
        + "(end_time <= ? or end_time is null) order by start_time desc, end_time desc ";
    List<TaskViewModel> list = new ArrayList<TaskViewModel>();
    ResultSet rs = null;
    
    try {
      preparedStatement = obtainPreparedStatement(sql);
      
      preparedStatement.setObject(1, startTime);
      preparedStatement.setObject(2, endTime);
      
      rs = preparedStatement.executeQuery();
      
      while(rs.next()) {
        TaskViewModel entity = getModelInstance();
        entity.populate(entityPopulator, rs);
        list.add(entity);
      }
      
      closePreparedStatement();
      closeConnection();
    } catch(SQLException exception) {
      throw new PersistenceException(exception);
    } catch(Exception exception) {
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
    
    return list;
  }
}
