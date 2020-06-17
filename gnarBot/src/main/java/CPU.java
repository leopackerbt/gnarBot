import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

public class CPU {
    private CentralProcessor cpu;
    private Sensors sensors;
    private OperatingSystem os;
    private Double calculo;

    public CPU() {
        cpu = new SystemInfo().getHardware().getProcessor();
        sensors = new SystemInfo().getHardware().getSensors();
        os = new SystemInfo().getOperatingSystem();
        calculo = Math.pow(10, 9);
    }

    public String getClockSpeed() {
        Double clockSpeed = (double) cpu.getProcessorIdentifier().getVendorFreq();
        return String.format("%.2f GHz", clockSpeed / calculo);
    }

    public String getClockSpeedAtual() {

        String atualGhz = "";
        long[] currentFreq = cpu.getCurrentFreq();

        for(Integer i =0 ; i< currentFreq.length; i++){
            long ghz = currentFreq[ currentFreq.length-1];
            atualGhz = String.format("%s", FormatUtil.formatHertz(ghz));
        }
        return atualGhz;
    }

    public String getProcessador() {
        return cpu.getProcessorIdentifier().getName().replaceAll("\\s+", " ").replaceAll("@", "");
    }

    public String getTemperature(){
        return String.format("%.0f ºC", sensors.getCpuTemperature());
    }

    public String getThreads() {
        return String.valueOf(os.getThreadCount());
    }

    public String getProcesses() {
        return String.valueOf(os.getProcessCount());
    }

    public String getDesempenho() {

        long[] prevTicks = cpu.getSystemCpuLoadTicks();
        // Wait a second...
        Util.sleep(1000);
        long[] ticks = cpu.getSystemCpuLoadTicks();

        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;

        return String.format("%.0f%%", 100d * user / totalCpu);
    }

}
