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
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Task view model.
 * 
 * @author James G. Willmore
 *
 */
public class TaskViewModel extends AbstractModel implements Comparable<TaskViewModel>, Model, Entity {
  
  /** The decimal format. */
  public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.0##");
  
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
  
  /** The elapsed time. */
  private long elapsedTime;
  
  /** The elapsed time in hours. */
  private double elapsedHours;
  
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
    calculateElapsedTime();
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
    calculateElapsedTime();
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
    calculateElapsedTime();
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
   * Gets the record key.
   *
   * @return the record key
   */
  public String getRecordKey() {
    return getWbsName() + "," + getActivityName();
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
   * Calculate elapsed time.
   *
   * @return the long
   */
  private long calculateElapsedTime() {
    elapsedTime = 0;
    
    if(null != getEndTime() && null != getStartTime()) {
      Instant end = getEndTime().toInstant();
      Instant start = getStartTime().toInstant();
      elapsedTime = ChronoUnit.MINUTES.between(start, end);
      elapsedHours = (double) (elapsedTime / 60d);
    }
    
    return elapsedTime;
  }

  /**
   * Gets the elapsed time.
   *
   * @return the elapsed time
   */
  public long getElapsedTime() {
    return calculateElapsedTime();
  }
  
  /**
   * Gets the elapsed hours.
   *
   * @return the elapsed hours
   */
  public double getElapsedHours() {
    if(elapsedHours <= 0) {
      calculateElapsedTime();
    }
    
    return elapsedHours;
  }
  
  /**
   * Get formated elapsed hours as a String.
   *
   * @return the string
   */
  public String getFormatedElapsedHours() {
    return DECIMAL_FORMAT.format(getElapsedHours());
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  /**
   * To values list.
   *
   * @return the list
   */
  public List<String> toValuesList() {
    List<String> values = new ArrayList<String>();
    
    values.add(wbsName);
    values.add(activityName);
    values.add(getFormatedElapsedHours());
    values.add(comments);
    
    return values;
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(TaskViewModel o) {
    int finalOrder = 0;
    
    finalOrder = compareStrings(wbsName, o.getWbsName());
      
    if(finalOrder == 0) {
      finalOrder = compareStrings(activityName, o.getActivityName());
    }
      
    if(finalOrder == 0) {
      finalOrder = Double.valueOf(elapsedHours).compareTo(Double.valueOf(o.getElapsedHours()));
    }

    return finalOrder;
  }

  //TODO - move to utility class
  /**
   * Compare two Strings.
   *
   * @param left the left
   * @param right the right
   * @return the int
   */
  private int compareStrings(String left, String right) {
    int finalOrder = 0;
    
    if(null == left && null != right) {
      finalOrder = -1;
    } else if(null != left && null == right) {
      finalOrder = 1;
    } else {
      finalOrder = left.compareToIgnoreCase(right);
    }
    
    return finalOrder;
  }
}
