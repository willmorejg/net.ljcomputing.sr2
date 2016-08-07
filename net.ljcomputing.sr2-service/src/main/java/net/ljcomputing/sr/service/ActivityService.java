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
import net.ljcomputing.sr.model.Activity;
import net.ljcomputing.sr.repository.impl.ActivityRepositoryImpl;

import java.util.List;

/**
 * Activity service interface.
 * 
 * @author James G. Willmore
 *
 */
public interface ActivityService
    extends ModelService<Activity, ActivityRepositoryImpl> {
  
  /** The Constant COLUMNS. */
  public final static String[] COLUMNS = new String[] { "name", "description", "wbs" };
  
  /**
   * Read by wbs.
   *
   * @param wbs the wbs
   * @return the list
   * @throws ServiceException the service exception
   */
  List<Activity> readByWbs(Integer wbs) throws ServiceException;
}
