import fr.stb.stats.impl.PlayerDirectExtractor;
import fr.stb.stats.model.PlayerExtractor;

public class TestExtractPlayersDirect {

    public static void main(String[] args) {

        PlayerExtractor playerExtractor = new PlayerDirectExtractor("./src/main/resources/");

        System.out.println(playerExtractor.getPlayerNames().size());

    }

}
