package com.arton.backend.terms.adapter.in;

import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.terms.application.data.TermsShowDto;
import com.arton.backend.terms.application.port.in.TermsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/terms")
@RequiredArgsConstructor
public class TermsController {
    private final TermsUseCase termsService;

    /**
     * 약관 리스트 보여주기
     * 선택은 url webview로 넘겨주자.
     * @return
     */
    @GetMapping
    @Operation(summary = "약관 페이지", description = "약관 페이지 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "약관 정보 반환 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TermsShowDto.class))))})
    public ResponseEntity<ResponseData<List<TermsShowDto>>> showTermList() {
        List<TermsShowDto> response = termsService.getTerms();
        ResponseData responseData = new ResponseData("SUCCESS", HttpStatus.OK.value(), response);
        return ResponseEntity.ok(responseData);
    }

//    @GetMapping
//    @Operation(summary = "약관 페이지", description = "약관 페이지 정보를 반환합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "약관 정보 반환 성공",
//                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TermsShowDtoV2.class))))})
//    public ResponseEntity<ResponseData<List<TermsShowDtoV2>>> showTermListV2() {
//        List<TermsShowDtoV2> response = termsService.getTermsV2();
//        ResponseData responseData = new ResponseData("SUCCESS", HttpStatus.OK.value(), response);
//        return ResponseEntity.ok(responseData);
//    }

}