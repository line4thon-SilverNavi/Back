package org.likelion._thon.silver_navi.domain.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.bookmark.entity.ProgramBookmark;
import org.likelion._thon.silver_navi.domain.bookmark.repository.ProgramBookmarkRepository;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.program.exception.ProgramNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramBookmarkServiceImpl implements ProgramBookmarkService {
    private final ProgramBookmarkRepository programBookmarkRepository;
    private final ProgramRepository programRepository;


    @Override
    @Transactional
    public boolean toggle(User user, Long contentId) {
        Optional<ProgramBookmark> foundBookmark =
                programBookmarkRepository.findByUser_IdAndProgram_Id(user.getId(), contentId);

        // 좋아요가 눌려있는 경우
        if (foundBookmark.isPresent()) {
            programBookmarkRepository.delete(foundBookmark.get());
            return false; // 북마크 OFF
        }

        var program = programRepository.findById(contentId)
                .orElseThrow(ProgramNotFoundException::new);

        ProgramBookmark bookmark = ProgramBookmark.create(user, program);

        programBookmarkRepository.save(bookmark);
        return true; // 북마크 ON
    }
}
