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

package net.ljcomputing.sr.configuration;

import net.ljcomputing.persistence.DataSourceTable;
import net.ljcomputing.persistence.impl.ConnectionPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Status Report persistence validator.
 * 
 * @author James G. Willmore
 *
 */
public final class PersistenceValidator {
  
  /** The logger. */
  private final static Logger LOGGER = LoggerFactory.getLogger(PersistenceValidator.class);
  
  /**
   * Initialize the database tables.
   */
  public static boolean initialize() {
    boolean initialized = true;
    
    try {
      LOGGER.debug(" ... initialize tables");
      ConnectionPool cp = ConnectionPool.getInstance();
      initTables(cp.getConnection());
      LOGGER.debug("COMPLETED ... initialize tables");
    } catch (Exception exception) {
      LOGGER.error("Cannot initialize database tables: ", exception);
      initialized = false;
    }
    
    return initialized;
  }

  /**
   * Inits the tables.
   *
   * @param conn the conn
   * @throws Exception the exception
   */
  private static void initTables(Connection conn) throws Exception {
    for (DataSourceTable table : Tables.values()) {
      if (!exists(conn, table)) {
        LOGGER.debug(" ... table " + table.getTableName() + " does not exist ... creating");
        createTable(conn, table);
        LOGGER.debug("CREATED ... " + table.getTableName());
      }
    }

    LOGGER.debug(" ... adding items");
    addItems(conn);
    LOGGER.debug("COMPLETED ... adding items");
  }

  /**
   * Exists.
   *
   * @param conn the conn
   * @param table the table
   * @return true, if successful
   * @throws Exception the exception
   */
  private static boolean exists(Connection conn, DataSourceTable table)
      throws Exception {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt
          .executeQuery("select * from " + table.getTableName() + " where 1=0");

      rs.close();
      stmt.close();
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  /**
   * Creates the table.
   *
   * @param conn the conn
   * @param table the table
   * @throws Exception the exception
   */
  private static void createTable(Connection conn, DataSourceTable table)
      throws Exception {
    Statement stmt = conn.createStatement();
    stmt.executeUpdate(table.getDDl());
    stmt.close();
  }

  /**
   * Adds the items.
   *
   * @param conn the conn
   * @throws Exception the exception
   */
  private static void addItems(Connection conn) throws Exception {
    int count = 0;
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select count(*) as cnt from wbs");

    while (rs.next()) {
      count = rs.getInt("cnt");
    }

    rs.close();
    stmt.close();

    if (count <= 0) {
      stmt = conn.createStatement();

      for (int i = 0; i < 2; i++) {
        stmt.executeUpdate(
            "insert into wbs(name) values('" + "Sample WBS " + i + "')");
        for (int a = 0; a < 2; a++) {
          //need to add 1, so the proper WBS ID is inserted
          stmt.executeUpdate("insert into activity(name, wbs) values('"
              + "Sample Activity " + a + "', " + (i+1) + ")");
          
          stmt.executeUpdate("insert into task(start_time, end_time, comments, activity) "
              + "values(current_timestamp, current_timestamp, 'new task', " + (a+1) + ")");
        }
      }

      stmt.close();
    }
  }
}
