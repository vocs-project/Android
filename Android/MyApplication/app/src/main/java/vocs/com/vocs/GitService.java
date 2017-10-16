package vocs.com.vocs;

/**
 * Created by ASUS on 05/10/2017.
 */

import java.util.List;
import retrofit.http.GET;
import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.Query;

public interface GitService {

    public static final String ENDPOINT = "http://vocs.lebarillier.fr/rest";

    @GET("/users")
    void listReposAsync(retrofit.Callback<List<User>> callback);

   @GET("/lists")
   void accederliste(retrofit.Callback<List<Liste>> callback);

}

