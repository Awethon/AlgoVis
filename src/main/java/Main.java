import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/rootView.fxml"));
        primaryStage.setTitle("AlgoVis [Merge Sort]");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxWidth(1200);
        primaryStage.setMaxHeight(800);
        primaryStage.show();
        //ll
    }

    public static void main(String[] args) {
        launch(args);
    }
}
