package sample;

import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WordCounter {
    private Map<String, Integer> wordCounts;
    private Map<String, Integer> freqspam;
    private Map<String, Double> spamProb;
     int numham;
     int numspam;
     static double truepositive = 0.0;
     static double truenegative = 0.0;
     static double falsepositives = 0.0;
     static int num;


    public WordCounter() {
        wordCounts = new TreeMap<>();
        freqspam = new TreeMap<>();
        spamProb = new TreeMap<>();
    }

    public void numofguesses(int n)
    {
        n = num;
    }
    public void math(double prob, String file)
    {
        if (prob == 1.0)
        {
            if(file == "spam")
            {
                truepositive++;
            }
        }

        if (prob == 0.0)
        {
            if(file == "ham")
            {
                truepositive++;
            }
        }
        else
        {falsepositives++;}
    }

    public static double calcaccuracy()
    {
        double acc = (truepositive + truenegative)/num;
        return acc;
    }

    public static double calpres()
    {
        double pres = truepositive/(falsepositives+truepositive);
        return pres;
    }

    public void process (File file) throws IOException {
        if (file.isDirectory()) {
            File[] filesInDir = file.listFiles();
            for (int i = 0; i < filesInDir.length; i++) {
                processFile(filesInDir[i]);
            }
        } else {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    //if(n == 1) {
                    //    countWord(word);
                    //}
                    //else
                    cWord(word);
                }
            }
        }

    }
    public void processFile (File file) throws IOException {
        if (file.isDirectory()) {
            File[] filesInDir = file.listFiles();
            for (int i = 0; i < filesInDir.length; i++) {
                processFile(filesInDir[i]);
            }
        } else {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    countWord(word);
                }
            }
        }
    }

    public double finalprob (File file) throws IOException
    {
        double sum = 0.0;
        if (file.isDirectory()) {
            File[] filesInDir = file.listFiles();
            for (int i = 0; i < filesInDir.length; i++) {
                numham = filesInDir.length;
                finalprob(filesInDir[i]);
            }
        } else {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    sum += prob(word);
                }
            }
        }

        double n = end(sum);
        return n;
    }

    public double prob(String w)
    {
        double N;
        if (spamProb.containsKey(w)==true)
        {
            double PSW = spamProb.get(w).intValue();

            N = (Math.log(1-PSW)) - (Math.log(PSW));
        } else
        {
            return 0;
        }
        return N;
    }

    public double end (double sum)
    {
        double PSF;
        PSF = 1/(1+Math.pow(Math.E,sum));
        return PSF;
    }


    private void countWord (String word) {
        if (wordCounts.containsKey(word)) {
            int oldCount = wordCounts.get(word);
            wordCounts.put(word, oldCount + 1);
        } else {
            wordCounts.put(word, 1);
        }
    }

    private void cWord (String word) {
        if (freqspam.containsKey(word)) {
            int oldCount = freqspam.get(word);
            freqspam.put(word, oldCount + 1);
        } else {
            freqspam.put(word, 1);
        }
    }

    private boolean isWord (String token) {
        String patten = "^[a-zA-Z]*$";
        if (token.matches(patten)) {
            return true;
        } else {
            return false;
        }
    }

    public void calcProb()
    {
        for (int i = 0;i< freqspam.size();i++)
        {
            //wordCounts.get(i).intValue();
            int NUMCW = freqspam.get(i).intValue();
            double PrWS =  NUMCW/ numspam;
            int NUMCW2;
            String word = freqspam.get(i).toString();
            if(wordCounts.containsKey(word))
            {
                NUMCW2 = wordCounts.get(word).intValue();
            }
            else{ NUMCW2 = 0;}

            double PrWH = NUMCW2/numham;

            double PSW = PrWS / (PrWS + PrWH);
            spamProb.put(word,PSW);
        }

    }

    public void printWordCounts (int minCount, File outFile) throws IOException {
        if (!outFile.exists() || outFile.canWrite()) {
            PrintWriter fout = new PrintWriter(outFile);
            Set<String> keys = wordCounts.keySet();
            Iterator<String> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                int count = wordCounts.get(key);
                if (count >= minCount) {
                    fout.println("'" + key + "' -> '" + count + "'");
                }
            }
            fout.close();
        }
    }

    public static void main (String []args) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(null);
        WordCounter wordCounter = new WordCounter();
        try {
            if (mainDirectory.exists()) {
                wordCounter.processFile(mainDirectory);
                wordCounter.printWordCounts(1, new File("./Documents"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}