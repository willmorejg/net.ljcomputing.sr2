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

package net.ljcomputing.sr.service.impl;

import net.ljcomputing.exception.PersistenceException;
import net.ljcomputing.exception.ServiceException;
import net.ljcomputing.service.impl.AbstractService;
import net.ljcomputing.sr.model.Task;
import net.ljcomputing.sr.repository.impl.TaskRepositoryImpl;
import net.ljcomputing.sr.service.TaskService;

import java.util.Date;

/**
 * Task service implementation.
 * 
 * @author James G. Willmore
 *
 */
public class TaskServiceImpl extends AbstractService<Task, TaskRepositoryImpl>
    implements TaskService {
  
  /**
   * Instantiates a new task service impl.
   *
   * @throws ServiceException the service exception
   */
  public TaskServiceImpl() throws ServiceException {
    super();
  }

  /**
   * @see net.ljcomputing.service.impl.AbstractService#getColumns()
   */
  public String[] getColumns() {
    return COLUMNS;
  }
  
  /**
   * @see net.ljcomputing.sr.service.TaskService#newTaskStartTime()
   */
  public Date newTaskStartTime() throws ServiceException {
    try {
      return repository.maxStartTimeDate();
    } catch (PersistenceException exception) {
      throw new ServiceException(exception);
    }
  }

  /**
   * @see net.ljcomputing.sr.service.TaskService#endOpenTasks(java.util.Date)
   */
  public void endOpenTasks(Date endTime) throws ServiceException {
    try {
      repository.endOpenTasks(endTime);
    } catch (Exception exception) {
      throw new ServiceException(exception);
    }
  }
}
