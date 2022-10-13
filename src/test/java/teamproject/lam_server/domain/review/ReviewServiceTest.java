package teamproject.lam_server.domain.review;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.review.repository.ReviewTagRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Transactional
@ActiveProfiles({"local", "oauth2"})
@Rollback
@Slf4j
public class ReviewServiceTest {

    @Autowired
    ReviewTagRepository reviewTagRepository;

    @Test
    public void test1() {
        Set<String> tags = new HashSet<>();
//        tags.add("서울");
//        tags.add("한달살기");

        log.info("ids={}", reviewTagRepository.findReviewTagsByTags(tags));
    }
}
