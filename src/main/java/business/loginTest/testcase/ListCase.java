package business.loginTest.testcase;

import annotation.annotations.BeforeClassRun;
import annotation.annotations.Search;
import base.BaseListCase;
import business.loginTest.service_constant.LoginService;

public class ListCase extends BaseListCase {
    @Search(addDataBaseCase = LoginCase.class, searchValuePath = "loginName")
    public String search;

    public ListCase() {
        serverMap = LoginService.List;
    }

    @BeforeClassRun
    public void beforeClass() {
        apiTest(new LoginCase().rightLoginCase());
        apiTest(new LoginCase().rightLoginCase());
    }
}
