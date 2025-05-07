package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public User save(User user) {
        System.out.println(user.getId());
        em.persist(user);
        System.out.println(user.getId());
        return user; // 영속객체
    }

    public User saveV1(User user) {
        // 1. insert
        em.createNativeQuery("INSERT INTO user (username, password, email, created_at) VALUES (?, ?, ?, now())")
                .setParameter(1, user.getUsername())
                .setParameter(2, user.getPassword())
                .setParameter(3, user.getEmail())
                .executeUpdate();

        // 2. 조회 후 return
        User result = (User) em.createNativeQuery("SELECT * FROM user WHERE username = ?", User.class)
                .setParameter(1, user.getUsername())
                .getSingleResult();

        return result;
    }

    public Optional<User> findByUsername(String username) {
        try {
            User userPS = em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            // 여기서는 절대 null일 수 없음
            return Optional.of(userPS);
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }
}
