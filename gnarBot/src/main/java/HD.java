import java.util.concurrent.TimeUnit;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;

public class HD {
    private HWDiskStore[] hd;
    private FileSystem sistemaArquivo;
    private OSFileStore[] sisArray;
    private String hardDiskPrincipal;
    private Double calculo;

    public HD() {
        hd = new SystemInfo().getHardware().getDiskStores();
        sistemaArquivo = new SystemInfo().getOperatingSystem().getFileSystem();
        sisArray = sistemaArquivo.getFileStores();
        calculo = Math.pow(10, 9);
    }

    public String getNomeModelo() {
        String modelHd = "";
        for (HWDiskStore disk : hd) {
            boolean discoNull = disk.getReads() > 0 || disk.getWrites() > 0;
            if (discoNull) {
                modelHd = String.format("%s ", disk.getModel());
            }
        }
        return modelHd.replaceFirst("[(].*?[)]", "");
    }

    public String getQtdDisk() {
        Integer cont = 0;
        for (HWDiskStore disk : hd) {
            boolean discoNull = disk.getReads() > 0 || disk.getWrites() > 0;
            if (discoNull) {
                cont++;
            }
        }
        return String.format("%d Discos disponíveis ", cont);
    }

    public String getSizeDisk() {
        String sizeHds = "";
        for (Integer d = 0; d < hd.length; d++) {
            boolean discoNull = hd[d].getReads() > 0 || hd[d].getWrites() > 0;
            if (discoNull) {
                String size = String.format("%s", FormatUtil.formatBytesDecimal(hd[d].getSize()));
                char[] charArray = size.toCharArray();
                for (char c : charArray) {
                    if (c == ',') {
                        sizeHds += "";
                        break;
                    } else {
                        sizeHds += c;
                    }
                }
            }
        }
        return sizeHds + " GB";
    }

    public String getDiscoAlocado() {
        String discos = "";
        for (OSFileStore d : sisArray) {
            boolean discoNull = "".equals(d.getType());
            if (!discoNull) {
                discos = String.format("%s", d.getName());
            }
        }
        return discos.replaceAll(" Fixo", "");
    }

    public String getTipoSADisco() {
        String tipos = "";
        for (OSFileStore t : sisArray) {
            boolean discoNull = "".equals(t.getType());
            if (!discoNull) {
//                tipos = String.format(" %s Tipo de sistema de arquivos: %s %n", t.getMount(), t.getType());
                tipos = String.format("%s", t.getType());
            }
        }
        return tipos;
    }

    public String getDiscosTotalDisponivel() {
        String livreTotal = "";
        for (OSFileStore d : sisArray) {
            boolean discoNull = "".equals(d.getType());
            if (!discoNull) {
                String livre = FormatUtil.formatBytes(d.getUsableSpace()).replaceAll(" GiB", "");
                char[] charArray = livre.toCharArray();
                for (char c : charArray) {
                    if (c == ',') {
                        livreTotal += "";
                        break;
                    } else {
                        livreTotal += c;
                    }
                }
            }
        }
        return livreTotal;
    }

    public String getDiscosTotalUtilizavel() {
        String utilizavelTotal = "";
        for (OSFileStore d : sisArray) {
            boolean discoNull = "".equals(d.getType());
            if (!discoNull) {
                String utilizavel = FormatUtil.formatBytes(d.getTotalSpace()).replaceAll(" GiB", "");
                char[] charArray = utilizavel.toCharArray();
                for (char c : charArray) {
                    if (c == ',') {
                        utilizavelTotal += "";
                        break;
                    } else {
                        utilizavelTotal += c;
                    }
                }
            }
        }
        return utilizavelTotal;
    }

    public String getPorcentagemDisponivel() {
        String desempenho = "";
        for (OSFileStore p : sisArray) {
            boolean discoNull = "".equals(p.getType());
            long utilizavel = p.getUsableSpace();
            if (utilizavel == 0 && !discoNull) {
                Double porcentagem = (100d * utilizavel) / p.getTotalSpace();
                desempenho = String.format("%s %.0f%% %n", p.getMount(), porcentagem);
            }
        }
        return desempenho;
    }

    public String getPorcentagemOcupada() {
        String desempenho = "";
        for (OSFileStore p : sisArray) {
            boolean discoNull = "".equals(p.getType());
            long utilizavel = p.getUsableSpace();
            if (utilizavel != 0 && !discoNull) {
                Double dispo = (100d * utilizavel) / p.getTotalSpace();
                Double porcentagem = 100d - dispo;
                desempenho = String.format("%.0f", porcentagem);
            }
        }
        return desempenho;
    }

    public Double getOcupadoNew() {
        Double desempenho = 0.0;
        for (OSFileStore p : sisArray) {
            boolean discoNull = "".equals(p.getType());
            long utilizavel = p.getUsableSpace();
            if (utilizavel != 0 && !discoNull) {
                Double dispo = (100d * utilizavel) / p.getTotalSpace();
                Double porcentagem = 100d - dispo;
                desempenho = porcentagem;
            }
        }
        return desempenho;
    }

    public Double getDisponivelNew() {
        Double totalDisponivel = Double.parseDouble(getDiscosTotalDisponivel());
        Double totalOcupado = getOcupadoNew();
        Double total = totalDisponivel - ((totalDisponivel * totalOcupado) / 100);
        return total;
    }

    public String getFilesVolume() {
        String volume = "";
        for (OSFileStore v : sisArray) {
            boolean discoNull = "".equals(v.getType());
            if (!discoNull) {
                volume = String.format(" %s %s\n", v.getMount(), v.getVolume()).replaceAll("\\\\", "").replaceAll("\\?", "");
            }
        }
        return volume;
    }

    public String getTempoTransferencia(HWDiskStore[] d) {
        Integer segFormat = 0;
        for (HWDiskStore disk : d) {
            boolean discoNull = disk.getReads() > 0 || disk.getWrites() > 0;
            if (discoNull) {
                Long seconds = TimeUnit.MILLISECONDS.toSeconds(disk.getTransferTime()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(disk.getTransferTime()));
                segFormat = seconds.intValue();
            }
        }
        return String.format("%d", segFormat);
    }

}
