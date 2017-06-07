package vocs.com.vocs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import java.text.Normalizer;
import android.content.Intent;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    String[] motanglais = new String[500];
    String[] motfrancais = new String[500];
    String motaffiche,motreponse,motsolution;
    int nb,nbmot,bon,tt;

    TextView afficheur,bienmal,soluc,lemot;
    EditText edit;
    Button valider,aide,score,retour;
    Switch bswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        afficheur = (TextView) findViewById(R.id.motatra);
        edit = (EditText) findViewById(R.id.reponse);
        valider = (Button) findViewById(R.id.valider);
        aide=(Button) findViewById(R.id.aide);
        bswitch = (Switch) findViewById(R.id.bswitch);
        bienmal = (TextView) findViewById(R.id.bienmal);
        soluc= (TextView) findViewById(R.id.soluc);
        bswitch.setChecked(true);
        score = (Button) findViewById(R.id.score);
        retour = (Button) findViewById(R.id.retour);
        lemot = (TextView) findViewById(R.id.lemot);

        bon=0;
        tt=0;
        nb=(int) (Math.random()*nbmot);
        afficheur.setText(motaffiche);
        anim_score();
        afficheur();
        fonction();

    }

    private void afficheur(){

        nbmot=207;

        motanglais[0]="gain access to"; motfrancais[0]="avoir acces";
        motanglais[1]="gap"; motfrancais[1]="ecart";
        motanglais[2]="garbage"; motfrancais[2]="dechet";
        motanglais[3]="garbage in"; motfrancais[3]="donnee nulle";
        motanglais[4]="garbage out"; motfrancais[4]="resultat nul";
        motanglais[5]="gate"; motfrancais[5]="porte";
        motanglais[6]="gather"; motfrancais[6]="rassembler";
        motanglais[7]="gear"; motfrancais[7]="materiel";
        motanglais[8]="general-purpose"; motfrancais[8]="a usages multiples";
        motanglais[9]="generate"; motfrancais[9]="generer";
        motanglais[10]="genuine"; motfrancais[10]="authentique";
        motanglais[11]="get rid of"; motfrancais[11]="se debarrasser de";
        motanglais[12]="glare-free"; motfrancais[12]="antireflet";
        motanglais[13]="glitch"; motfrancais[13]="probleme technique";
        motanglais[14]="glossary"; motfrancais[14]="glossaire";
        motanglais[15]="goal"; motfrancais[15]="but";
        motanglais[16]="grab"; motfrancais[16]="attraper";
        motanglais[17]="grabber"; motfrancais[17]="appareil de saisie";
        motanglais[18]="grain"; motfrancais[18]="grain";
        motanglais[19]="graph"; motfrancais[19]="graphique";
        motanglais[20]="graft"; motfrancais[20]="greffer";
        motanglais[21]="graph plotter"; motfrancais[21]="traceur de courbes";
        motanglais[22]="graphic"; motfrancais[22]="graphique";
        motanglais[23]="graphical user interface"; motfrancais[23]="interface graphique";
        motanglais[24]="graphics"; motfrancais[24]="visualisation graphique";
        motanglais[25]="grid"; motfrancais[25]="grille";
        motanglais[26]="grip"; motfrancais[26]="saisir";
        motanglais[27]="growth"; motfrancais[27]="croissance";
        motanglais[28]="guillotine"; motfrancais[28]="massicoter";
        motanglais[29]="hack"; motfrancais[29]="pirater";
        motanglais[30]="helpware"; motfrancais[30]="logiciel d'aide";
        motanglais[31]="hacker"; motfrancais[31]="pirate informatique";
        motanglais[32]="hi-res"; motfrancais[32]="haute resolution";
        motanglais[33]="hacker-proof"; motfrancais[33]="protege contre piratage";
        motanglais[34]="high-tech"; motfrancais[34]="de pointe";
        motanglais[35]="hacking"; motfrancais[35]="piratage informatique";
        motanglais[36]="hide"; motfrancais[36]="cacher";
        motanglais[37]="halt"; motfrancais[37]="arreter";
        motanglais[38]="highlight"; motfrancais[38]="marquer";
        motanglais[39]="handheld computer"; motfrancais[39]="ordinateur portable";
        motanglais[40]="hitch"; motfrancais[40]="panne";
        motanglais[41]="(to)handle"; motfrancais[41]="traiter";
        motanglais[42]="hole"; motfrancais[42]="trou";
        motanglais[43]="handle"; motfrancais[43]="poignee";
        motanglais[44]="home automation"; motfrancais[44]="domotique";
        motanglais[45]="handset"; motfrancais[45]="combine telephonique";
        motanglais[46]="home computer"; motfrancais[46]="ordinateur domestique";
        motanglais[47]="handshake"; motfrancais[47]="etablissement de liaison";
        motanglais[48]="homeshopping"; motfrancais[48]="teleachat";
        motanglais[49]="handy"; motfrancais[49]="pratique";
        motanglais[50]="hood"; motfrancais[50]="capot";
        motanglais[51]="hard copy"; motfrancais[51]="tirage sur papier";
        motanglais[52]="hook up"; motfrancais[52]="connecter";
        motanglais[53]="hard disk"; motfrancais[53]="disque dur";
        motanglais[54]="host computer"; motfrancais[54]="ordinateur serveur";
        motanglais[55]="hardware"; motfrancais[55]="materiel informatique";
        motanglais[56]="(to)house"; motfrancais[56]="loger";
        motanglais[57]="hatch"; motfrancais[57]="hachurer";
        motanglais[58]="housing"; motfrancais[58]="logement";
        motanglais[59]="hazard"; motfrancais[59]="risque";
        motanglais[60]="hub"; motfrancais[60]="centre";
        motanglais[61]="hazardous"; motfrancais[61]="risque";
        motanglais[62]="hue"; motfrancais[62]="teinte";
        motanglais[63]="hdtv"; motfrancais[63]="tv haute definition";
        motanglais[64]="huge"; motfrancais[64]="enorme";
        motanglais[65]="(to)head"; motfrancais[65]="mener";
        motanglais[66]="hyphen"; motfrancais[66]="trait d'union";
        motanglais[67]="header"; motfrancais[67]="en-tete";
        motanglais[68]="hyphenate"; motfrancais[68]="mettre un trait d'union";
        motanglais[69]="headset"; motfrancais[69]="casque";
        motanglais[70]="hyphenation"; motfrancais[70]="cesure";
        motanglais[71]="(to)heat"; motfrancais[71]="chauffer";
        motanglais[72]="heat"; motfrancais[72]="chaleur";
        motanglais[73]="helpline"; motfrancais[73]="service d'assistance telephonique";
        motanglais[74]="integrated circuit"; motfrancais[74]="circuit integre";
        motanglais[75]="induce"; motfrancais[75]="induire";
        motanglais[76]="icon"; motfrancais[76]="icone";
        motanglais[77]="infect"; motfrancais[77]="infecter";
        motanglais[78]="identification"; motfrancais[78]="identification";
        motanglais[79]="information technology"; motfrancais[79]="informatique";
        motanglais[80]="identifier"; motfrancais[80]="identificateur";
        motanglais[81]="infrared"; motfrancais[81]="infrarouge";
        motanglais[82]="identify"; motfrancais[82]="identifier";
        motanglais[83]="infringment"; motfrancais[83]="infraction";
        motanglais[84]="idle"; motfrancais[84]="inactif";
        motanglais[85]="inhibit"; motfrancais[85]="inhiber";
        motanglais[86]="idleness"; motfrancais[86]="inactivite";
        motanglais[87]="in-house"; motfrancais[87]="interne";
        motanglais[88]="ie"; motfrancais[88]="c'est a dire";
        motanglais[89]="ink-jet printer"; motfrancais[89]="imprimante a jet d'encre";
        motanglais[90]="ignore"; motfrancais[90]="ignorer";
        motanglais[91]="input"; motfrancais[91]="entree";
        motanglais[92]="illegal"; motfrancais[92]="interdit";
        motanglais[93]="output"; motfrancais[93]="sortie";
        motanglais[94]="illegible"; motfrancais[94]="illisible";
        motanglais[95]="insert"; motfrancais[95]="inserer";
        motanglais[96]="imaging"; motfrancais[96]="imagerie";
        motanglais[97]="instruction manual"; motfrancais[97]="manuel d'utilisation";
        motanglais[98]="impact printer"; motfrancais[98]="imprimante a impact";
        motanglais[99]="instruction set"; motfrancais[99]="jeu d'instruction";
        motanglais[100]="implement"; motfrancais[100]="implanter";
        motanglais[101]="instruction sheet"; motfrancais[101]="notice d'instruction";
        motanglais[102]="implementation"; motfrancais[102]="implementation";
        motanglais[103]="insulate"; motfrancais[103]="isoler";
        motanglais[104]="imply"; motfrancais[104]="impliquer";
        motanglais[105]="insure"; motfrancais[105]="assurer";
        motanglais[106]="improve"; motfrancais[106]="ameliorer";
        motanglais[107]="integer"; motfrancais[107]="nombre entier";
        motanglais[108]="improvement"; motfrancais[108]="amelioration";
        motanglais[109]="isdn"; motfrancais[109]="rnis";
        motanglais[110]="impulse"; motfrancais[110]="impulsion";
        motanglais[111]="interchange"; motfrancais[111]="echange";
        motanglais[112]="inaccuracy"; motfrancais[112]="imprecision";
        motanglais[113]="intercom"; motfrancais[113]="interphone";
        motanglais[114]="inaccurate"; motfrancais[114]="imprecis";
        motanglais[115]="interfere"; motfrancais[115]="interferer";
        motanglais[116]="inch"; motfrancais[116]="pouce";
        motanglais[117]="inverted commas"; motfrancais[117]="guillemets";
        motanglais[118]="incoming"; motfrancais[118]="entrant";
        motanglais[119]="invoice"; motfrancais[119]="facture";
        motanglais[120]="inconsistent"; motfrancais[120]="incoherent";
        motanglais[121]="irrelevant"; motfrancais[121]="hors sujet";
        motanglais[122]="increase"; motfrancais[122]="augmenter";
        motanglais[123]="issue"; motfrancais[123]="probleme";
        motanglais[124]="incur"; motfrancais[124]="encourir";
        motanglais[125]="indent"; motfrancais[125]="alinea";
        motanglais[126]="jam"; motfrancais[126]="blocage";
        motanglais[127]="index"; motfrancais[127]="indice";
        motanglais[128]="kb"; motfrancais[128]="ko";
        motanglais[129]="leak"; motfrancais[129]="fuir";
        motanglais[130]="keep track"; motfrancais[130]="faire le suivi";
        motanglais[131]="led"; motfrancais[131]="diode electroluminescente";
        motanglais[132]="kernel"; motfrancais[132]="centre";
        motanglais[133]="legible"; motfrancais[133]="lisible";
        motanglais[134]="key in"; motfrancais[134]="entrer";
        motanglais[135]="lenght"; motfrancais[135]="longueur";
        motanglais[136]="key"; motfrancais[136]="cle";
        motanglais[137]="lens"; motfrancais[137]="lentille";
        motanglais[138]="keyboard"; motfrancais[138]="clavier";
        motanglais[139]="level"; motfrancais[139]="niveau";
        motanglais[140]="keypad"; motfrancais[140]="pave numerique";
        motanglais[141]="line feed"; motfrancais[141]="saut de ligne";
        motanglais[142]="kill"; motfrancais[142]="tuer";
        motanglais[143]="license"; motfrancais[143]="autoriser";
        motanglais[144]="knob"; motfrancais[144]="bouton";
        motanglais[145]="lid"; motfrancais[145]="capot";
        motanglais[146]="know-how"; motfrancais[146]="savoir faire";
        motanglais[147]="light pen"; motfrancais[147]="crayon optique";
        motanglais[148]="knowledgeware"; motfrancais[148]="logiciel expert";
        motanglais[149]="(to)link"; motfrancais[149]="lier";
        motanglais[150]="link"; motfrancais[150]="liaison";
        motanglais[151]="(to)label"; motfrancais[151]="etiqueter";
        motanglais[152]="linkage"; motfrancais[152]="liaison";
        motanglais[153]="label"; motfrancais[153]="etiquette";
        motanglais[154]="load"; motfrancais[154]="charger";
        motanglais[155]="labour-intensive"; motfrancais[155]="couteux en main d'oeuvre";
        motanglais[156]="locate"; motfrancais[156]="localiser";
        motanglais[157]="labour-saving"; motfrancais[157]="qui facilite le travail";
        motanglais[158]="location"; motfrancais[158]="emplacement";
        motanglais[159]="lack"; motfrancais[159]="manque";
        motanglais[160]="lock"; motfrancais[160]="verouiller";
        motanglais[161]="lag"; motfrancais[161]="trainer";
        motanglais[162]="log"; motfrancais[162]="registre";
        motanglais[163]="local area network"; motfrancais[163]="reseau local";
        motanglais[164]="log in"; motfrancais[164]="se connecter";
        motanglais[165]="laptop"; motfrancais[165]="ordinateur protable";
        motanglais[166]="log off"; motfrancais[166]="se deconnecter";
        motanglais[167]="laser printer"; motfrancais[167]="imprimante laser";
        motanglais[168]="look up"; motfrancais[168]="chercher";
        motanglais[169]="launch"; motfrancais[169]="lancer";
        motanglais[170]="loop"; motfrancais[170]="boucle";
        motanglais[171]="layer"; motfrancais[171]="couche";
        motanglais[172]="speaker"; motfrancais[172]="haut-parleur";
        motanglais[173]="layout"; motfrancais[173]="presentation";
        motanglais[174]="lower case"; motfrancais[174]="minuscule";
        motanglais[175]="liquid crystal display"; motfrancais[175]="affichage a cristaux liquides";
        motanglais[176]="luggable"; motfrancais[176]="portable";
        motanglais[177]="magnify"; motfrancais[177]="agrandir";
        motanglais[178]="mount"; motfrancais[178]="monter";
        motanglais[179]="main"; motfrancais[179]="secteur";
        motanglais[180]="multitasking"; motfrancais[180]="traitement multitâches";
        motanglais[181]="mainframe"; motfrancais[181]="gros système";
        motanglais[182]="mundane"; motfrancais[182]="banal";
        motanglais[183]="maintain"; motfrancais[183]="entretenir";
        motanglais[184]="maintenance"; motfrancais[184]="maintenance";
        motanglais[185]="make"; motfrancais[185]="marque";
        motanglais[186]="(to)malfunction"; motfrancais[186]="mal fonctionner";
        motanglais[187]="malfunction"; motfrancais[187]="defaillance technique";
        motanglais[188]="management"; motfrancais[188]="gestion";
        motanglais[189]="manufacturer"; motfrancais[189]="fabricant";
        motanglais[190]="(to)map"; motfrancais[190]="faire une projection de";
        motanglais[191]="margin"; motfrancais[191]="marge";
        motanglais[192]="master"; motfrancais[192]="maitriser";
        motanglais[193]="match"; motfrancais[193]="correspondre";
        motanglais[194]="matrix"; motfrancais[194]="matrice";
        motanglais[195]="maximize"; motfrancais[195]="agrandir";
        motanglais[196]="mb"; motfrancais[196]="mo";
        motanglais[197]="medium"; motfrancais[197]="support";
        motanglais[198]="pull-down menu"; motfrancais[198]="menu deroulant";
        motanglais[199]="merge"; motfrancais[199]="fusionner";
        motanglais[200]="merger"; motfrancais[200]="fusion";
        motanglais[201]="microchip"; motfrancais[201]="circuit integre";
        motanglais[202]="minimize"; motfrancais[202]="reduire";
        motanglais[203]="mismatch"; motfrancais[203]="disparite";
        motanglais[204]="misplace"; motfrancais[204]="egarer";
        motanglais[205]="misprint"; motfrancais[205]="une faute";
        motanglais[206]="monitor"; motfrancais[206]="controler";
    }

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    public void config(){

        motreponse = edit.getText().toString();
        motreponse = motreponse.toLowerCase();
        motreponse = removeAccent(motreponse);

    }



    public void anim_score(){
        score.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent score = new Intent(MainActivity.this, score.class);
                Bundle b = new Bundle();
                Bundle t = new Bundle();
                b.putInt("key", bon);
                t.putInt("autre", tt);
                score.putExtras(b);
                score.putExtras(t);
                startActivity(score);
                finish();
            }
    });
        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent retour2 = new Intent (MainActivity.this, PagePrinc.class);
                startActivity(retour2);
            }
        });
    }

    public void maide(){
        aide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               soluc.setText(motsolution);
            }
        });

    }

    public void fonction(){
        if (bswitch.isChecked()){

            motaffiche = motfrancais[nb];
            motsolution = motanglais[nb];

            afficheur.setText(motaffiche);
            anim_score();
            maide();
            valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anim_score();
                    config();
                    if (motreponse.equals(motsolution)) {
                        bienmal.setText("Bien");
                        bienmal.setTextColor(Color.GREEN);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution + " : " + motaffiche);
                        bon++;
                        nb=(int) (Math.random()*nbmot);
                        afficheur.setText(motaffiche);
                        fonction();
                    } else {

                        bienmal.setText("Pas exactement");
                        bienmal.setTextColor(Color.RED);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution + " : " + motaffiche);
                        nb=(int) (Math.random()*nbmot);
                        afficheur.setText(motaffiche);
                        fonction();
                    }tt++;

                }
            }) ;
        }
        else {

            motaffiche = motanglais[nb];
            motsolution=motfrancais[nb];
            anim_score();
            config();
            afficheur.setText(motaffiche);
            maide();
            valider.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    anim_score();
                    config();
                    if (motreponse.equals(motsolution)){
                        bienmal.setText("Good");
                        bienmal.setTextColor(Color.GREEN);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution + " : " + motaffiche);
                        bon++;
                        nb=(int) (Math.random()*nbmot);
                        afficheur.setText(motaffiche);
                        fonction();
                    }
                    else{
                        bienmal.setText("Not really");
                        bienmal.setTextColor(Color.RED);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution + " : " + motaffiche);
                        nb=(int) (Math.random()*nbmot);
                        afficheur.setText(motaffiche);
                        fonction();
                    }tt++;
                }
            });
        }
    }
}
