package org.likelion._thon.silver_navi.domain.manager.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.manager.service.ManagerService;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSignUpReq;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class ManagerController implements ManagerApi{

    private final ManagerService managerService;

    @Override
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<?>> signup(
            @RequestBody @Valid ManagerSignUpReq managerSignUpReq
    ) {
        managerService.signUp(managerSignUpReq);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.created(null));
    }
}
