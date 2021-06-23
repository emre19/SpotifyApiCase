package Services;

import Helpers.Helper;
import Specs.RequestSpec;
import Specs.ResponseSpec;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.awt.*;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Playlists extends RequestSpec {

    public Playlists() {
        super("/v1/playlists");
    }

    public int getPlaylistSize(String playlistId, int statusCode){

        Response response =
                given()
                    .spec(super.getRequestSpecification())
                    .get("/{playlist_id}/tracks",playlistId)
                .then()
                    .spec(ResponseSpec.checkStatusCode(statusCode))
                    .extract()
                    .response();

        List<Object> trackList = response.jsonPath().getList("items");
        System.out.println("Listedeki şarkı sayısı : "+trackList.size());
        return trackList.size();
    }

    public void addTracks(List<String> tracks, String playlistId, int statusCode){

        for (String track : tracks){

            given()
                    .spec(super.getRequestSpecification())
                    .queryParam("uris",track)
                    .post("/{playlist_id}/tracks",playlistId)
            .then()
                    .spec(ResponseSpec.checkStatusCode(statusCode));

            System.out.println(track + " şarkısı eklendi.");
        }
    }

    public void deleteTrack(String trackUri, String playlistId, int statusCode){

        JSONObject deleteBody = Helper.readJsonFile("deleteBody");
        deleteBody.getJSONArray("tracks").getJSONObject(0).put("uri",trackUri);

        given()
                .spec(super.getRequestSpecification())
                .body(deleteBody.toString())
                .delete("/{playlist_id}/tracks",playlistId)
        .then()
                .spec(ResponseSpec.checkStatusCode(statusCode));

        System.out.println(trackUri + " şarkısı listeden silindi");
    }
}
