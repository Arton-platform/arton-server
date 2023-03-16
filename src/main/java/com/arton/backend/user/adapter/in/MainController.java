package com.arton.backend.user.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import com.arton.backend.user.application.data.MainPageDto;
import com.arton.backend.user.application.port.in.MainPageUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainPageUseCase mainPageUseCase;


    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "메인화면", description = "메인화면에 필요한 데이터를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "데이터 반환 성공",
                    content = @Content(schema = @Schema(implementation = MainPageDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/home")
    public ResponseEntity<ResponseData<MainPageDto>> goHome(@AuthenticationPrincipal UserDetails userDetails, @PageableDefault Pageable pageable) {
        long id = Long.parseLong(userDetails.getUsername());
        MainPageDto mainPage = mainPageUseCase.getMainPage(id, (int)pageable.getOffset(), pageable.getPageSize());
        ResponseData responseData = new ResponseData("SUCCESS", HttpStatus.OK.value(), mainPage);
        return ResponseEntity.ok(responseData);
    }
}
