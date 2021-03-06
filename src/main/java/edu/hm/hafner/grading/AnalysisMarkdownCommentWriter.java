package edu.hm.hafner.grading;

/**
 * Renders the static analysis results in Markdown.
 *
 * @author Tobias Effner
 * @author Ullrich Hafner
 */
public class AnalysisMarkdownCommentWriter {
    private static final String ANALYSIS_HEADER = "## :warning: Static Analysis Warnings: ";

    /**
     * Renders the static analysis results in Markdown.
     *
     * @param score
     *         the aggregated score
     *
     * @return returns formatted string
     */
    public String create(final AggregatedScore score) {
        if (!score.getAnalysisConfiguration().isEnabled()) {
            return ANALYSIS_HEADER + "not configured :warning:\n";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ANALYSIS_HEADER)
                .append(score.getAnalysisAchieved())
                .append(" / ")
                .append(score.getAnalysisConfiguration().getMaxScore())
                .append(" :warning:\n");
        stringBuilder.append(formatColumns(
                new String[] {"Name", "Errors", "Warning High", "Warning Normal", "Warning Low", "Impact"}));
        stringBuilder.append(formatColumns(new String[] {":-:", ":-:", ":-:", ":-:", ":-:", ":-:"}));
        score.getAnalysisScores().forEach(analysisScore -> stringBuilder.append(formatColumns(new String[] {
                analysisScore.getName(),
                String.valueOf(analysisScore.getErrorsSize()),
                String.valueOf(analysisScore.getHighSeveritySize()),
                String.valueOf(analysisScore.getNormalSeveritySize()),
                String.valueOf(analysisScore.getLowSeveritySize()),
                String.valueOf(analysisScore.getTotalImpact())})));
        return stringBuilder.toString();
    }

    private String formatColumns(final Object[] columns) {
        String format = "|%1$-10s|%2$-10s|%3$-10s|%4$-10s|%5$-10s|%6$-10s|\n";
        return String.format(format, columns);
    }
}
