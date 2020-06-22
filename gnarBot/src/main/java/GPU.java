import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.components.Gpu;
import com.profesorfalken.jsensors.model.sensors.Fan;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.util.FormatUtil;

public class GPU extends Initialize {

    private GraphicsCard[] gpu = new SystemInfo().getHardware().getGraphicsCards();

    public GPU() {
        gpu = new SystemInfo().getHardware().getGraphicsCards();
    }

    public String getNomePlaca() {
        String nome = "";
        for (Integer d = 0; d < gpu.length; d++) {
            nome = gpu[0].getName();
        }
        return nome;
    }


    public String getRAM() {
        String ram = "";
        for (GraphicsCard graphicsCard : gpu) {
            Double r =  Double.valueOf(FormatUtil.formatBytes(graphicsCard.getVRam()).replaceAll(" GiB", "").replaceAll(",", "."));
            ram = String.format("%.2f", Math.ceil(r));
        }
        return ram;
    }

    public String getDesempenho() {
        String desempenho = "";
        for (Integer d = 0; d < gpu.length; d++) {
            desempenho = String.format("%d",gpu[0].getVRam() % 1024);
        }
        return desempenho;
    }

    public String getVersaoGPU() {
        String versao = "";
//        for (GraphicsCard graphicsCard : gpu) {
        for (Integer d = 0; d < gpu.length; d++) {
            versao =  gpu[0].getVersionInfo().replaceAll("DriverVersion=","");
        }

        return versao;
    }

    public void sensores() {
        Components components = JSensors.get.components();

        //Lista de cpus
        List<Cpu> cpus = components.cpus;

        //Lista de gpus
        List<Gpu> gpus = components.gpus;

        if (gpus != null) {
            for (final Gpu gpu : gpus) {
                //Print temperatures
                List<Temperature> temps = gpu.sensors.temperatures;
                gpu.sensors.temperatures.forEach((temperatures) -> {
                    System.out.println("Temperatura: " + temperatures.value);
                });
//                    for (final Temperature temp : temps) {
//                        System.out.println(temp.name + ": " + temp.value + " C");

                //Print fan speed
                List<Fan> fans = gpu.sensors.fans;
                gpu.sensors.fans.forEach((fan) -> {
                    System.out.println(fan.value);
                });
            }
        }
    }

}
