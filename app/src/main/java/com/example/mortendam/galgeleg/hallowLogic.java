package com.example.mortendam.galgeleg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by MortenDam on 16-09-2015.
 */
public class hallowLogic {

        private ArrayList<String> muligeOrd = new ArrayList();
        private String ordet;
        private ArrayList<String> brugteBogstaver = new ArrayList();
        private String synligtOrd;
        private int antalForkerteBogstaver;
        private boolean sidsteBogstavVarKorrekt;
        private boolean spilletErVundet;
        private boolean spilletErTabt;

        public ArrayList<String> getBrugteBogstaver() {
            return this.brugteBogstaver;
        }

        public String getSynligtOrd() {
            return this.synligtOrd;
        }

        public String getOrdet() {
            return this.ordet;
        }

        public int getAntalForkerteBogstaver() {
            return this.antalForkerteBogstaver;
        }

        public boolean erSidsteBogstavKorrekt() {
            return this.sidsteBogstavVarKorrekt;
        }

        public boolean erSpilletVundet() {
            return this.spilletErVundet;
        }

        public boolean erSpilletTabt() {
            return this.spilletErTabt;
        }

        public boolean erSpilletSlut() {
            return this.spilletErTabt || this.spilletErVundet;
        }

        public hallowLogic() {
            this.muligeOrd.add("bil");
            this.muligeOrd.add("computer");
            this.muligeOrd.add("programmering");
            this.muligeOrd.add("motorvej");
            this.muligeOrd.add("busrute");
            this.muligeOrd.add("gangsti");
            this.muligeOrd.add("skovsnegl");
            this.muligeOrd.add("solsort");
            this.nulstil();
        }

        public void nulstil() {
            this.brugteBogstaver.clear();
            this.antalForkerteBogstaver = 0;
            this.spilletErVundet = false;
            this.spilletErTabt = false;
            this.ordet = (String)this.muligeOrd.get((new Random()).nextInt(this.muligeOrd.size()));
            this.opdaterSynligtOrd();
        }

        private void opdaterSynligtOrd() {
            this.synligtOrd = "";
            this.spilletErVundet = true;

            for(int n = 0; n < this.ordet.length(); ++n) {
                String bogstav = this.ordet.substring(n, n + 1);
                if(this.brugteBogstaver.contains(bogstav)) {
                    this.synligtOrd = this.synligtOrd + bogstav;
                } else {
                    this.synligtOrd = this.synligtOrd + "*";
                    this.spilletErVundet = false;
                }
            }

        }

        public void gætBogstav(String bogstav) {
            if(bogstav.length() == 1) {
                System.out.println("Der gættes på bogstavet: " + bogstav);
                if(!this.brugteBogstaver.contains(bogstav)) {
                    if(!this.spilletErVundet && !this.spilletErTabt) {
                        this.brugteBogstaver.add(bogstav);
                        if(this.ordet.contains(bogstav)) {
                            this.sidsteBogstavVarKorrekt = true;
                            System.out.println("Bogstavet var korrekt: " + bogstav);
                        } else {
                            this.sidsteBogstavVarKorrekt = false;
                            System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
                            ++this.antalForkerteBogstaver;
                            if(this.antalForkerteBogstaver > 5) {
                                this.spilletErTabt = true;
                            }
                        }

                        this.opdaterSynligtOrd();
                    }
                }
            }
        }

        public void logStatus() {
            System.out.println("---------- ");
            System.out.println("- ordet (skult) = " + this.ordet);
            System.out.println("- synligtOrd = " + this.synligtOrd);
            System.out.println("- forkerteBogstaver = " + this.antalForkerteBogstaver);
            System.out.println("- brugeBogstaver = " + this.brugteBogstaver);
            if(this.spilletErTabt) {
                System.out.println("- SPILLET ER TABT");
            }

            if(this.spilletErVundet) {
                System.out.println("- SPILLET ER VUNDET");
            }

            System.out.println("---------- ");
        }

        public static String hentUrl(String url) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader((new URL(url)).openStream()));
            StringBuilder sb = new StringBuilder();

            for(String linje = br.readLine(); linje != null; linje = br.readLine()) {
                sb.append(linje + "\n");
            }

            return sb.toString();
        }

        public void hentOrdFraDr() throws Exception {
            String data = hentUrl("http://dr.dk");
            System.out.println("data = " + data);
            data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
            System.out.println("data = " + data);
            this.muligeOrd.clear();
            this.muligeOrd.addAll(new HashSet(Arrays.asList(data.split(" "))));
            System.out.println("muligeOrd = " + this.muligeOrd);
            this.nulstil();
        }
    }
