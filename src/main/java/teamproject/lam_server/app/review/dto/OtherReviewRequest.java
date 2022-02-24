package teamproject.lam_server.app.review.dto;

import lombok.Data;
import teamproject.lam_server.constants.CategoryConstants.OrderByStatus;

import javax.validation.constraints.Min;

@Data
public class OtherReviewRequest {

    private OrderByStatus orderBy;

    @Min(0)
    private int limit;

}
