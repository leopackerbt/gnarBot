import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import oshi.SystemInfo;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Gnar extends ListenerAdapter {

    static CPU cpu;
    static RAM memoriaRam;
    static Boolean isGnarLigado = true;
    static MessageReceivedEvent currentEvent;
    static String autor,mensagem,conteudo;
    static Boolean liberarMonitoracaoRAM = false;
    static Boolean liberarMonitoracaoCPU = false;
    static Boolean liberarMonitoracaoHD = false;
    static SystemInfo sistema = new SystemInfo();
    static Double memoriaTotal = 0.0;
    static Integer horaAtual;
    static List<String> respostaDia = Arrays.asList("Bom dia, <user>! Em que posso ajudar? :smile:", "Eai <user>, bom dia! Precisa de ajuda? :blush: ","bom dia");
    static List<String> respostaTarde = Arrays.asList("Boa tarde, <user>! Em que posso ajudar? :smile:", "Eai <user>, boa tarde! Precisa de ajuda? :blush: ","boa tarde");
    static List<String> respostaNoite = Arrays.asList("Boa noite, <user>! Em que posso ajudar? :smile:", "Eai <user>, boa noite! Precisa de ajuda? :blush: ","boa noite");
    static List<String> respostaMadrugada = Arrays.asList("Boa madrugada, <user>! Em que posso ajudar? :sleeping:", "Eai <user>, boa madrugada! Precisa de ajuda? :blush:","boa madruga");
    private static String msgCmds = "> Lista de comandos do GnarBot:\n" +
            "**!ligar** - Ativa as funcoes e resposta do Gnar\n" +
            "**!desligar** - Desativa as funcoes e respostas\n" +
            "**monitorar** [ram/cpu/hd/gpu] - Inicia um monitoramento de hardware (1 por vez)\n";

    public Gnar() {
        cpu = new CPU();
        memoriaRam = new RAM();
    }

    public static void setEvent(MessageReceivedEvent event){
        currentEvent = event;
        autor = event.getAuthor().getName();
        mensagem = event.getMessage().getContentDisplay();
        conteudo = event.getMessage().getContentRaw().toLowerCase();
    }

    // caso esteja desligado, não consegue entender nenhuma mensagem que não seja !ligar
    private static void verificaLigar() {
        if(!isGnarLigado) {
            if(conteudo.contains("!ligar")) {
                currentEvent.getChannel().sendMessage("> #PartiuTrabalhar :smile:").queue();
                isGnarLigado = true;
            }
        return;
        }
    }

    private static void verificaDesligar() {
        if(isGnarLigado && conteudo.contains("!desligar")) {
            currentEvent.getChannel().sendMessage("> #PartiuDormir :sleeping: ").queue();
            isGnarLigado = false;
        }
        return;
    }

    // recebe uma string como entrada e outra string como resposta do bot
    public static void responderMsg(String entrada, String resposta) {
        verificaLigar();
        verificaDesligar();
        if(isGnarLigado && conteudo.contains(entrada)) {
            currentEvent.getChannel().sendMessage(resposta).queue();
            isGnarLigado = true;
        }
    }

    // recebe uma lista como entrada e uma string como resposta do bot
    public static void responderMsg(List entrada, String resposta) {
        verificaLigar();
        verificaDesligar();
        if(isGnarLigado && entrada.contains(conteudo)) {
            currentEvent.getChannel().sendMessage(resposta).queue();
            isGnarLigado = true;
        }
    }

    // recebe uma lista de entrada e saída e responde com um index aleatório
    public static void responderMsgRandom(List entrada, List resposta) {
        verificaLigar();
        verificaDesligar();
        if(isGnarLigado && entrada.contains(conteudo)) {
            Random aleatorio = new Random();
            Integer numero = aleatorio.nextInt(resposta.size());
            currentEvent.getChannel().sendMessage(resposta.get(numero).toString().replace("<user>", autor)).queue();
        }
    }

    // método para setar as variáveis de liberação de monitoramento para TRUE
    public static void monitorar() {
        if(isGnarLigado && conteudo.contains("!monitorar") && conteudo.contains ("ram")) {
            enviarMsg("Irei monitorar a memoria RAM! Poderia me falar por quantos segundos?");
            liberarMonitoracaoRAM = true;
        }
        else if(isGnarLigado && conteudo.contains("!monitorar") && conteudo.contains("cpu")) {
            enviarMsg("Irei monitorar a CPU! Poderia me falar por quantos segundos?");
            liberarMonitoracaoCPU = true;
        }
    }

    public static void iniciarMonitoramentoRAM() {
        if (isGnarLigado && liberarMonitoracaoRAM) {
            Double memoriaDisponivel = 0.0;

            currentEvent.getChannel().sendMessage("> Iniciando monitoramento da RAM...").queue();
            System.out.println(conteudo);
            Integer contador = 0;
            Integer tempoMonitorar = 0;
            try {
                tempoMonitorar = Integer.parseInt(conteudo);
                while (contador < tempoMonitorar) {
                    Thread.sleep(1000);
                      System.out.println("Iniciando monitoramentooooo");
                //    memoriaDisponivel = memoriaRam.getDisponivel();
                //    System.out.println("teste" + memoriaRam.getDisponivel());
                      currentEvent.getChannel().sendMessage("Voce possui **" + "x" + "** MB de memoria disponivel.").queue();
                      contador += 1;
                }
            } catch (NumberFormatException | InterruptedException numeroInvalidoException) {
                enviarMsg("Voce nao digitou um numero valido.");
            }
            finally {
                liberarMonitoracaoRAM = false;
                currentEvent.getChannel().sendMessage(String.format("Monitoramento finalizado! Duracao: %d segundos :smile:", tempoMonitorar)).queue();
            }
            liberarMonitoracaoRAM = false;
        }
    }

    public static void iniciarMonitoramentoCPU() {
        if (isGnarLigado && liberarMonitoracaoCPU) {
            enviarMsg("> Iniciando monitoramento da CPU...");
            Integer contador = 0;
            Integer tempoMonitorar = 0;
            try {
                tempoMonitorar = Integer.parseInt(conteudo);
                while(contador < tempoMonitorar) {
                    Thread.sleep(1000);
                    String usoCPU = "X";
                    currentEvent.getChannel().sendMessage("Voce esta usando **" + usoCPU + "%** de sua CPU.").queue();
                    contador += 1;
                }
            } catch (NumberFormatException | InterruptedException numeroInvalidoException) {
                enviarMsg("Voce nao digitou um numero valido.");
            } finally {
                liberarMonitoracaoRAM = false;
                currentEvent.getChannel().sendMessage(String.format("Monitoramento finalizado! Duracao: %d segundos :smile:", tempoMonitorar)).queue();
            }
            liberarMonitoracaoCPU = false;
        }
    }

    // método para o bot responder de acordo com a hora do dia, independente se recebe um "bom dia" ou "boa noite"
    public static void responderHorarioRandom(List entrada) {
        if (isGnarLigado && entrada.contains(conteudo)) {
            horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            Random aleatorio = new Random();
            Integer numero = aleatorio.nextInt(respostaDia.size()); // pega um index aleatório na lista de resposta
            if (horaAtual > 0 && horaAtual <= 5) {
                currentEvent.getChannel().sendMessage(respostaMadrugada.get(numero).toString().replace("<user>", autor)).queue();
            } else if (horaAtual > 5 && horaAtual <= 12) {
                currentEvent.getChannel().sendMessage(respostaDia.get(numero).toString().replace("<user>", autor)).queue();
            } else if (horaAtual > 12 && horaAtual <= 17) {
                currentEvent.getChannel().sendMessage(respostaTarde.get(numero).toString().replace("<user>", autor)).queue();
            } else if (horaAtual > 17 && horaAtual <= 23) {
                currentEvent.getChannel().sendMessage(respostaNoite.get(numero).toString().replace("<user>", autor)).queue();
            }
        }
    }

    public static void enviarMsg(String entrada) {
        currentEvent.getChannel().sendMessage(entrada).queue();
    }

    public static void mensagemConsole() {
        try {
            System.out.println("Recebemos uma mensagem de " + Gnar.autor + ": " + Gnar.mensagem );
        } catch (Exception NullPointerException) {
            System.out.println("Erro");
        }
    }

    public static String getMsgCmds() {
        return msgCmds;
    }

}
