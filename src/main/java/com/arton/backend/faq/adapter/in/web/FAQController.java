package com.arton.backend.faq.adapter.in.web;

import com.arton.backend.faq.application.data.FAQCreateDTO;
import com.arton.backend.faq.application.data.FAQListResponseDTO;
import com.arton.backend.faq.application.data.FAQResponseDTO;
import com.arton.backend.faq.application.port.in.FAQCreateUseCase;
import com.arton.backend.faq.application.port.in.FAQDeleteUseCase;
import com.arton.backend.faq.application.port.in.FAQUseCase;
import com.arton.backend.faq.domain.FAQ;
import com.arton.backend.follow.applicaion.data.UserFollowDto;
import com.arton.backend.infra.shared.common.CommonResponse;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
@Slf4j
public class FAQController {
    // port.in UseCase 를 바라보고 사용한다.
    private final FAQUseCase faqUseCase;
    private final FAQCreateUseCase faqCreateUseCase;
    private final FAQDeleteUseCase faqDeleteUseCase;

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
            @ApiResponse(responseCode = "200", description = "FAQ 정보 반환 성공",
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

    @Operation(summary = "FAQ 등록", description = "FAQ를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공",
                    content = @Content( schema = @Schema(implementation = FAQResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없는 유저",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버오류",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/")
    public ResponseEntity<CommonResponse> add(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody FAQCreateDTO faqCreateDTO){
        log.info("[FAQCreateDTO]: {}", faqCreateDTO);
        long userId = Long.parseLong(userDetails.getUsername());
        faqCreateUseCase.createFaq(userId, faqCreateDTO);
        CommonResponse response = CommonResponse.builder().message("문의사항 등록이 성공적으로 완료되었습니다.").status(200).build();
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "FAQ 삭제", description = "FAQ를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content( schema = @Schema(implementation = FAQResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없는 유저",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버오류",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{faqId}")
    public ResponseEntity<CommonResponse> delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "faqId") long faqId){
        log.info("[FAQ Delete ID]: {}", faqId);
        long userId = Long.parseLong(userDetails.getUsername());
        faqDeleteUseCase.deleteFaq(userId, faqId);
        CommonResponse response = CommonResponse.builder().message("삭제를 성공하였습니다.").status(200).build();
        return ResponseEntity.ok().body(response);
    }

}
