import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import oshi.SystemInfo;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Gnar extends ListenerAdapter {

    static Boolean isGnarLigado = true;
    static MessageReceivedEvent currentEvent;
    static String autor,mensagem,conteudo;
    static Boolean liberarMonitoracaoRAM = false;
    static Boolean liberarMonitoracaoCPU = false;
    static Boolean liberarMonitoracaoHD = false;
    static SystemInfo sistema = new SystemInfo();
    static long memoriaTotal = ((sistema.getHardware().getMemory().getTotal() / 1024) / 1024);
    static Integer horaAtual;
    static List<String> respostaDia = Arrays.asList("Bom dia, <user>! :smile:", "Eai <user>, bom dia! :blush: ","bom dia");
    static List<String> respostaTarde = Arrays.asList("Boa tarde, <user>! :smile:", "Eai <user>, boa tarde! :blush: ","boa tarde");
    static List<String> respostaNoite = Arrays.asList("Boa noite, <user>! :smile:", "Eai <user>, boa noite! :blush: ","boa noite");
    static List<String> respostaMadrugada = Arrays.asList("Boa madrugada, <user>! :sleeping:", "Eai <user>, boa madrugada! :blush:","boa madruga");

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
                currentEvent.getChannel().sendMessage("#PartiuTrabalhar :smile:").queue();
                isGnarLigado = true;
            }
        return;
        }
    }

    private static void verificaDesligar() {
        if(isGnarLigado && conteudo.contains("!desligar")) {
            currentEvent.getChannel().sendMessage("#PartiuDormir :sleeping: ").queue();
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
        if(isGnarLigado && conteudo.contains(entrada) && conteudo.contains(hardware) && liberarMonitoracaoRAM == false) {
            enviarMsg("Irei monitorar a memoria RAM! Poderia me falar por quantos segundos?");
            liberarMonitoracaoRAM = true;
        }
    }

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
            currentEvent.getChannel().sendMessage("> Iniciando monitoramento da RAM...").queue();
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
                enviarMsg("Voce nao digitou um numero valido.");
            } finally {
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
                    double usoCPU = (sistema.getHardware().getProcessor().getSystemCpuLoad() * 100);
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

//    public static void responderHorario(List entrada) {
//        if (isGnarLigado && entrada.contains(conteudo)) {
//            horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//            if (horaAtual > 0 && horaAtual <= 5) {
//                currentEvent.getChannel().sendMessage(String.format("Boa madrugada, %s ! :sleeping: ", autor)).queue();
//            } else if (horaAtual > 5 && horaAtual <= 12) {
//                currentEvent.getChannel().sendMessage(String.format("Bom dia, %s! :smile:", autor)).queue();
//            } else if (horaAtual > 12 && horaAtual <= 17) {
//                currentEvent.getChannel().sendMessage(String.format("Boa tarde, %s! :smile:", autor)).queue();
//            } else if (horaAtual > 17 && horaAtual <= 23) {
//                currentEvent.getChannel().sendMessage(String.format("Boa noite, %s! :smile:", autor)).queue();
//            }
//        }
//    }

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

}
