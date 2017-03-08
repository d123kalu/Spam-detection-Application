package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;

import java.io.File;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dikachi on 04/03/17.
 */



public class DataSource {
    private Map<String, Integer> wordCounts;
    private Map<String, Integer> freqspam;
    private Map<String, Double> spamProb;

    static int numham = 0;
    static int numspam = 0;

    public DataSource()
    {
        wordCounts = new TreeMap<>();
        freqspam = new TreeMap<>();
        spamProb = new TreeMap<>();

    }
    @FXML

    public static ObservableList<TestFile> getAllMarks() {
        ObservableList<TestFile> marks = FXCollections.observableArrayList();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(null);
        WordCounter wordCounter = new WordCounter();
        try {
            if (mainDirectory.exists()) {
                wordCounter.processFile(mainDirectory);
                wordCounter.printWordCounts(1, new File("./trainHamFreq"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        DirectoryChooser d = new DirectoryChooser();
        d.setInitialDirectory(new File("."));
        File main = directoryChooser.showDialog(null);
        //WordCounter word = new WordCounter();
        try {
            if (main.exists()) {
                wordCounter.process(main);
                wordCounter.printWordCounts(1, new File("./trainSpamFreq"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        DirectoryChooser c = new DirectoryChooser();
        c.setInitialDirectory(new File("."));
        File m = c.showDialog(null);
        //WordCounter w = new WordCounter();
        if (m.isDirectory())
        {
            try {

                File[] filesInDir = m.listFiles();
                int num = filesInDir.length;
                wordCounter.numofguesses(num);
                for (int i = 0; i < filesInDir.length; i++) {
                    String name, file;

                    wordCounter.calcProb();
                    double prob = wordCounter.finalprob(filesInDir[i]);
                    name = filesInDir[i].getName();
                    file = m.getName();

                    marks.add(new TestFile(name, prob, file));
                    wordCounter.math(prob,file);

                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return marks;


    }

}
