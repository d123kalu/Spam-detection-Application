
package sample;

        import javafx.application.Application;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.geometry.Insets;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TextField;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.layout.BorderPane;
        import javafx.scene.layout.GridPane;
        import javafx.stage.Stage;
        import javafx.scene.control.ListView;
        import javafx.scene.control.Button;
        import javafx.stage.FileChooser;
        import java.io.File;
        import javafx.stage.FileChooser.ExtensionFilter;
        import java.util.List;
        import javafx.scene.layout.Pane;

public class Main extends Application {
    private BorderPane layout;
    //private TableView <TestFile> table;
    private Button btn2;
    private ListView listview;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(new Scene(root, 700, 425));

        /* Create the Table (For the Center of the User Interface) */
        TableView table = new TableView<>();
        //table.setItems();
        table.setItems(DataSource.getAllMarks());
        table.setEditable(true);

        /* Create the Table's Columns */
        TableColumn <TestFile, String> fileColumn = null;
        fileColumn = new TableColumn<>("File");
        fileColumn.setMinWidth(300);
        fileColumn.setCellValueFactory(new PropertyValueFactory<TestFile, String>("Filename"));

        TableColumn <TestFile, String> actualClassColumn = null;
        actualClassColumn = new TableColumn<>("Actual Class");
        actualClassColumn.setMinWidth(200);
        actualClassColumn.setCellValueFactory(new PropertyValueFactory<TestFile, String>("ActualClass"));

        TableColumn <TestFile, Double> spamProbabilityColumn = null;
        spamProbabilityColumn = new TableColumn<>("Spam Probability");
        spamProbabilityColumn.setMinWidth(200);
        spamProbabilityColumn.setCellValueFactory(new PropertyValueFactory<TestFile, Double>("SpamProbability"));

        /* Adding Columns to the Table */
        table.getColumns().add(fileColumn);
        table.getColumns().add(actualClassColumn);
        table.getColumns().add(spamProbabilityColumn);

        /* Creating an Edit Area (for the Bottom of the User Interface) */
        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(10, 10, 10, 10));
        editArea.setVgap(10);
        editArea.setHgap(10);

        Label accuracyLabel = new Label("Accuracy");
        editArea.add(accuracyLabel, 0, 0);
        TextField accuracyField = new TextField();
        accuracyField.setText(String.valueOf(WordCounter.calcaccuracy()));
        editArea.add(accuracyField, 1, 0);
        accuracyField.setEditable(false);

        Label precisionLabel = new Label ("Precision");
        editArea.add(precisionLabel, 0, 1);
        TextField precisionField = new TextField();
        precisionField.setText(String.valueOf(WordCounter.calpres()));
        editArea.add(precisionField, 1, 1);
        precisionField.setEditable(false);

/*
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("\\home\\dikachi\\Desktop\\data\\"));
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);
        if(selectedFiles != null) {
            for (int i = 0; i < selectedFiles.size(); i++) {
                listview.getItems().add(selectedFiles.get(i).getAbsolutePath());

            }
        }
            else
            {
                System.out.println("file is not valid");
            }
        */


        /* Arranging All Components in the User Interface */
        BorderPane layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(editArea);

        Scene scene = new Scene (layout, 700, 425);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

