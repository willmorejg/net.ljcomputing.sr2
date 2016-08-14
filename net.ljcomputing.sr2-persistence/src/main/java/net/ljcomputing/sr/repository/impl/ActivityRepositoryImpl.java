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
import net.ljcomputing.sr.model.Activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity repository implementation.
 * 
 * @author James G. Willmore
 *
 */
public class ActivityRepositoryImpl extends AbstractRepository<Activity> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = LoggerFactory.getLogger(ActivityRepositoryImpl.class);

  /**
   * Instantiates a new activity repository impl.
   *
   * @throws PersistenceException the persistence exception
   */
  public ActivityRepositoryImpl() throws PersistenceException {
    super(Tables.ACTIVITY);
  }

  /**
   * Read all by wbs.
   *
   * @param wbs the wbs
   * @return the list
   * @throws PersistenceException the persistence exception
   */
  public List<Activity> readAllByWbs(Integer wbs) throws PersistenceException {
    String sql = "select * from " + table.getTableName() + " where wbs=?";
    List<Activity> list = new ArrayList<Activity>();
    ResultSet rs = null;
    
    try {
      preparedStatement = obtainPreparedStatement(sql);
      
      preparedStatement.setObject(1, wbs);
      
      rs = preparedStatement.executeQuery();
      
      while(rs.next()) {
        Activity entity = getModelInstance();
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
