package fr.stb.stats;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;

public class PlayerStatConnector {

    private static final String STATEMENT =
            "SELECT SUM(STAT[0]) AS STAT_VALUE \n" +
                    "FROM \n" +
                    "    (SELECT RAW\n" +
                    "        ARRAY i.%stat%\n" +
                    "        FOR i IN stb.players\n" +
                    "        WHEN (i.`first-name`='%fn%' AND i.`last-name`='%ln%')\n" +
                    "        END AS players\n" +
                    "    FROM `stb-dh-2018` stb\n" +
                    "    WHERE stb.type='stat') \n" +
                    "AS STAT";

    private PlayerName playerName;

    private Bucket bucket;

    public PlayerStatConnector(PlayerName playerName, Bucket bucket) {
        this.playerName = playerName;
        this.bucket = bucket;
    }

    PlayerName getPlayerName() {
        return playerName;
    }

    Integer getPlayerStat(BaseballStat stat) {
        int out = 0;
        String statement = STATEMENT.replaceFirst("%stat%", stat.name().toLowerCase());
        statement = statement.replaceFirst("%fn%", playerName.getFirstName());
        statement = statement.replaceFirst("%ln%", playerName.getLastName());
        N1qlQuery n1qlQuery = N1qlQuery.simple(statement);
        N1qlQueryResult result = bucket.query(n1qlQuery);

        if (result.rows().hasNext()) {
            N1qlQueryRow n1qlQueryRow = result.rows().next();
            System.out.println(n1qlQueryRow);
            JsonObject jsonObject = n1qlQueryRow.value();
            if (jsonObject.containsKey("STAT_VALUE") && jsonObject.get("STAT_VALUE") != null) {
                out = jsonObject.getInt("STAT_VALUE");
            }
            System.out.println(out);
        }
        return out;
    }

}
