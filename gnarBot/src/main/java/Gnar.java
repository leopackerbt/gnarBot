import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import oshi.SystemInfo;

import java.util.List;
import java.util.Random;

public class Gnar extends ListenerAdapter {

    static Boolean isGnarLigado = true;
    static MessageReceivedEvent currentEvent;
    static String autor,mensagem,conteudo;
    static Boolean liberarMonitoracao = false;
    static SystemInfo sistema = new SystemInfo();

    public static void setEvent(MessageReceivedEvent event){
        currentEvent = event;
        autor = event.getAuthor().getName();
        mensagem = event.getMessage().getContentDisplay();
        conteudo = event.getMessage().getContentRaw().toLowerCase();
    }

//    private static void verificaLigar() {
//        String texto;
//        if(conteudo.contains("!ligar") ) {
//            if(!isGnarLigado){
//                texto  = "Ligando";
//                isGnarLigado = true;
//            } else{
//                texto = "Já estou ligado";
//            }
//            currentEvent.getChannel().sendMessage(texto).queue();
//        }
//        return;
//    }

    private static void verificaLigar() {

        if(!isGnarLigado) {
            if(conteudo.contains("!ligar")) {
                currentEvent.getChannel().sendMessage("Ligando!").queue();
                isGnarLigado = true;
            }
        return;
        }
    }

    private static void verificaDesligar() {
        if(isGnarLigado && conteudo.contains("!desligar")) {
            currentEvent.getChannel().sendMessage("Desligando").queue();
            isGnarLigado = false;
        }
        return;
    }

    public static void responderMsg(String entrada, String resposta) {
        verificaLigar();
        verificaDesligar();
        if(isGnarLigado && conteudo.contains(entrada)) {
            currentEvent.getChannel().sendMessage(resposta).queue();
            isGnarLigado = true;
        }
    }
    public static void responderMsg(List entrada, String resposta) {
        verificaLigar();
        verificaDesligar();
        if(isGnarLigado && entrada.contains(conteudo)) {
            currentEvent.getChannel().sendMessage(resposta).queue();
            isGnarLigado = true;
        }
    }

    public static void responderMsgRandom(List entrada, List resposta) {
        verificaLigar();
        verificaDesligar();
        if(isGnarLigado && entrada.contains(conteudo)) {
            Random aleatorio = new Random();
            Integer numero = aleatorio.nextInt(resposta.size());
            currentEvent.getChannel().sendMessage(resposta.get(numero).toString().replace("<user>", autor)).queue();
        }
    }

    public static void monitorarRAM(String entrada, String hardware) {
        if(conteudo.contains(entrada) && conteudo.contains(hardware) && liberarMonitoracao == false) {
            currentEvent.getChannel().sendMessage("Irei monitorar a memoria RAM! Poderia me falar por quantos segundos?").queue();
            liberarMonitoracao = true;
        }
    }

    public static void iniciarMonitoramento() {
        if (liberarMonitoracao == true) {
            currentEvent.getChannel().sendMessage("aaaaaaa").queue();
            System.out.println(conteudo);
            Integer contador = 0;
            Integer tempoMonitorar = 0;
            try {
                tempoMonitorar = Integer.parseInt(conteudo);
                while (contador < tempoMonitorar) {
                    Thread.sleep(1000);
                    long memoriaDisponivel = ((sistema.getHardware().getMemory().getAvailable() / 1024) / 1024);
                    currentEvent.getChannel().sendMessage("Voce possui **" + memoriaDisponivel + "** MB de memoria disponivel.").queue();
                    contador += 1;
                }
            } catch (NumberFormatException | InterruptedException numeroInvalidoException) {
                currentEvent.getChannel().sendMessage("Voce nao digitou um numero valido.").queue();
            } finally {
                liberarMonitoracao = false;
                currentEvent.getChannel().sendMessage(String.format("Monitoramento finalizado! Duracao: %d segundos :smile:", tempoMonitorar)).queue();
            }
            liberarMonitoracao = false;
        }
    }
}
