package cz.muni.fi.pv168.addresses;

import java.util.stream.Collectors;

class PerformanceTestReport {

    private final String configurationDescription;
    private final double loadingTime;
    private final double totalTime;
    private final double oneRecordAvgTime;
    private final long heapSizeInMiB;
    private final int availableProcessors;

    PerformanceTestReport(
            String configurationDescription,
            double loadingTime,
            double totalTime,
            double oneRecordAvgTime,
            long heapSizeInMiB,
            int availableProcessors) {

        this.configurationDescription = configurationDescription;
        this.loadingTime = loadingTime;
        this.totalTime = totalTime;
        this.oneRecordAvgTime = oneRecordAvgTime;
        this.heapSizeInMiB = heapSizeInMiB;
        this.availableProcessors = availableProcessors;
    }

    private static String escapeHtml(String str) {
        return str.codePoints().mapToObj(c -> c > 127 || "\"'<>&".indexOf(c) != -1 ?
                "&#" + c + ";" : new String(Character.toChars(c)))
                .collect(Collectors.joining());
    }

    @Override
    public String toString() {
        return String.format("%s%n" +
                        "Data loading:                   %,10.3f ms%n" +
                        "Total search time:              %,10.3f ms%n" +
                        "Average time per single search: %,10.3f ms%n" +
                        "Current heap size:              %,6d MiB%n" +
                        "CPU count:                      %,6d%n",
                configurationDescription, loadingTime, totalTime, oneRecordAvgTime, heapSizeInMiB, availableProcessors);
    }

    public String toHtmlString() {
        return String.format("<html>" +
                        "<h2>%s</h2>" +
                        "<table>" +
                        "<tr><th align=\"left\">Data loading:</th><td align=\"right\">%,.3f</td><td>ms</td></tr>" +
                        "<tr><th align=\"left\">Total search time:</th><td align=\"right\">%,10.3f</td><td>ms</td></tr>" +
                        "<tr><th align=\"left\">Average time per single search:</th><td align=\"right\">%,10.3f</td><td>ms</td></tr>" +
                        "<tr><th align=\"left\">Current heap size:</th><td align=\"right\">%,d</td><td>MiB</td></tr>" +
                        "<tr><th align=\"left\">CPU count:</th><td align=\"right\">%,d</td><td></td></tr>" +
                        "</table></html>",
                escapeHtml(configurationDescription), loadingTime, totalTime, oneRecordAvgTime, heapSizeInMiB, availableProcessors);
    }
}
