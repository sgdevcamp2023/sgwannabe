//package spring.auth.repository;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import spring.auth.domain.GraphUser;
//
//@SpringBootTest
//class FriendShipRepositoryTest {
//
//    @Autowired
//    private FollowRepository followRepository;
//
//    @BeforeEach
//    public void setUp() {
//        followRepository.deleteAll();
//        followRepository.save(new GraphUser("doong@gmail.com", "흰둥이"));
//        followRepository.save(new GraphUser("misun@gmail.com", "봉미선"));
//
//    }
//
//}