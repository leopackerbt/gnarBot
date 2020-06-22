import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import oshi.SystemInfo;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class Main extends ListenerAdapter {
    Gnar gnar;
    List<String> saudacoesGnar;
    List<String> entradaRandom;
    List<String> saudacoesGnarResposta;
    List<String> respostaRandom;

    public Main() {
        saudacoesGnar = Arrays.asList("bom dia","boa tarde","boa noite","b dia","b tarde","b noite");
        entradaRandom = Arrays.asList("entrada", "entrada2");
        saudacoesGnarResposta = Arrays.asList("Ola <user>, em que posso ajudar? ","E ai <user>! Precisa de ajuda?");
        respostaRandom = Arrays.asList("resposta1", "resposta2", "resposta3");
        gnar = new Gnar();
    }

    public static void main(String[] args) throws LoginException {
        // String token = JOptionPane.showInputDialog("Insira o token do seu bot"); //"Njg1NDg0MjExMzEwMDM0OTQ1.XmfSag.s1ex-DVJiHpNhNlYdV_vMLkSaNc";
        //token = (token.isEmpty()) ? "Njg1NDg0MjExMzEwMDM0OTQ1.XmfSag.s1ex-DVJiHpNhNlYdV_vMLkSaNc": token;
        Login.main();

        if (Login.token.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Token vazio");
        }

    }

    public void onMessageReceived(MessageReceivedEvent event) {
        gnar.setEvent(event);
        gnar.mensagemConsole();

        if(event.getAuthor().isBot()) {
            return;
        }

        gnar.trazerInfos();
        gnar.iniciarMonitoramentoRAM();
        gnar.iniciarMonitoramentoCPU();
        gnar.iniciarMonitoramentoHD();
        gnar.iniciarMonitoramentoGPU();
        gnar.monitorar();
        gnar.responderHorarioRandom(gnar.getSaudacoesHora());
        gnar.responderMsg("!comandos", gnar.getMsgCmds());

       // Gnar.responderMsg(saudacoesGnar,"É nois");
       // Gnar.responderMsgRandom(saudacoesGnar, saudacoesGnarResposta);
    }

}
