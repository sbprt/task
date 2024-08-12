package ru.sbt.bit.ood.srp.sample1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TeamChooser {

    public static void main(String[] args) {
        new TeamChooser().formTeam("team-candidates.csv", 5);
    }

    public void formTeam(String filename, int teamSize) {
        try {
            Reader in = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename));
            ArrayList<CSVRecord> players = new ArrayList<>();
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                players.add(record);
            }
            Collections.sort(players, new Comparator<CSVRecord>() {
                @Override
                public int compare(CSVRecord p1, CSVRecord p2) {
                    // compare speed with weight 0.5 and accuracy with weight 0.5
                    return (int) -(Double.parseDouble(p1.get(2)) * 0.5 + Double.parseDouble(p1.get(3)) * 0.5 -
                            (Double.parseDouble(p2.get(2))*0.5 + Double.parseDouble(p2.get(3))*0.5));
                }
            });
            try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("team.csv"));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            ) {
                for (int i = 0; i < teamSize;i++) {
                    csvPrinter.printRecord(players.get(i).get(0), players.get(i).get(1));
                }
                csvPrinter.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
