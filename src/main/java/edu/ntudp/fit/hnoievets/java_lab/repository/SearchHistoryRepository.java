package edu.ntudp.fit.hnoievets.java_lab.repository;

import edu.ntudp.fit.hnoievets.java_lab.model.SearchHistory;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SearchHistoryRepository extends CrudRepository<SearchHistory, Integer> {
    @Query(value = "SELECT * from (" +
            " SELECT DISTINCT ON (query) *" +
            " FROM search_history" +
            " ORDER BY query, id DESC" +
            " ) t" +
            " ORDER BY id DESC;"
            , nativeQuery = true)
    List<SearchHistory> findLimitedLastSearchHistory(Limit limit);
}
