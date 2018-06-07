package controllers.PretenzUpdateControllerTest;

import SQl.SQLScenarioMapRepository;
import Services.PretenzService;
import controllers.PretenzUpdateController;
import models.QueryDateParams;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.MockitoAnnotations.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PretenzUpdateControllerTest
 **/
public class PretenzUpdateMockTest extends PretenzUpdateAbstractTest {
    @Mock
    private PretenzService pretenzService;

    @Mock
    private SQLScenarioMapRepository mapRepository;

    @InjectMocks
    private PretenzUpdateController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mockMvc = standaloneSetup(controller).build();
        doNothing().when(pretenzService).fullTableReload(anyString());
        doNothing().when(pretenzService).dailyTableUpdate();
        doNothing().when(mapRepository).setScenarioMap(any(QueryDateParams.class));
    }

    /**
     * checks if controller recreate method calls right methods of classes right amount of time
     */
    @Test
    public void checkCallsFromRecreateControllerMethod() throws Exception {
        mockMvc.perform(post("/table/recreate")
                .param("currentMonth", currentMonth)
                .param("currentYear", currentYear)
                .param("otchetMonth", othcetMonth)
                .param("otchetYear", otchetYear))
                .andExpect(status().isOk());
        verify(mapRepository, times(1)).setScenarioMap(any(QueryDateParams.class));
        verify(pretenzService, times(1)).fullTableReload(othcetMonth);
        verifyNoMoreInteractions(pretenzService);
    }

    /**
     * checks if controller update method calls right methods of classes right amount of time
     */
    @Test
    public void checkCallsFromUpdateControllerMethod() throws Exception {
        mockMvc.perform(post("/table/update")
                .param("currentMonth", currentMonth)
                .param("currentYear", currentYear)
                .param("otchetMonth", othcetMonth)
                .param("otchetYear", otchetYear))
                .andExpect(status().isOk());
        verify(pretenzService, times(1)).dailyTableUpdate();
        verifyNoMoreInteractions(pretenzService);
    }
}
