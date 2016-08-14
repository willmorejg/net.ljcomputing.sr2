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
 * A time-based task associated with an activity.
 * 
 * @author James G. Willmore
 *
 */
public class Task extends AbstractModel implements Model, Entity {
  /** The associated activity. */
  private transient Integer activity;

  /** The start time. */
  private Date startTime = new Date();

  /** The end time. */
  private Date endTime;

  /** The comments. */
  private String comments;

  /**
   * Instantiates a new task.
   */
  public Task() {
    super();
  }

  /**
   * Instantiates a new task.
   *
   * @param activity the activity
   */
  public Task(final Integer activity) {
    super();
    this.activity = activity;
  }

  /**
   * @see net.ljcomputing.persistence.Entity#populate(net.ljcomputing.persistence.EntityPopulator, java.sql.ResultSet)
   */
  public void populate(final EntityPopulator entityPopulator, final ResultSet resultSet) throws PersistenceException {
    entityPopulator.populate(this, resultSet);
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
  public void newActivity(final Integer activity) {
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
  public void setStartTime(final Date startTime) {
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
  public void setEndTime(final Date endTime) {
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
  public void setComments(final String comments) {
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
