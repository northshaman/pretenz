package controllers.PretenzUpdateControllerTest;

import Utils.TimerForLogs;
import config.AppConfig;
import config.WebConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * for running pretenztest updates
 **/
@ContextConfiguration(classes = {WebConfig.class, AppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PretenzUpdateTest extends PretenzUpdateAbstractTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    /**
     * calls full update on pretenztest.cm_pretenz_all
     */
    @Test
    @Ignore
    public void fullRecreationTest() throws Exception {
        TimerForLogs timer = new TimerForLogs();
        mockMvc.perform(post("/table/recreate")
                .param("currentMonth", currentMonth)
                .param("currentYear", currentYear)
                .param("otchetMonth", othcetMonth)
                .param("otchetYear", otchetYear))
                .andExpect(status().isOk());
        System.out.println(timer.getRound());
    }

    /**
     * calls daily update on pretenztest.cm_pretenz_all
     */
    @Test
//    @Ignore
    public void dailyUpdateTest() throws Exception {
        mockMvc.perform(post("/table/update")
                .param("currentMonth", currentMonth)
                .param("currentYear", currentYear)
                .param("otchetMonth", othcetMonth)
                .param("otchetYear", otchetYear))
                .andExpect(status().isOk());
    }

    /**
     * calls full update on non existing month
     */
    @Test
    public void noFTPFileTest() throws Exception{
        MvcResult result = mockMvc.perform(post("/table/recreate")
                .param("currentMonth", currentMonth)
                .param("currentYear", currentYear)
                .param("otchetMonth", wrongOtchetMonth)
                .param("otchetYear", otchetYear))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        assertEquals(result.getResponse().getContentAsString(), "Не обнаружен файл на FTP сервере! ");
    }


}
