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
import net.ljcomputing.sr.model.ActivityViewModel;

/**
 * Activity view model repository implementation.
 * 
 * @author James G. Willmore
 *
 */
public class ActivityViewModelRepositoryImpl extends AbstractRepository<ActivityViewModel> {

  /**
   * Instantiates a new activity view model repository impl.
   *
   * @throws PersistenceException the persistence exception
   */
  public ActivityViewModelRepositoryImpl() throws PersistenceException {
    super(Tables.ACTIVITY_VIEW);
  }

  /**
   * @see net.ljcomputing.repository.impl.AbstractRepository#create(net.ljcomputing.model.Model, java.lang.String[])
   */
  @Override
  public void create(ActivityViewModel model, String... columns) throws PersistenceException {
    throw new PersistenceException("Not implemented");
  }

  /**
   * @see net.ljcomputing.repository.impl.AbstractRepository#update(net.ljcomputing.model.Model, java.lang.String[])
   */
  @Override
  public void update(ActivityViewModel model, String... columns) throws PersistenceException {
    throw new PersistenceException("Not implemented");
  }

  /**
   * @see net.ljcomputing.repository.impl.AbstractRepository#delete(net.ljcomputing.model.Model)
   */
  @Override
  public void delete(ActivityViewModel model) throws PersistenceException {
    throw new PersistenceException("Not implemented");
  }

  /**
   * @see net.ljcomputing.repository.impl.AbstractRepository#delete(java.lang.Integer)
   */
  @Override
  public void delete(Integer id) throws PersistenceException {
    throw new PersistenceException("Not implemented");
  }
}
