package vocs.com.vocs;

/**
 * Created by ASUS on 24/05/2017.
 */

public class mots {
        private String motanglais;
        private String motfrancais;
        private int id;

        public mots(){}

        public mots(String motanglais, String motfrancais){
            this.motanglais=motanglais;
            this.motfrancais=motfrancais;
        }

        public String getMotanglais(){
            return motanglais;
        }

        public String getMotfrancais(){
            return motfrancais;
        }

        public void setMotanglais(String motanglais){
            this.motanglais=motanglais;
        }

        public void setMotfrancais(String motfrancais){
            this.motfrancais=motfrancais;
        }

        public int getId(){
            return id;
        }

        public void setId(int id){
            this.id=id;
        }

        public String toString(){
            return motanglais+" = "+motfrancais+"  ID:"+id;
        }
    }

