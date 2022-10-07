package teamproject.lam_server.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

//    @Query(value = "select * from review r " +
//            "join member m on m.member_id = r.member_id " +
//            "join review_tag rt on r.review_id = rt.review_id " +
//            "where r.title like CONCAT('%',:#{#cond.searchWord},'%') " +
//            "or r.content like CONCAT('%',:#{#cond.searchWord},'%') " +
//            "or m.nickname like CONCAT('%',:#{#cond.searchWord},'%') " +
//            "and r.category in :#{#cond.type.subs} " +
//            "and rt.tag_name in :#{#cond.tags} " +
//            "group by r.review_id " +
//            "order by r.review_id desc",
//            countQuery = "select count(1) from review r " +
//                    "join member m on m.member_id = r.member_id " +
//                    "join review_tag rt on r.review_id = rt.review_id " +
//                    "where r.title like CONCAT('%',:#{#cond.searchWord},'%') " +
//                    "or r.content like CONCAT('%',:#{#cond.searchWord},'%') " +
//                    "or m.nickname like CONCAT('%',:#{#cond.searchWord},'%') " +
//                    "and r.category = :#{#cond.type.code} " +
//                    "and rt.tag_name in :#{#cond.tags} " +
//                    "group by r.review_id",
//            nativeQuery = true)
//    Page<Review> search(@Param("cond") ReviewSearchCond cond, Pageable pageable);

}
