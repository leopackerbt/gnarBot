import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Login extends ListenerAdapter {

    static JDABuilder builder = new JDABuilder(AccountType.BOT); // criando a instância de JDA, usando a class JDABuilder
    static String token = "Njg1NDg0MjExMzEwMDM0OTQ1.XnAHYQ.4cNVDORTiYpUDCLoOXRaLxiCHIg"; // setando o token, que é gerado pelo discord app

    public static void main() throws LoginException {
        builder.setToken(token); // adicionando o token no JDABuilder
        builder.addEventListener(new Main());
        builder.setGame(Game.playing("!comandos"));
        builder.buildAsync();// logar no discord com o nosso bot
    }
}
