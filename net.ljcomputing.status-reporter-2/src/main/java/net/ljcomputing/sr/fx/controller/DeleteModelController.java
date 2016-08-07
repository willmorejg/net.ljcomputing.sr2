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


package net.ljcomputing.sr.fx.controller;

import net.ljcomputing.model.Model;
import net.ljcomputing.sr.service.SrModelServiceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author James G. Willmore
 *
 */
public class DeleteModelController {
  
  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(DeleteModelController.class);

  /**
   * Instantiates a new delete model controller.
   */
  public DeleteModelController() {
    super();
  }
  
  /**
   * Delete an item.
   *
   * @param item the item
   * @throws Exception the exception
   */
  @SuppressWarnings("unchecked")
  public void delete(Model item) throws Exception {
    String itemClass = item.getClass().getSimpleName();
    SrModelServiceFactory service = SrModelServiceFactory.valueOf(itemClass);
    service.getServiceInstance().delete(item);
  }
}
