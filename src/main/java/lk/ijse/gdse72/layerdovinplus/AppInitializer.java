package lk.ijse.gdse72.layerdovinplus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/Batch.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image image = new Image(getClass().getResourceAsStream("/style/login.css"));

        stage.setTitle("ovinplus pvt ltd");
       // stage.getIcons().add(
                //new Image(getClass().getResourceAsStream("/asset/Untitled.jpeg")));

        //stage.setFullScreen(true);
     //   stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



