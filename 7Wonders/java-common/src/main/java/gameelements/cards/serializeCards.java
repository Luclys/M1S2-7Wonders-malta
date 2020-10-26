package gameelements.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import gameelements.DetailedResults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class serializeCards {

    public static void main(String[] args) throws IOException {

        DetailedResults stats0 = new DetailedResults();
        DetailedResults stats1 = new DetailedResults();
        DetailedResults stats2 = new DetailedResults();

        List<DetailedResults> list = new ArrayList<>();
        list.add(stats0);
        list.add(stats1);
        list.add(stats2);

        DetailedResults[] tabstats = new DetailedResults[]{stats0, stats1, stats2};
        Object[] tabstatsfromAL = list.toArray();


        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(tabstats);
        String jsonString2 = mapper.writeValueAsString(tabstatsfromAL);

        //DetailedResults[] newstats = mapper.readValue(jsonString, DetailedResults[].class);
        DetailedResults[] newstats2 = mapper.readValue(jsonString2, DetailedResults[].class);
        //System.out.println(Arrays.toString(newstats));
        System.out.println(Arrays.toString(newstats2));
    }

    static class TestStatsClass {
        String nbViq;
        int nbViq1;
        int nbViq2;
        int nbViq3;
        int nbViq4;

        TestStatsClass() {
            nbViq = "0";
            nbViq1 = 0;
            nbViq2 = 0;
            nbViq3 = 0;
            nbViq4 = 0;
        }

        public String getNbViq() {
            return nbViq;
        }

        public void setNbViq(String nbViq) {
            this.nbViq = nbViq;
        }

        public int getNbViq1() {
            return nbViq1;
        }

        public void setNbViq1(int nbViq1) {
            this.nbViq1 = nbViq1;
        }

        public int getNbViq2() {
            return nbViq2;
        }

        public void setNbViq2(int nbViq2) {
            this.nbViq2 = nbViq2;
        }

        public int getNbViq3() {
            return nbViq3;
        }

        public void setNbViq3(int nbViq3) {
            this.nbViq3 = nbViq3;
        }

        public int getNbViq4() {
            return nbViq4;
        }

        public void setNbViq4(int nbViq4) {
            this.nbViq4 = nbViq4;
        }
    }
}
