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
 * Work breakdown structure domain.
 * 
 * @author James G. Willmore
 *
 */
public class WorkBreakdownStructure extends AbstractModel implements Model, Entity {

  /** The name. */
  private transient String name;

  /** The description. */
  private String description;
  
  /**
   * Instantiates a new work breakdown structure.
   */
  public WorkBreakdownStructure() {
    super();
  }

  /**
   * Instantiates a new work breakdown structure.
   *
   * @param name the name
   */
  public WorkBreakdownStructure(final String name) {
    this(name, null);
  }

  /**
   * Instantiates a new work breakdown structure.
   *
   * @param name the name
   * @param description the description
   */
  public WorkBreakdownStructure(final String name, final String description) {
    super();
    this.name = name;
    this.description = description;
  }

  /**
   * @see net.ljcomputing.persistence.Entity#populate(net.ljcomputing.persistence.EntityPopulator, java.sql.ResultSet)
   */
  public void populate(final EntityPopulator entityPopulator, final ResultSet resultSet) throws PersistenceException {
    entityPopulator.populate(this, resultSet);
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
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
