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

import net.ljcomputing.service.ModelService;
import net.ljcomputing.sr.model.WorkBreakdownStructure;
import net.ljcomputing.sr.repository.impl.WorkBreakdownStructureRepositoryImpl;

/**
 * Work breakdown structure service interface.
 * 
 * @author James G. Willmore
 */
public interface WorkBreakdownStructureService extends
    ModelService<WorkBreakdownStructure, WorkBreakdownStructureRepositoryImpl> {
  
  /** The Constant COLUMNS. */
  public final static String[] COLUMNS = new String[]{"name", "description"};
}
