package player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class PlayerControllerITCase {
    @Autowired
    PlayerController playerController;

    @Autowired
    Player player;

    Player pSpy;

    @BeforeEach
    void setUp() {
        pSpy = Mockito.spy(player);
        ReflectionTestUtils.setField(playerController, "playerServer", pSpy);
    }

    @Test
    void connectionToEngineTest(){
        System.out.println("*************************************************************************");
        System.out.println("* TEST PLAYER CONTROLLER > CONNECT PLAYER TO ENGINE SERVER              *");
        System.out.println("*************************************************************************");
        assertEquals(0,pSpy.getId());
    }

}