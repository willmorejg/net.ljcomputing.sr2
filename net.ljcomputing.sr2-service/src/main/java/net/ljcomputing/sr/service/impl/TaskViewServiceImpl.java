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

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

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

  /**
   * @see net.ljcomputing.sr.service.TaskViewService#toCsv(java.lang.Appendable, java.util.List)
   */
  public void toCsv(Appendable out, final List<TaskViewModel> taskViewModels)
      throws ServiceException {
    try {
      CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL)
          .withRecordSeparator(System.getProperty("line.separator"));
      //TODO - refactor, so annotation is no longer needed
      @SuppressWarnings("resource")
      CSVPrinter printer = new CSVPrinter(out, format);

      printer.printRecord((Object[])TaskViewModel.CVS_RECORD_HEADER);

      List<TaskViewModel> list = new LinkedList<TaskViewModel>();
      list.addAll(taskViewModels);
      Collections.sort(list);
      
      for (TaskViewModel taskItem : taskViewModels) {
        printer.printRecord(taskItem.toValuesList());
      }

      //TODO - when write to file is implemented
      //printer.close();
    } catch (IOException exception) {
      throw new ServiceException(exception);
    }
  }
}
