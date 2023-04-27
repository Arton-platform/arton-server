package com.arton.backend.faq.adapter.in.web;

import com.arton.backend.faq.application.data.FAQListResponseDTO;
import com.arton.backend.faq.application.data.FAQResponseDTO;
import com.arton.backend.faq.application.port.in.FAQUseCase;
import com.arton.backend.faq.domain.FAQ;
import com.arton.backend.follow.applicaion.data.UserFollowDto;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
@Slf4j
public class FAQController {
    // port.in UseCase 를 바라보고 사용한다.

    private final FAQUseCase faqUseCase;

    @Operation(summary = "FAQ 리스트 가져오기", description = "FAQ 리스트 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ 리스트 정보 반환 성공",
                    content = @Content( array = @ArraySchema( schema = @Schema( implementation = FAQListResponseDTO.class))))})
    @GetMapping("/list")
    public ResponseEntity<ResponseData<List<FAQListResponseDTO>>> faqList(){
        log.info("[FAQ] {}","faqList");
        ResponseData<List<FAQListResponseDTO>> response = new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                faqUseCase.faqListV2()
        );
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "특정 FAQ 보기", description = "특정 FAQ 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로워 정보 반환 성공",
                    content = @Content( schema = @Schema(implementation = FAQResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "서버오류",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<FAQResponseDTO>> faq(@PathVariable("id") Long id){
        log.info("[FAQ]: faqId : {}", id);
        ResponseData<FAQResponseDTO> response = new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                faqUseCase.getFaqByIdV2(id)
        );
        return ResponseEntity.ok().body(response);
    }

}
