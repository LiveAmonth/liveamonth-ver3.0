package teamproject.lam_server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.exception.notfound.ServiceNotFound;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentServiceFinder {

    private final List<CommentService> commentServices;

    public CommentService find(String type) {
        return commentServices.stream()
                .filter(commentService -> commentService.getType() == CommentType.valueOf(type.toUpperCase()))
                .findAny()
                .orElseThrow(ServiceNotFound::new);
    }

}
