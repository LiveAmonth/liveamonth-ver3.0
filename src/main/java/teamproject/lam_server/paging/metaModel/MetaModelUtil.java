package teamproject.lam_server.paging.metaModel;

import com.querydsl.core.types.Path;

public abstract class MetaModelUtil {
    public static String getColumn(Path<?> path) {
        return path.getMetadata().getName();
    }
}
