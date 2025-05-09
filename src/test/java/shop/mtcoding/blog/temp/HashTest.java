package shop.mtcoding.blog.temp;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

public class HashTest {

    @Test
    public void encode_test() {
        //$2a$10$LRLt.kszv59GC3.3exrQBeIncZFTUApvZdGvh5.cLgQJg6YNCj6sq
        //$2a$10$A63YJTFEoiJUfdvpp99gPOqcaQN3QTj.9avJruV2ipiNkoLdFAL1y
        String password = "1234";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashedPassword);
    }

    @Test
    public void decode_test() {
        //$2a$10$LRLt.kszv59GC3.3exrQBeIncZFTUApvZdGvh5.cLgQJg6YNCj6sq
        //$2a$10$A63YJTFEoiJUfdvpp99gPOqcaQN3QTj.9avJruV2ipiNkoLdFAL1y
        String dbPassword = "$2a$10$LRLt.kszv59GC3.3exrQBeIncZFTUApvZdGvh5.cLgQJg6YNCj6sq";
        String password = "1234";
        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        if (dbPassword.equals(encPassword)){
            System.out.println("비밀번호가 같아요");
        }else{
            System.out.println("비밀번호가 달라요");
        }

    }

    @Test
    public void decodev2_test() {
        //$2a$10$LRLt.kszv59GC3.3exrQBeIncZFTUApvZdGvh5.cLgQJg6YNCj6sq
        //$2a$10$A63YJTFEoiJUfdvpp99gPOqcaQN3QTj.9avJruV2ipiNkoLdFAL1y
        String dbPassword = "$2a$10$LRLt.kszv59GC3.3exrQBeIncZFTUApvZdGvh5.cLgQJg6YNCj6sq";
        String password = "1234";
        Boolean isSame = BCrypt.checkpw(password, dbPassword);
        System.out.println(isSame);

    }
}
