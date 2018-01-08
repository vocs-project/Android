package vocs.com.vocs;

/**
 * Created by ASUS on 05/10/2017.
 */

import java.util.List;

import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Body;
import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit2.Call;
import retrofit2.Retrofit;

import static android.R.attr.password;
import static android.R.attr.path;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.M;

public interface GitService {

    public static final String ENDPOINT = "https://vocsAPI.lebarillier.fr/rest";
    //http://vocs.lebarillier.fr/rest

    @GET("/users/{iduser}/lists/{idlistes}")
    void accedermots(@Path ("iduser") String iduser,@Path ("idlistes") String idlistes, Callback<MotsListe> callback);

    @GET("/users/{iduser}")
    void accederaunuser(@Path ("iduser") String iduser, Callback<User> callback);

    @POST("/users/authentification")
    void testconnexion (@Body LoginInfo log, Callback<User> callback);

    @POST("/users")
    void userpost(@Body Userpost userpost, Callback<Userpost> callback);

    @GET("/users")
    void accederauxusers(Callback<List<User>> callback);

    @GET("/users/{iduser}/lists")
    void accederlistedunuser(@Path ("iduser") String iduser, Callback<List<Liste>> callback);

    @GET("/users/{iduser}/classes")
    void classesdunuser(@Path ("iduser") String iduser, Callback<List<Classes>> callback);

    @GET("/classes/{idclasses}/lists")
    void obtenirlistsclasses(@Path ("idclasses") String idclasses, Callback<List<Liste>> callback);

    @GET("/classes/{idclasses}")
    void obtenirclasses(@Path ("idclasses") String idclasses, Callback<Classes> callback);

    @POST("/users/{iduser}/lists")
    void ajouterliste(@Path ("iduser") String iduser, @Body Listepost liste, Callback<Liste> callback);

    @POST("/users/{iduser}/lists/{idlists}/wordTrad")
    void ajoutermotdanslisteuser(@Path ("iduser") String iduser, @Path ("idlists") String idlists, @Body Wordpost wordpost, Callback<Wordpost> callback);

    @DELETE("/lists/{idlists}")
    void deleteliste(@Path ("idlists") String idlists,Callback<Liste> callback);

    @DELETE("/lists/{idlists}/wordTrad/{idwordTrad}")
    void deletemot(@Path ("idlists") String idlists, @Path ("idwordTrad") String wordTrad,Callback<MotsListe> callback);

    @GET ("/classes")
    void getclasses(Callback<List<Classes>> callback);

    @POST("/users/{iduser}/classes")
    void creerclasse(@Path ("iduser") String iduser,@Body Postclasse classe, Callback<Postclasse> callback);

    @GET("/lists/{idlists}")
    void accederliste(@Path ("idlists") String idlists,Callback<MotsListe> callback);

    @POST("/demands")
    void envoyerdemande(@Body Demands demands, Callback<GetDemands> callback);

    @GET("/demands/users/{iduser}")
    void getdemandsuser(@Path ("iduser") String iduser, Callback<GetDemands> callback);

    @DELETE("/demands/{iddemands}")
    void deletedemands(@Path("iddemands") String iddemands, Callback<GetDemands> callback);

    @PATCH("/users/{iduser}")
    void ajouterclasseauser(@Path ("iduser") String iduser,@Body PatchCLasse patchclasse, Callback<User> callback);

    @DELETE("/classes/{idclasse}")
    void deleteclasse(@Path ("idclasse") String idclasse, Callback<Classe> callback);

    @PATCH("/classes/{idclasse}")
    void patchclasseliste(@Path ("idclasse") String idclasse,@Body PatchList patchlist, Callback<Classe> callback);

    @PATCH("/classes/{idclasse}")
    void patchclassename(@Path ("idclasse") String idclasse,@Body PatchListeName patchlistename, Callback<Classe> callback);

    @DELETE("/classes/{idclasse}/users/{iduser}")
    void deleteuserdeclasse(@Path ("idclasse") String idclasse, @Path ("iduser") String iduser, Callback<Classe> callback);

    @PATCH("/users/{iduser}")
    void patchunuser(@Path ("iduser") String iduser,@Body UserPatch userpatch, Callback<User> callback);

    @PATCH("/users/{iduser}")
    void patchunpassword(@Path ("iduser") String iduser,@Body UserPatchPassword userpatchPassword, Callback<User> callback);
}

