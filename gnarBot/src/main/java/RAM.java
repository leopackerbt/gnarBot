import java.util.ArrayList;
import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;
import oshi.util.FormatUtil;

public class RAM {
    private GlobalMemory ram;
    private PhysicalMemory[] memoriaFisica;
    private Double calculo;
    private Double total = 0.0;

    public RAM() {
        ram = new SystemInfo().getHardware().getMemory();
        memoriaFisica = ram.getPhysicalMemory();
        calculo = Math.pow(10, 9);
    }

    public String getTipoMemoria() {
        return memoriaFisica[0].getMemoryType();
    }

    public String getNumerosDePentes() {
        return String.valueOf(memoriaFisica.length);
    }

    public String getTotalMemoria() {
        Double total = 0.0;
        for (PhysicalMemory p : memoriaFisica) {
            String capacity = String.format("%.0f", p.getCapacity() / calculo).replaceAll("[^0-9]", "");
            total += Integer.parseInt(capacity);
        }
        return String.format("%.1f GB", total);
    }
    public Double getVirtualMemoriaTotal(){
        Double total = (double)ram.getVirtualMemory().getSwapTotal();
        /*
            Os arquivos de paginação nada mais são do que um espaço no disco rígido reservado para ajudar a
            armazenar os dados da memória RAM quando ela está cheia. É uma forma de estender a quantidade de
            memória para os dados temporários utilizados pelos aplicativos em execução sem que você precise
            fazer um upgrade de hardware.
        */
        return total; //String.format("%.1f GB",total/ calculo);
    }

    public String getUtilizavel(){
        return FormatUtil.formatBytes(ram.getTotal()).replaceAll("i", "");
    }
    public Double getDisponivel() {
        return (ram.getTotal() - ram.getAvailable() / calculo);
    }

    public String getClockSpeed() {
        Double clockSpeed = (double) memoriaFisica[0].getClockSpeed();
        return String.format("%.1f GHz", clockSpeed / calculo);
    }

    public String getDesempenhoMemoria() {
        Double ramDesempenho = (double) (ram.getTotal() - ram.getAvailable());
        return String.format("%.0f%%", (ramDesempenho * 100.0) / ram.getTotal());
    }
}
