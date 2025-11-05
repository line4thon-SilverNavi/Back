package org.likelion._thon.silver_navi.domain.program.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AttendanceUpdateReq {

    List<Long> applicantIds;
}
