package edu.hm.hafner.grading;

import java.util.List;
import java.util.stream.Collectors;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.parser.violations.PitAdapter;

/**
 * Provides PIT mutation coverage scores by converting corresponding {@link Report} instances.
 *
 * @author Ullrich Hafner
 */
public class PitReportSupplier extends PitSupplier {
    private final List<Report> pitReports;

    /**
     * Creates a new instance of {@link PitReportSupplier}.
     *
     * @param pitReports
     *         the PIT mutation reports
     */
    public PitReportSupplier(final List<Report> pitReports) {
        this.pitReports = pitReports;
    }

    @Override
    protected List<PitScore> createScores(final PitConfiguration configuration) {
        return pitReports.stream()
                .map(report -> createPitScore(configuration, report))
                .collect(Collectors.toList());

    }

    private PitScore createPitScore(final PitConfiguration configuration, final Report report) {
        return new PitScore.PitScoreBuilder()
                .withConfiguration(configuration)
                .withDisplayName("PIT")
                .withTotalMutations(report.getCounter(PitAdapter.TOTAL_MUTATIONS))
                .withUndetectedMutations(report.getCounter(PitAdapter.SURVIVED_MUTATIONS))
                .build();
    }
}
