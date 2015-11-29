package control;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panasyuk on 22.11.2015.
 */
public class History {
    private File historyFile;
    private String fileName;
    private FileOutputStream fileOutputStream;

    public History()  {
//
    }

    public void addHistory(List history) throws Exception {
        historyFile = new File("historyGame.txt");
        if (historyFile.exists()) {
            historyFile.delete();
        }
        historyFile.createNewFile();

        BufferedWriter bw = new BufferedWriter(new FileWriter("historyGame.txt", true));
        String str;
        for (int i = 0; i < history.size(); i++) {
            str = (String) history.get(i);
            bw.write(str + '\n');
        }
        bw.close();
        repeatGame();
    }

    public List repeatGame() throws Exception {
        String step;
        List<String> history = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("historyGame.txt"));
        while ((step = br.readLine()) != null) {
            history.add(step);
        }
//        for (int i = 0; i < history.size(); i++){
//            System.out.println(history.get(i));
//        }
        return history;
    }
}
