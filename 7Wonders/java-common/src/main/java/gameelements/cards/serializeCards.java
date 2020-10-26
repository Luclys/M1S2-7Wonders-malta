package gameelements.cards;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;

public class serializeCards {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        TestStatsClass stats0 = new TestStatsClass();
        TestStatsClass stats1 = new TestStatsClass();
        TestStatsClass stats2 = new TestStatsClass();
        stats0.setNbViq("0");
        stats0.setNbViq1(1);
        stats0.setNbViq2(2);
        stats0.setNbViq3(3);
        stats0.setNbViq4(4);
        stats1.setNbViq("0");
        stats1.setNbViq1(1);
        stats1.setNbViq2(2);
        stats1.setNbViq3(3);
        stats1.setNbViq4(4);
        stats2.setNbViq("0");
        stats2.setNbViq1(1);
        stats2.setNbViq2(2);
        stats2.setNbViq3(3);
        stats2.setNbViq4(4);

        TestStatsClass[] tabstats = new TestStatsClass[]{stats0, stats1, stats2};
        String jsonString = mapper.writeValueAsString(tabstats);

        TestStatsClass[] newstats = mapper.readValue(jsonString, TestStatsClass[].class);
        System.out.println(Arrays.toString(newstats));
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
