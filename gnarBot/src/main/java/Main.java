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
import java.util.Timer;

public class Main extends ListenerAdapter {

    static JDABuilder builder = new JDABuilder(AccountType.BOT); // criando a instância de JDA, usando a class JDABuilder
    static String token = "Njg1NDg0MjExMzEwMDM0OTQ1.XmfSag.s1ex-DVJiHpNhNlYdV_vMLkSaNc"; // setando o token, que é gerado pelo discord app

    public static void main(String[] args) throws LoginException {
        // configurando o oshi
        SystemInfo sistema = new SystemInfo();
        long memoriaDisponivel = ((sistema.getHardware().getMemory().getAvailable() / 1024 ) / 1024);
        // String token = JOptionPane.showInputDialog("Insira o token do seu bot"); //"Njg1NDg0MjExMzEwMDM0OTQ1.XmfSag.s1ex-DVJiHpNhNlYdV_vMLkSaNc";
        //token = (token.isEmpty()) ? "Njg1NDg0MjExMzEwMDM0OTQ1.XmfSag.s1ex-DVJiHpNhNlYdV_vMLkSaNc": token;
        if (token.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Token vazio");
        }
        builder.setToken(token); // adicionando o token no JDABuilder
        builder.addEventListener(new Main());
        builder.setGame(Game.playing("!comandos"));
        builder.buildAsync();// logar no discord com o nosso bot
    }

    // setando variáveis estáticas
    Integer passo = 0;
    Integer admin = 1;
    SystemInfo sistema = new SystemInfo();
    List<String> saudacoes = Arrays.asList("bom dia","quale","salve","boa noite","dlc");
    List<String> saudacoesResposta = Arrays.asList("Ola <user>, em que posso ajudar? ","E ai <user>! Precisa de ajuda?");
    List<String> entradaRandom = Arrays.asList("entrada", "entrada2");
    List<String> respostaRandom = Arrays.asList("resposta1", "resposta2", "resposta3");
    Timer tempo = new Timer();
    Integer intervaloUm = 1000;
    Integer intervaloDois = 2000;
    Integer tempoMonitorar;
    Integer liberarMonitoracao = 0;
    Integer contador = 0;

    // após esse passo, adicionamos o extends ListenerAdapter na classe Main
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println(respostaRandom.size());
        System.out.println(respostaRandom.get(2));
        Gnar.setEvent(event);
        long memoriaDisponivel = ((sistema.getHardware().getMemory().getAvailable() / 1024 ) / 1024);

//        if(Gnar.isGnarLigado){
//            builder.setStatus(OnlineStatus.ONLINE);
//            builder.setGame(Game.listening("!commands"));
//        }else{
//            builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
//            builder.setGame(Game.listening("Use !ligar"));
//        }

        if(event.getAuthor().isBot()) {
            return;
        }


        Gnar.responderMsg("hello gnar","Salve, " + Gnar.autor + ", em que posso ajudar?"); // metódos de resposta simples (input, output)

        Gnar.responderMsg(saudacoes,"É nois"); // métodos de resposta simples com lista (lista, output)

        Gnar.responderMsgRandom(saudacoes, saudacoesResposta); // métodos de resposta aleatória com lista (listaInput, listaOutput)

        Gnar.iniciarMonitoramento();

        Gnar.monitorarRAM("!monitorar", "ram");



        System.out.println("Recebemos uma mensagem de " + Gnar.autor + ": " + Gnar.mensagem );
/*
        if (statusGnar == 1) {
            if (passo == 1) {
                if (conteudo.contains("funciona") && conteudo.contains("gnar")) {
                    event.getChannel().sendMessage("Sim, " + autor + ", funciona, eu nao sou tao burro.").queue();
                    passo = 0;
                }
            }

            if (liberarMonitoracao == 1) {
                contador = 0;
                try {
                    tempoMonitorar = Integer.parseInt(conteudo);
                    while (contador < tempoMonitorar) {
                        Thread.sleep(1000);
                        memoriaDisponivel = ((sistema.getHardware().getMemory().getAvailable() / 1024) / 1024);
                        event.getChannel().sendMessage("Voce possui **" + memoriaDisponivel + "** MB de memoria disponivel.").queue();
                        contador += 1;
                    }
                } catch (NumberFormatException | InterruptedException numeroInvalidoException) {
                    event.getChannel().sendMessage("Voce nao digitou um numero valido.").queue();
                } finally {
                    liberarMonitoracao = 0;
                    event.getChannel().sendMessage(String.format("Monitoramento finalizado! Duracao: %d segundos :smile:", tempoMonitorar)).queue();
                }
            }

            if (palavroes.contains(conteudo)) {
                event.getChannel().sendMessage(String.format("Olha a boca, %s!", autor)).queue();
            }

            if (conteudo.contains("!painel") && autor.contains("lpacker") && admin == 1) {
                try {
                    Thread.sleep(2000);
                    event.getChannel().sendMessage("Pode falar").queue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

            System.out.println(memoriaDisponivel);
        }

 */

    }

}
