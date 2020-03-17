import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import oshi.SystemInfo;
import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        // String token = JOptionPane.showInputDialog("Insira o token do seu bot"); //"Njg1NDg0MjExMzEwMDM0OTQ1.XmfSag.s1ex-DVJiHpNhNlYdV_vMLkSaNc";
        //token = (token.isEmpty()) ? "Njg1NDg0MjExMzEwMDM0OTQ1.XmfSag.s1ex-DVJiHpNhNlYdV_vMLkSaNc": token;

        Login.main();

        if (Login.token.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Token vazio");
        }
    }

    // setando variáveis estáticas
    SystemInfo sistema = new SystemInfo();
    List<String> saudacoesHora = Arrays.asList("bom dia","boa tarde","boa noite","b dia","b tarde","b noite");
    List<String> saudacoesGnar = Arrays.asList("bom dia","boa tarde","boa noite","b dia","b tarde","b noite");
    List<String> saudacoesGnarResposta = Arrays.asList("Ola <user>, em que posso ajudar? ","E ai <user>! Precisa de ajuda?",
            "Opa, <user>, beleza? Precisa de alguma coisa?");
    List<String> entradaRandom = Arrays.asList("entrada", "entrada2");
    List<String> respostaRandom = Arrays.asList("resposta1", "resposta2", "resposta3");

    public void onMessageReceived(MessageReceivedEvent event) {
        Gnar.setEvent(event);
        Gnar.mensagemConsole();

        if(event.getAuthor().isBot()) {
            return;
        }

        Gnar.iniciarMonitoramentoRAM();

        Gnar.iniciarMonitoramentoCPU();

        Gnar.responderHorarioRandom(saudacoesHora);

        Gnar.responderMsg("hello gnar","Salve, " + Gnar.autor + ", em que posso ajudar?"); // metódos de resposta simples (input, output)

//        Gnar.responderMsg("salve","É nois"); // métodos de resposta simples com lista (lista, output)

        Gnar.responderMsgRandom(saudacoesGnar, saudacoesGnarResposta); // métodos de resposta aleatória com lista (listaInput, listaOutput)

        Gnar.monitorarRAM("!monitorar", "ram");

        Gnar.monitorar();

/*
        if (statusGnar == 1) {
            if (passo == 1) {
                if (conteudo.contains("funciona") && conteudo.contains("gnar")) {
                    event.getChannel().sendMessage("Sim, " + autor + ", funciona, eu nao sou tao burro.").queue();
                    passo = 0;
                }
            }


            if (conteudo.contains("bom dia") && autor.contains("lpacker")) {
                event.getChannel().sendMessage("Bom dia, criador :)").queue();
                passo = 1;
            } else if (conteudo.contains("bom dia")) {
                event.getChannel().sendMessage("Bom dia, " + autor + ", em que posso ajudar?").queue();
                passo = 1;
            } else if (conteudo.contains("brabo")) {
                event.getChannel().sendMessage("Lancei a braba " + autor + " :D").queue();
                passo = 0;
            } else if (conteudo.contains("gnar") && conteudo.contains("monitorar")) {
                event.getChannel().sendMessage("Voce possui " + memoriaDisponivel + " MB de memoria disponivel.").queue();
                passo = 0;
            } else if (conteudo.contains("obrigado") && conteudo.contains("gnar")) {
                event.getChannel().sendMessage(":)").queue();
                passo = 0;
            } else if (conteudo.contains("gnar") && conteudo.contains("painel de adm")) {
                if (autor.contains("lpacker")) {
                    event.getChannel().sendMessage("Ola criador, o painel agora esta **ligado**!").queue();
                } else {
                    event.getChannel().sendMessage("Voce nao eh meu criador").queue();
                }
                // event.getChannel().sendMessage("Ola criador, o painel agora esta **ligado**!").queue();
                // admin = 1;
            } else if (conteudo.contains("!monitorar") && conteudo.contains("ram")) {
                event.getChannel().sendMessage("Irei monitorar a memoria RAM! Poderia me falar por quantos segundos?").queue();
                liberarMonitoracao = 1;
            } else if (conteudo.contains("!desligar")) {
                event.getChannel().sendMessage("#PartiuDormir :sleeping:").queue();
                statusGnar = 0;
            }

        }

 */

    }

}
