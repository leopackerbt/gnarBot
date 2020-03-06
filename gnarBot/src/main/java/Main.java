import net.dv8tion.jda.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;


public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        // criando a instância de JDA, usando a class JDABuilder
        JDABuilder builder = new JDABuilder(AccountType.BOT);

        // setando o token, que é gerado pelo discord app
        String token = "Njg1NDg0MjExMzEwMDM0OTQ1.XmJXAA.t5oHUWK1lxc--ePp9YhBgAcmfbY";

        // adicionando o token no JDABuilder
        builder.setToken(token);

        builder.addEventListener(new Main());

        // logar no discord com o nosso bot
        builder.buildAsync();
    }

    Integer passo = 0;

    // após esse passo, adicionamos o extends ListenerAdapter na classe Main
    public void onMessageReceived(MessageReceivedEvent event) {
        String autor = event.getAuthor().getName();
        String mensagem = event.getMessage().getContentDisplay();
        String conteudo = event.getMessage().getContentRaw().toLowerCase();

        if(event.getAuthor().isBot()) {
            return;
        }

        if(passo == 1) {
            if(conteudo.contains("funciona") && conteudo.contains("gnar")) {
                event.getChannel().sendMessage("Sim, " + autor + ", funciona, eu nao sou tao burro.").queue();
                passo = 0;
            }
        }

        System.out.println("Recebemos uma mensagem de " +
                autor + ": " +
                mensagem
        );

        if(conteudo.contains("bom dia"))  {
            event.getChannel().sendMessage("Bom dia, " + autor + ", em que posso ajudar?").queue();
            passo = 1;
        }
        else if(conteudo.contains("brabo")) {
            event.getChannel().sendMessage("Lancei a braba " + autor + ":D").queue();
        }
    }
}
