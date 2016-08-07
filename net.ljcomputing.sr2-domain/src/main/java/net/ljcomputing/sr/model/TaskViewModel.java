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


package net.ljcomputing.sr.model;

import net.ljcomputing.exception.PersistenceException;
import net.ljcomputing.model.AbstractModel;
import net.ljcomputing.model.Model;
import net.ljcomputing.persistence.Entity;
import net.ljcomputing.persistence.EntityPopulator;

import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Task view model.
 * 
 * @author James G. Willmore
 *
 */
public class TaskViewModel extends AbstractModel implements Model, Entity {
  
  /** The start time. */
  private Date startTime;
  
  /** The end time. */
  private Date endTime;
  
  /** The comments. */
  private String comments;
  
  /** The activity id. */
  private Integer activityId;

  /** The activity name. */
  private String activityName;
  
  /** The activity description. */
  private String activityDescription;
  
  /** The wbs id. */
  private Integer wbsId;

  /** The wbs name. */
  private String wbsName;

  /** The wbs description. */
  private String wbsDescription;
  
  /**
   * Instantiates a new task view model.
   *
   * @param ep the ep
   * @param rs the rs
   * @throws PersistenceException the persistence exception
   */
  public TaskViewModel(EntityPopulator ep, ResultSet rs) throws PersistenceException {
    populate(ep, rs);
  }

  /**
   * @see net.ljcomputing.persistence.Entity#populate(net.ljcomputing.sr.persistence.EntityPopulator, java.sql.ResultSet)
   */
  public void populate(EntityPopulator ep, ResultSet rs) throws PersistenceException {
    ep.populate(this, rs);
  }
  
  /**
   * Gets the start time.
   *
   * @return the start time
   */
  public Date getStartTime() {
    return startTime;
  }

  /**
   * Sets the start time.
   *
   * @param startTime the new start time
   */
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /**
   * Gets the end time.
   *
   * @return the end time
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * Sets the end time.
   *
   * @param endTime the new end time
   */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  /**
   * Gets the comments.
   *
   * @return the comments
   */
  public String getComments() {
    return comments;
  }

  /**
   * Sets the comments.
   *
   * @param comments the new comments
   */
  public void setComments(String comments) {
    this.comments = comments;
  }

  /**
   * Gets the activity id.
   *
   * @return the activity id
   */
  public Integer getActivityId() {
    return activityId;
  }

  /**
   * Sets the activity id.
   *
   * @param activityId the new activity id
   */
  public void setActivityId(Integer activityId) {
    this.activityId = activityId;
  }

  /**
   * Gets the activity name.
   *
   * @return the activity name
   */
  public String getActivityName() {
    return activityName;
  }

  /**
   * Sets the activity name.
   *
   * @param activityName the new activity name
   */
  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  /**
   * Gets the activity description.
   *
   * @return the activity description
   */
  public String getActivityDescription() {
    return activityDescription;
  }

  /**
   * Sets the activity description.
   *
   * @param activityDescription the new activity description
   */
  public void setActivityDescription(String activityDescription) {
    this.activityDescription = activityDescription;
  }

  /**
   * Gets the wbs id.
   *
   * @return the wbs id
   */
  public Integer getWbsId() {
    return wbsId;
  }

  /**
   * Sets the wbs id.
   *
   * @param wbsId the new wbs id
   */
  public void setWbsId(Integer wbsId) {
    this.wbsId = wbsId;
  }

  /**
   * Gets the wbs name.
   *
   * @return the wbs name
   */
  public String getWbsName() {
    return wbsName;
  }

  /**
   * Sets the wbs name.
   *
   * @param wbsName the new wbs name
   */
  public void setWbsName(String wbsName) {
    this.wbsName = wbsName;
  }

  /**
   * Gets the wbs description.
   *
   * @return the wbs description
   */
  public String getWbsDescription() {
    return wbsDescription;
  }

  /**
   * Sets the wbs description.
   *
   * @param wbsDescription the new wbs description
   */
  public void setWbsDescription(String wbsDescription) {
    this.wbsDescription = wbsDescription;
  }
  
  /**
   * Gets the task.
   *
   * @return the task
   */
  public Task getTask() {
    Task task = new Task(getActivityId());
    
    task.setId(getId());
    task.setStartTime(getStartTime());
    task.setEndTime(getEndTime());
    task.setComments(getComments());
    
    return task;
  }

  /**
   * To string.
   *
   * @return the string
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}