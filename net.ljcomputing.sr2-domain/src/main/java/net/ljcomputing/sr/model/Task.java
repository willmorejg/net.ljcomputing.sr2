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
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A time-based task associated with an activity.
 * 
 * @author James G. Willmore
 *
 */
public class Task extends AbstractModel implements Model, Entity {
  private static final DecimalFormat df = new DecimalFormat("#.##");
  
  /** The associated activity. */
  private Integer activity;
  
  /** The start time. */
  private Date startTime = new Date();
  
  /** The end time. */
  private Date endTime;
  
  /** The elapsed time. */
  private long elapsedTime;
  
  /** The elapsed time in hours. */
  private double elapsedHours;
  
  /** The comments. */
  private String comments;
  
  /**
   * Instantiates a new task.
   *
   * @param ep the ep
   * @param rs the rs
   * @throws PersistenceException the persistence exception
   */
  public Task(EntityPopulator ep, ResultSet rs) throws PersistenceException {
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
   * Instantiates a new task.
   *
   * @param activity the activity
   */
  public Task(Integer activity) {
    this.activity = activity;
  }

  /**
   * Gets the activity.
   *
   * @return the activity
   */
  public Integer getActivity() {
    return activity;
  }

  /**
   * Associate task with a new activity.
   *
   * @param activity the activity
   */
  public void newActivity(Integer activity) {
    this.activity = activity;
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
    calculateElapsedTime();
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
    return df.format(getElapsedHours());
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
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
