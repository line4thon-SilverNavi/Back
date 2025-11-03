package org.likelion._thon.silver_navi.domain.consult.web.dto;

import java.util.List;

public record ConsultHistorySummaryRes(
        long totalCount,
        long waitingCount,
        long confirmedCount,
        long completedCount,
        List<ConsultHistoryRes> consults
) {}
