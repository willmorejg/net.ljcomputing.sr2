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

package net.ljcomputing.sr.service;

import net.ljcomputing.exception.ServiceException;
import net.ljcomputing.service.ModelService;
import net.ljcomputing.sr.model.Task;
import net.ljcomputing.sr.repository.impl.TaskRepositoryImpl;

import java.util.Date;

/**
 * Task service interface.
 * 
 * @author James G. Willmore
 *
 */
public interface TaskService extends ModelService<Task, TaskRepositoryImpl> {
  
  /** The Constant COLUMNS. */
  public final static String[] COLUMNS = new String[] { "activity", "start_time", "end_time", "comments" };
  
  /**
   * Get the latest start time for a new task, 
   * based upon the maximum end time of all tasks.
   *
   * @return the date
   * @throws ServiceException the service exception
   */
  public Date newTaskStartTime() throws ServiceException;
  
  /**
   * Update the end time of all open tasks to now.
   *
   * @param endTime the end time
   * @throws ServiceException the service exception
   */
  public void endOpenTasks(Date endTime) throws ServiceException;
}
