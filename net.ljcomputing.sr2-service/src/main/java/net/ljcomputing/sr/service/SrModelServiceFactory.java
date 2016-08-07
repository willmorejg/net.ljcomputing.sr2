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
import net.ljcomputing.service.ModelServiceFactory;
import net.ljcomputing.sr.service.impl.ActivityServiceImpl;
import net.ljcomputing.sr.service.impl.ActivityViewServiceImpl;
import net.ljcomputing.sr.service.impl.TaskServiceImpl;
import net.ljcomputing.sr.service.impl.TaskViewServiceImpl;
import net.ljcomputing.sr.service.impl.WorkBreakdownStructureServiceImpl;

/**
 * Model service factory.
 * 
 * @author James G. Willmore
 *
 */
public enum SrModelServiceFactory implements ModelServiceFactory {
    WorkBreakdownStructure(WorkBreakdownStructureServiceImpl.class), 
    Activity(ActivityServiceImpl.class),
    Task(TaskServiceImpl.class),
    TaskView(TaskViewServiceImpl.class),
    ActivityView(ActivityViewServiceImpl.class);
    
    /** The service class. */
    @SuppressWarnings("rawtypes")
    private Class<? extends ModelService> serviceClass;
    
    /**
     * Instantiates a new model service factory.
     *
     * @param serviceClass the service class
     */
    @SuppressWarnings("rawtypes")
    private SrModelServiceFactory(Class<? extends ModelService> serviceClass) {
      this.serviceClass = serviceClass;
    }
    
    /**
     * Gets the service instance.
     *
     * @return the service instance
     * @throws ServiceException the service exception
     */
    @SuppressWarnings("rawtypes")
    public ModelService getServiceInstance() throws ServiceException {
      try {
        return serviceClass.newInstance();
      } catch (Exception exception) {
        throw new ServiceException(exception);
      }
    }
}
