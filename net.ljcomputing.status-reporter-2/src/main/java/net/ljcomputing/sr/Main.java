package net.ljcomputing.sr;

import net.ljcomputing.fx.alert.ErrorAlert;
import net.ljcomputing.sr.configuration.StatusReporterConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.DoubleConsumer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
           Copyright 2015-2016, James G. Willmore

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

/**
 * Status Reporter main application class.
 * 
 * @author James G. Willmore
 *
 */
public class Main extends Application {

  /** The logger. */
  private final Logger logger = LoggerFactory.getLogger(Main.class);

  /**
   * @see javafx.application.Application#start(javafx.stage.Stage)
   */
  @Override
  public void start(Stage primaryStage) throws IOException {
    logger.debug("starting application");
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> Platform.runLater(() -> showErrorDialog(t, e)));
    Thread.currentThread().setUncaughtExceptionHandler(this::showErrorDialog);
    
    setUserAgentStylesheet(STYLESHEET_MODENA);
    Parent root = (Parent) FXMLLoader.load(getClass().getResource(StatusReporterConfig.MAIN_FXML));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle(StatusReporterConfig.APP_NAME);
    primaryStage.setOnCloseRequest(e -> {
      logger.debug("exiting application");
      Platform.exit();
      System.exit(0);
    });

    setScreen(primaryStage);

    primaryStage.show();

    logger.debug("primary stage showing");
  }

  // TODO - refactor
  private void setScreen(Stage stage) {
    ObservableList<Screen> screens = Screen.getScreens();
    Screen screen = null;

    for (Screen s : screens) {
      screen = s;
    }

    Rectangle2D sbounds = screen.getVisualBounds();

    double sw = sbounds.getMaxY();
    double sh = sbounds.getMaxX();

    listenToSizeInitialization(stage.widthProperty(), w -> stage.setX((sw - w) * 0.25));
    listenToSizeInitialization(stage.heightProperty(), h -> stage.setY((sh - h) * 0.75));
  }

  private void listenToSizeInitialization(ObservableDoubleValue size, DoubleConsumer handler) {

    ChangeListener<Number> listener = new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> obs, Number oldSize, Number newSize) {
        logger.debug("{}", newSize);
        if (newSize.doubleValue() != Double.NaN) {
          handler.accept(newSize.doubleValue());
          size.removeListener(this);
        }
      }
    };

    size.addListener(listener);
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String... args) {
    Application.launch(Main.class, args);
  }

  /**
   * Show error dialog.
   *
   * @param thread the thread @param exception the exception
   */
  private void showErrorDialog(Thread thread, Throwable exception) {
    new ErrorAlert().show((Exception) exception);
  }
}
