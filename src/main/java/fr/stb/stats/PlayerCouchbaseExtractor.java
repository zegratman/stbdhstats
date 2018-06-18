package fr.stb.stats;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import fr.stb.stats.model.PlayerName;

import java.util.*;

public class PlayerCouchbaseExtractor implements PlayerExtractor {

    List<PlayerName> playerNames;

    Bucket bucket;

    public PlayerCouchbaseExtractor(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public List<PlayerName> getPlayerNames() {
        if (playerNames == null) {
            playerNames = new ArrayList<>();
            N1qlQueryResult result = bucket.query(N1qlQuery.simple(
                    "SELECT " +
                            " ARRAY {i.`first-name`, i.`last-name`}" +
                            " FOR i IN stb.players" +
                            " END AS players" +
                            " FROM `stb-dh-2018` stb" +
                            " WHERE stb.type='stat'"));
            playerNames.addAll(extractNames(result));
        }
        return playerNames;
    }

    private Collection<? extends PlayerName> extractNames(N1qlQueryResult result) {
        Set<PlayerName> playerNames = new HashSet<>();
        for (N1qlQueryRow row : result) {
            for (Object obj : row.value().getArray("players").toList()) {
                if (obj instanceof Map) {
                    Map<String, String> jsonObject = (Map) obj;
                    playerNames.add(new PlayerName(jsonObject.get("first-name"), jsonObject.get("last-name")));
                }
            }
        }
        return playerNames;
    }

}
