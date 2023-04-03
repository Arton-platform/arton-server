package com.arton.backend.announcement.adapter.in.web;

import com.arton.backend.announcement.application.port.in.SelectAllUseCase;
import com.arton.backend.announcement.application.port.in.SelectOneUseCase;
import com.arton.backend.announcement.domain.Announcement;
import com.arton.backend.faq.domain.FAQ;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/announcement")
@Slf4j
public class AnnounceController {

    private final SelectAllUseCase selectAllUseCase;
    private final SelectOneUseCase selectOneUseCase;

    @Operation(summary = "공지 리스트 가져오기", description = "공지 리스트 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ 리스트 정보 반환 성공",
                    content = @Content( array = @ArraySchema( schema = @Schema( implementation = Announcement.class))))})
    @GetMapping("/list")
    public ResponseEntity<ResponseData<List<Announcement>>> announcementList(){
        log.info("[Announcement] : announcementList {}", "announcementList");
        ResponseData<List<Announcement>> response = new ResponseData(
          "SUCCESS",
                HttpStatus.OK.value(),
                selectAllUseCase.announcementList()
        );
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "특정 공지 가져오기", description = "특정 공지 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ 리스트 정보 반환 성공",
                    content = @Content( array = @ArraySchema( schema = @Schema( implementation = Announcement.class)))),
    @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<List<Announcement>>> loadAnnouncementDetail(@PathVariable("id") Long id){
        log.info("[Announcement] : loadAnnouncementDetail - announcementId : {}", id);
        ResponseData<List<Announcement>> response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                selectOneUseCase.loadAnnouncementDetail(id)
        );
        return ResponseEntity.ok().body(response);
    }
}
