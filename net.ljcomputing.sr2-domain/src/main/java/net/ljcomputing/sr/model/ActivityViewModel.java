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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Activity view model.
 * 
 * @author James G. Willmore
 *
 */
public class ActivityViewModel extends AbstractModel implements Model, Entity {

  /** The name. */
  private String name;
  
  /** The description. */
  private String description;
  
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
  public ActivityViewModel(EntityPopulator ep, ResultSet rs) throws PersistenceException {
    populate(ep, rs);
  }

  /**
   * @see net.ljcomputing.persistence.Entity#populate(net.ljcomputing.sr.persistence.EntityPopulator, java.sql.ResultSet)
   */
  public void populate(EntityPopulator ep, ResultSet rs) throws PersistenceException {
    ep.populate(this, rs);
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param activityDescription the new description
   */
  public void setDescription(String activityDescription) {
    this.description = activityDescription;
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
