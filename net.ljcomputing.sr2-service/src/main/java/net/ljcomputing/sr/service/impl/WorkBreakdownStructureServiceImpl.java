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

import net.ljcomputing.exception.ServiceException;
import net.ljcomputing.service.impl.AbstractService;
import net.ljcomputing.sr.model.WorkBreakdownStructure;
import net.ljcomputing.sr.repository.impl.WorkBreakdownStructureRepositoryImpl;
import net.ljcomputing.sr.service.WorkBreakdownStructureService;

/**
 * Work breakdown structure service implementation.
 * 
 * @author James G. Willmore
 */
public class WorkBreakdownStructureServiceImpl extends
    AbstractService<WorkBreakdownStructure, WorkBreakdownStructureRepositoryImpl>
    implements WorkBreakdownStructureService {
  
  /**
   * Instantiates a new work breakdown structure service impl.
   *
   * @throws ServiceException the service exception
   */
  public WorkBreakdownStructureServiceImpl() throws ServiceException {
    super();
  }
  
  /**
   * @see net.ljcomputing.service.impl.AbstractService#getColumns()
   */
  public String[] getColumns() {
    return COLUMNS;
  }
}
