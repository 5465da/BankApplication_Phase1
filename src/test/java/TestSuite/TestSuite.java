package TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import dao.*;
import service.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   AdminDAOImplTest.class,
   CustomerDAOImplTest.class,
   EmailDAOImplTest.class,
   LoginDAOimplTest.class,
   AdminServiceImplTest.class,
   CredentialServiceImplTest.class,
   CustomerServiceImplTest.class,
   EmailServerImplTest.class,
   PaymentsTest.class,
   ServiceMenuImplTest.class
})
public class TestSuite {

}
