package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public Optional<Board> findByIdJoinUserAndReplies(Integer id) {
        try {
            Query query = em.createQuery("select b from Board b join fetch b.user left join fetch b.replies r left join fetch r.user where b.id = :id order by r.id desc", Board.class);
            query.setParameter("id", id);
            Board boardPS = (Board) query.getSingleResult();
            return Optional.of(boardPS);
        } catch (RuntimeException e) {
            return Optional.ofNullable(null);
        }

    }

    public Optional<Board> findByIdJoinUser(Integer id) {
        try {
            Query query = em.createQuery("select b from Board b join fetch b.user where b.id = :id", Board.class);
            query.setParameter("id", id);
            Board boardPS = (Board) query.getSingleResult();
            return Optional.of(boardPS);
        } catch (RuntimeException e) {
            return Optional.ofNullable(null);
        }
    }

    public Optional<Board> findById(Integer id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    public Long totalCount(String keyword) {
        String sql;
        if (!(keyword.isBlank()))
            sql = "select count(b) from Board b where b.isPublic = true and b.title like :keyword";
        else sql = "select count(b) from Board b where b.isPublic = true";
        Query query = em.createQuery(sql, Long.class);
        if (!(keyword.isBlank())) query.setParameter("keyword", "%" + keyword + "%");
        return (Long) query.getSingleResult();
    }

    public Long totalCount(int userId, String keyword) {
        String sql;
        if (!(keyword.isBlank()))
            sql = "select count(b) from Board b where b.isPublic = true or b.user.id = :userId and b.title like :keyword";
        else sql = "select count(b) from Board b where b.isPublic = true or b.user.id = :userId";
        Query query = em.createQuery(sql, Long.class);
        if (!(keyword.isBlank())) query.setParameter("keyword", "%" + keyword + "%");
        query.setParameter("userId", userId);
        return (Long) query.getSingleResult();
    }

    public List<Board> findAll(int page, String keyword) {
        String sql;

        if (keyword.isBlank()) {
            sql = "select b from Board b where b.isPublic = true order by b.id desc";
        } else {
            sql = "select b from Board b where b.isPublic = true and b.title like :keyword order by b.id desc";
        }

        Query query = em.createQuery(sql, Board.class);
        if (!keyword.isBlank()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }

        query.setFirstResult(page * 3);
        query.setMaxResults(3);

        return query.getResultList();
    }

    public List<Board> findAll(Integer userId, int page, String keyword) {
        String sql;
        if (keyword.isBlank()) {
            sql = "select b from Board b where b.isPublic = true or b.user.id = :userId order by b.id desc";
        } else {
            sql = "select b from Board b where b.isPublic = true or b.user.id = :userId and b.title like :keyword order by b.id desc";
        }

        Query query = em.createQuery(sql, Board.class);
        query.setParameter("userId", userId);
        if (!keyword.isBlank()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        query.setFirstResult(page * 3);
        query.setMaxResults(3);
        return query.getResultList();
    }

//    public List<Board> findAll() {
//        String sql = "select b from Board b where b.isPublic = true order by b.id desc";
//        Query query = em.createQuery(sql, Board.class);
//        return query.getResultList();
//    }
//
//    public List<Board> findAll(Integer userId) {
//        String sql = "select b from Board b where b.isPublic = true or b.user.id = :userId order by b.id desc";
//        Query query = em.createQuery(sql, Board.class);
//        query.setParameter("userId", userId);
//        return query.getResultList();
//    }

    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    public void deleteById(Integer id) {
        em.createQuery("delete from Board b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}