package com.memil.memilog.repository;

import com.memil.memilog.domain.Post;
import com.memil.memilog.request.PostSearch;
import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
