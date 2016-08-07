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
 * Activity domain.
 * 
 * @author James G. Willmore
 *
 */
public class Activity extends AbstractModel implements Model, Entity {
  
  /** The name. */
  private String name;
  
  /** The description. */
  private String description;
  
  /** The associated work breakdown structure. */
  private Integer wbs;
  
  /**
   * Instantiates a new activity.
   *
   * @param ep the ep
   * @param rs the rs
   * @throws PersistenceException the persistence exception
   */
  public Activity(EntityPopulator ep, ResultSet rs) throws PersistenceException {
    populate(ep, rs);
  }
  
  /**
   * Instantiates a new work breakdown structure.
   *
   * @param name the name
   * @param wbs the wbs
   */
  public Activity(String name, Integer wbs) {
    this(name, null, wbs);
  }
  
  /**
   * Instantiates a new work breakdown structure.
   *
   * @param name the name
   * @param description the description
   * @param wbs the wbs
   */
  public Activity(String name, String description, Integer wbs) {
    this.name = name;
    this.description = description;
    this.wbs = wbs;
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
   * @param description the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the associated work breakdown structure.
   *
   * @return the wbs
   */
  public Integer getWbs() {
    return wbs;
  }

  /**
   * Associate the activity with a new work breakdown structure.
   *
   * @param wbs the wbs
   */
  public void newWbs(Integer wbs) {
    this.wbs = wbs;
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
