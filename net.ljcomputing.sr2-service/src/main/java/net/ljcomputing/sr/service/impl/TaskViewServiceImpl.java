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
import net.ljcomputing.sr.model.TaskViewModel;
import net.ljcomputing.sr.repository.impl.TaskViewModelRepositoryImpl;
import net.ljcomputing.sr.service.TaskViewService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Task view service implementation.
 * 
 * @author James G. Willmore
 */
public class TaskViewServiceImpl extends AbstractService<TaskViewModel, TaskViewModelRepositoryImpl>
    implements TaskViewService {
  
  /**
   * Instantiates a new task view service impl.
   *
   * @throws ServiceException the service exception
   */
  public TaskViewServiceImpl() throws ServiceException {
    super();
  }

  /**
   * @see net.ljcomputing.service.impl.AbstractService#getColumns()
   */
  public String[] getColumns() {
    return COLUMNS;
  }
  
  /**
   * @see net.ljcomputing.sr.service.TaskViewService#readByTimes(java.time.LocalDateTime, java.time.LocalDateTime)
   */
  public List<TaskViewModel> readByTimes(LocalDateTime startTime, LocalDateTime endTime) throws ServiceException {
    try {
      Timestamp startTimestamp = Timestamp.valueOf(startTime);
      Timestamp endTimestamp = Timestamp.valueOf(endTime);

      return repository.readByTimes(startTimestamp, endTimestamp);
    } catch (PersistenceException exception) {
      throw new ServiceException(exception);
    }
  }
}
