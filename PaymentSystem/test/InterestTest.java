import Exception.GeneralException;
import Interest.DTO.InterestDTO;
import Interest.Interest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static Templates.ConsoleTemplates.ERROR_INTEREST_INPUT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InterestTest {
    private String globalAcc = "ACC01";

    @Test
    void testInsertRuleHappy() throws GeneralException {
        String req = "20230101 RULE01 1.95";
        Interest interest = new Interest();
        interest.interestHandler(req);
        List<InterestDTO> resp = Interest.getInterestRules();
        assertTrue(resp.size() > .0);
    }

    @Test
    void testInsertRuleInvalidInput() {
        String req = "Random words";
        Interest interest = new Interest();
        try {
            interest.interestHandler(req);
        } catch (GeneralException e) {
            assertEquals(ERROR_INTEREST_INPUT, e.getMessage());
        }
    }

    @Test
    void testInsertRuleOverridesWithLatestDateInput() throws GeneralException {
        String firstRequest = "20230101 RULE01 1.95";
        String secondRequestSameDate = "20230101 NEWRULE 2.95";
        Interest interest = new Interest();
        interest.interestHandler(firstRequest);
        interest.interestHandler(secondRequestSameDate);
        assertEquals(1, Interest.getInterestRules().size());
    }

    @Test
    void testSorting() throws GeneralException {
        String firstRequest = "20230101 RULE01 1.95";
        String secondRequest = "20220101 NEWRULE 2.95";
        Interest interest = new Interest();
        interest.interestHandler(firstRequest);
        interest.interestHandler(secondRequest);
        assertEquals(2, Interest.getInterestRules().size());
        assertEquals(new BigDecimal("1.95"),Interest.getInterestRules().get(Interest.getInterestRules().size()-1).getRate());
    }

}
