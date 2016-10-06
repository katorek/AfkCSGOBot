package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Main extends Application {

    private Button start;
    private Robot robot;
    private int delay = 0;
    private boolean started = false;
    private String s1 = "Press to Start", s2 = "Press to Stop";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        start = new Button(s1);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Runnable r = this::start;
                start.setText((start.getText().equals(s1)) ? s2 : s1);
                new Thread(r).start();
            }

            private void start() {
                if (robot == null) {
                    try {
                        robot = new Robot();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
                started = !started;
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int height = gd.getDisplayMode().getHeight();
                int width = gd.getDisplayMode().getWidth();
                while (started) {
                    robot.keyPress(KeyEvent.VK_W);
                    robot.mouseMove(width * 3 / 4, height / 2);
                    robot.delay(100);
                    robot.keyRelease(KeyEvent.VK_W);
                }
//                robot.keyRelease(KeyEvent.VK_D);
//                robot.keyRelease(KeyEvent.VK_W);
                robot.delay(delay);
            }
        });

        GridPane grid = new GridPane();
        grid.add(start, 0, 0);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        stage.setTitle("AFK csgo bot");
        stage.setScene(new Scene(grid, 50, 50));
        stage.setResizable(false);
        stage.show();
    }
}
