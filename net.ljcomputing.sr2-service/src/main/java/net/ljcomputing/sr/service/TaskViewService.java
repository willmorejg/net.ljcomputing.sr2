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
import net.ljcomputing.sr.model.TaskViewModel;
import net.ljcomputing.sr.repository.impl.TaskViewModelRepositoryImpl;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Task view service interface.
 * 
 * @author James G. Willmore
 *
 */
public interface TaskViewService extends ModelService<TaskViewModel, TaskViewModelRepositoryImpl> {
  
  /** The Constant COLUMNS. */
  public final static String[] COLUMNS = new String[] { 
      "start_time",  "end_time", "comments", 
      "activity_id", "activity_name", "activity_description", 
      "wbs_id", "wbs_name", "wbs_description"};

  /**
   * Read tasks between the given by start and end times.
   *
   * @param startTime the start time
   * @param endTime the end time
   * @return the list
   * @throws ServiceException the service exception
   */
  public List<TaskViewModel> readByTimes(
      LocalDateTime startTime, LocalDateTime endTime) throws ServiceException;
  
  /**
   * To CSV format.
   *
   * @param out the out
   * @param taskViewModels the task view models
   * @throws ServiceException the service exception
   */
  public void toCsv(FileWriter out, List<TaskViewModel> taskViewModels) throws ServiceException;
}
