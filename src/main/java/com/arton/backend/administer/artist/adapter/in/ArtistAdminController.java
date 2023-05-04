package com.arton.backend.administer.artist.adapter.in;

import com.arton.backend.administer.artist.application.data.ArtistAdminCreateDTO;
import com.arton.backend.administer.artist.application.data.ArtistAdminEditDTO;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminDeleteUseCase;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminEditUseCase;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminSaveUseCase;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminUseCase;
import com.arton.backend.artist.application.port.in.ArtistUseCase;
import com.arton.backend.artist.domain.Artist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ArtistAdminController {
    private final ArtistAdminSaveUseCase artistAdminSaveUseCase;
    private final ArtistAdminUseCase artistAdminUseCase;
    private final ArtistAdminEditUseCase artistAdminEditUseCase;
    private final ArtistAdminDeleteUseCase artistAdminDeleteUseCase;
    private final ArtistUseCase artistUseCase;

    @GetMapping("/web/artist/add")
    public String addArtist(Model model) {
        return "artist/createForm";
    }

    @PostMapping("/web/artist/add")
    public String postArtist(@Valid ArtistAdminCreateDTO artistAdminCreateDTO) {
        artistAdminSaveUseCase.addArtist(artistAdminCreateDTO);
        return "redirect:/web/artist";
    }

    @GetMapping("/web/artist")
    public String goArtistHome(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Artist> artists = artistUseCase.findAll(pageable);
        model.addAttribute("artists", artists);
        return "artist/index";
    }

    @GetMapping("/web/artist/{id}")
    public String goArtistEditHome(Model model, @PathVariable(name = "id") Long id) {
        ArtistAdminEditDTO editDto = artistAdminUseCase.getArtistEditDto(id);
        model.addAttribute("editDto", editDto);
        return "artist/editForm";
    }

    @PostMapping("/web/artist/{id}")
    public String editArtist(Model model, @PathVariable(name = "id") Long id, ArtistAdminEditDTO editDto) {
        artistAdminEditUseCase.editArtist(id, editDto);
        return "redirect:/web/artist";
    }

    // 공연 삭제
    @PostMapping("/web/artist/revoke")
    public String revokePerformance(@RequestParam(required = true, name = "idx") Long idx) {
        artistAdminDeleteUseCase.deleteArtist(idx);
        return "redirect:/web/artist";
    }
}
