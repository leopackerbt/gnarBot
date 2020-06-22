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
    static HD hd;
    static GPU gpu;
    static Boolean isGnarLigado = true;
    static MessageReceivedEvent currentEvent;
    static String autor,mensagem,conteudo;
    static Boolean liberarMonitoracaoRAM = false;
    static Boolean liberarMonitoracaoCPU = false;
    static Boolean liberarMonitoracaoHD = false;
    static Boolean liberarMonitoracaoGPU = false;
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
            "**!monitorar** [ram/cpu/hd/gpu] - Inicia um monitoramento de hardware (1 por vez)\n" +
            "**!infos** [ram/cpu/hd/gpu] - Mostra algumas informacoes extras de hardware";
    private static List<String> saudacoesHora;

    public Gnar() {
        cpu = new CPU();
        memoriaRam = new RAM();
        hd = new HD();
        gpu = new GPU();
        saudacoesHora = Arrays.asList("bom dia","boa tarde","boa noite","b dia","b tarde","b noite");
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

    public static void trazerInfos() {
        if(isGnarLigado && conteudo.contains("!infos") && conteudo.contains("ram")) {
            enviarMsg(String.format("> Infos sobre a Memoria RAM:\n" +
                    "- Total: %s\n" +
                    "- Disponivel: %s\n" +
                    "- Velocidade Clock: %s", memoriaRam.getTotalMemoria(), memoriaRam.getDisponivel(), memoriaRam.getClockSpeed()));
        }
        else if(isGnarLigado && conteudo.contains("!infos") && conteudo.contains("cpu")) {
            enviarMsg(String.format("> Infos sobre o Processador:\n" +
                    "- Nome: %s\n" +
                    "- Processos: %s\n" +
                    "- Threads: %s", cpu.getProcessador(), cpu.getProcesses(), cpu.getThreads()));
        }
        else if(isGnarLigado && conteudo.contains("!infos") && conteudo.contains("hd")) {
            enviarMsg(String.format("> Infos sobre o HD:\n" +
                    "- Alocado em: %s\n" +
                    "- Tamanho total: %s GB\n" +
                    "- Disponivel: %.1f GB", hd.getDiscoAlocado(), hd.getDiscosTotalDisponivel(), hd.getDisponivelNew()));
        }
        else if(isGnarLigado && conteudo.contains("!infos") && conteudo.contains("gpu")) {
            enviarMsg(String.format("> Infos sobre a Placa de Video:\n" +
                    "- Nome: %s\n" +
                    "- Versao: %s\n" +
                    "- Velocidade Clock: %s GB", gpu.getNomePlaca(), gpu.getVersaoGPU(), gpu.getRAM()));
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
        else if(isGnarLigado && conteudo.contains("!monitorar") && conteudo.contains("hd")) {
            enviarMsg("Irei monitorar o HD! Poderia me falar por quantos segundos?");
            liberarMonitoracaoHD = true;
        }
        else if(isGnarLigado && conteudo.contains("!monitorar") && conteudo.contains("gpu")) {
            enviarMsg("Irei monitorar a GPU! Poderia me falar por quantos segundos?");
            liberarMonitoracaoGPU = true;
        }
    }

    public static void iniciarMonitoramentoRAM() {
        if (isGnarLigado && liberarMonitoracaoRAM) {
            String memoriaDisponivel = "";

            currentEvent.getChannel().sendMessage("> Iniciando monitoramento da RAM...").queue();
            System.out.println(conteudo);
            Integer contador = 0;
            Integer tempoMonitorar = 0;
            try {
                tempoMonitorar = Integer.parseInt(conteudo);
                while (contador < tempoMonitorar) {
                    Thread.sleep(1000);
                      System.out.println("Iniciando monitoramento");
                      memoriaDisponivel = memoriaRam.getDesempenhoMemoria();
                      currentEvent.getChannel().sendMessage(String.format("Voce esta usando **%s** de sua memoria.", memoriaDisponivel)).queue();
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
            String usoCPU = "";

            enviarMsg("> Iniciando monitoramento da CPU...");
            Integer contador = 0;
            Integer tempoMonitorar = 0;
            try {
                tempoMonitorar = Integer.parseInt(conteudo);
                while(contador < tempoMonitorar) {
                    Thread.sleep(1000);
                    usoCPU = cpu.getDesempenho();
                    currentEvent.getChannel().sendMessage("Voce esta usando **" + usoCPU + "** de sua CPU.").queue();
                    contador += 1;
                }
            } catch (NumberFormatException | InterruptedException numeroInvalidoException) {
                enviarMsg("Voce nao digitou um numero valido.");
            } finally {
                liberarMonitoracaoCPU = false;
                currentEvent.getChannel().sendMessage(String.format("Monitoramento finalizado! Duracao: %d segundos :smile:", tempoMonitorar)).queue();
            }
            liberarMonitoracaoCPU = false;
        }
    }

    public static void iniciarMonitoramentoHD() {
        if (isGnarLigado && liberarMonitoracaoHD) {
            String usoHD = "";

            enviarMsg("> Iniciando monitoramento do HD...");
            Integer contador = 0;
            Integer tempoMonitorar = 0;
            try {
                tempoMonitorar = Integer.parseInt(conteudo);
                while(contador < tempoMonitorar) {
                    Thread.sleep(1000);
                    usoHD = hd.getPorcentagemOcupada();
                    currentEvent.getChannel().sendMessage("Voce esta usando **" + usoHD + "** de seu HD.").queue();
                    contador += 1;
                }
            } catch (NumberFormatException | InterruptedException numeroInvalidoException) {
                enviarMsg("Voce nao digitou um numero valido.");
            } finally {
                liberarMonitoracaoHD = false;
                currentEvent.getChannel().sendMessage(String.format("Monitoramento finalizado! Duracao: %d segundos :smile:", tempoMonitorar)).queue();
            }
            liberarMonitoracaoHD = false;
        }
    }

    public static void iniciarMonitoramentoGPU() {
        if (isGnarLigado && liberarMonitoracaoGPU) {
            String usoGPU = "";

            enviarMsg("> Iniciando monitoramento da GPU...");
            Integer contador = 0;
            Integer tempoMonitorar = 0;
            try {
                tempoMonitorar = Integer.parseInt(conteudo);
                while(contador < tempoMonitorar) {
                    Thread.sleep(1000);
                    usoGPU = gpu.getDesempenho();
                    currentEvent.getChannel().sendMessage("Voce esta usando **" + usoGPU + "%** de sua GPU.").queue();
                    contador += 1;
                }
            } catch (NumberFormatException | InterruptedException numeroInvalidoException) {
                enviarMsg("Voce nao digitou um numero valido.");
            } finally {
                liberarMonitoracaoGPU = false;
                currentEvent.getChannel().sendMessage(String.format("Monitoramento finalizado! Duracao: %d segundos :smile:", tempoMonitorar)).queue();
            }
            liberarMonitoracaoGPU = false;
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

    public static List getSaudacoesHora() {
        return saudacoesHora;
    }

}
