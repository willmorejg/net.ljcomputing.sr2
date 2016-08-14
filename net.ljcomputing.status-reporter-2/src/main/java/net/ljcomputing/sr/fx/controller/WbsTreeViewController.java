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

import net.ljcomputing.fx.alert.ErrorAlert;
import net.ljcomputing.model.Model;
import net.ljcomputing.sr.fx.tree.item.WbsTreeItem;
import net.ljcomputing.sr.fx.treeview.WbsTreeView;
import net.ljcomputing.sr.model.Activity;
import net.ljcomputing.sr.model.WorkBreakdownStructure;
import net.ljcomputing.sr.service.ActivityService;
import net.ljcomputing.sr.service.SrModelServiceFactory;
import net.ljcomputing.sr.service.WorkBreakdownStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.TreeItem;

/**
 * JavaFX work breakdown structure tree view controller.
 * 
 * @author James G. Willmore
 *
 */
class WbsTreeViewController extends AbstractComponentController implements ComponentController {
  
  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(WbsTreeViewController.class);

  /** The wbs tree. */
  private WbsTreeView wbsTree;
  
  /**
   * Instantiates a new wbs tree view controller.
   *
   * @param wbsTree the wbs tree
   */
  WbsTreeViewController(WbsTreeView wbsTree) {
    this.wbsTree = wbsTree;
  }

  /**
   * @see net.ljcomputing.sr.fx.controller.ComponentController#refreshComponents()
   */
  public void refreshComponents() {
    wbsTree.setRoot(addTreeValues(getWorkBreakdownStructures()));
    wbsTree.getRoot().setExpanded(true);
  }

  /**
   * Gets the work breakdown structures.
   *
   * @return the work breakdown structures
   */
  private List<Model> getWorkBreakdownStructures() {
    List<Model> list = new LinkedList<Model>();
    
    try {
      WorkBreakdownStructureService wbsService = 
          (WorkBreakdownStructureService) SrModelServiceFactory.WorkBreakdownStructure.getServiceInstance();
      
      list.addAll(wbsService.readAll());
    } catch (Exception e) {
      new ErrorAlert().show(e);
    }

    return list;
  }

  /**
   * Adds the tree values.
   *
   * @param wbsList the wbs list
   * @return the tree item
   */
  private TreeItem<Model> addTreeValues(List<Model> wbsList) {
    WbsTreeItem root = new WbsTreeItem(new WorkBreakdownStructure("WBS List"));

    for (Model model : wbsList) {
      WbsTreeItem wbsItem = new WbsTreeItem(model);

      try {
        ActivityService activityService = (ActivityService) SrModelServiceFactory.Activity.getServiceInstance();
        
        for (Activity a : activityService.readByWbs(model.getId())) {
          WbsTreeItem activityItem = new WbsTreeItem(a);
          wbsItem.getChildren().add(activityItem);
          wbsItem.setExpanded(true);
        }
      } catch (Exception e) {
        new ErrorAlert().show(e);
      }

      root.getChildren().add(wbsItem);
      root.setExpanded(true);
    }

    return root;
  }
}
