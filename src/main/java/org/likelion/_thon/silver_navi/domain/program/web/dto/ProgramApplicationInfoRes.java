package org.likelion._thon.silver_navi.domain.program.web.dto;

import java.util.List;

public record ProgramApplicationInfoRes(
        ProgramApplicationSummaryRes summary,
        List<ProgramApplicantsRes> applicants
) {
}
