package shop.mtcoding.blog.love;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class LoveRepository {
    private final EntityManager em;

    public Long findByBoardId(int boardId) {
        Query query = em.createQuery("select count(lo) from Love lo where lo.board.id = :boardId");
        query.setParameter("boardId", boardId);

        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Optional<Love> findByUserIdAndBoardId(Integer userId, Integer boardId) {
        Query query = em.createQuery("select lo from Love lo where lo.user.id = :userId and lo.board.id = :boardId", Love.class);
        query.setParameter("userId", userId);
        query.setParameter("boardId", boardId);
        try {
            Love lovePs = (Love) query.getSingleResult();
            return Optional.of(lovePs);
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public Love save(Love love) {
        em.persist(love);
        return love;
    }

    public void deleteById(Integer id) {
        Query query = em.createQuery("delete from Love lo where lo.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public Optional<Love> findById(Integer id) {
        return Optional.ofNullable(em.find(Love.class, id));
    }
}
