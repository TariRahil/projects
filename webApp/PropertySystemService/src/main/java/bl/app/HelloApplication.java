package bl.app;

import bl.rest.PropSysService;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
public class HelloApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    public HelloApplication() {
        singletons.add(new PropSysService());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}