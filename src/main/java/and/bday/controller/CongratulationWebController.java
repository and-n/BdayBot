package and.bday.controller;

import and.bday.service.CongratulationService;
import and.bday.service.model.CongratulationMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CongratulationWebController {

    private static final Logger log = Logger.getLogger(CongratulationWebController.class);

    private CongratulationService congratulationService;

    @Autowired
    public void setCongratulationService(CongratulationService congratulationService) {
        this.congratulationService = congratulationService;
    }

    @GetMapping("/addconmessage")
    public String addHuman(@RequestParam(name = "part1") String part1, @RequestParam(name = "part2") String part2, Model model) {
        log.info("Add congratulation message " + part1 + " / " + part2);

        try {
            if (part1 == null || part2 == null || part1.isEmpty() || part2.isEmpty()) {
                throw new Exception("Some part is EMPTY!");
            }
            congratulationService.addCongratulationMessage(new CongratulationMessage(part1, part2));
            model.addAttribute("part1", part1);
            model.addAttribute("part2", part2);
        } catch (Exception e) {
            log.error("fail add congratulation message ", e);
            model.addAttribute("error", e);
        }

        return "addconmessage";
    }

}
