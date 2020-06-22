import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.components.Gpu;
import java.util.List;

public abstract class Initialize {
    protected Components components = JSensors.get.components();
    protected List<Gpu> gpus = components.gpus;
    //protected List<Fan> fans = cpu.sensors.fans;
    protected List<Cpu> cpus = components.cpus;
    //protected List<Temperature> temps = cpu.sensors.temperatures;
}