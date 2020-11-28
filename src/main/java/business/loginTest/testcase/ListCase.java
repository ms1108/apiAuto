package business.loginTest.testcase;

import annotation.annotation.BeforeClassRun;
import annotation.annotation.Search;
import base.BaseCase;
import business.loginTest.service_constant.LoginService;

public class ListCase extends BaseCase {
    @Search(addDataBaseCase = LoginCase.class, searchValuePath = "loginName")
    public String search;
    public Integer page = 1;
    public Integer pageSize = 100;

    public ListCase() {
        serverMap = LoginService.List;
    }

    @BeforeClassRun
    public void beforeClass() {
        apiTest(new LoginCase().rightLoginCase());
        apiTest(new LoginCase().rightLoginCase());
    }
}
