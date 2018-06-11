package and.bday.controller;

import and.bday.service.HumanService;
import and.bday.service.model.Human;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HumanWebController {

    private static final Logger log = Logger.getLogger(HumanWebController.class);

    private HumanService humanService;

    @Autowired
    public void setHumanService(HumanService humanService) {
        this.humanService = humanService;
    }

    @GetMapping("/addhuman")
    public String addHuman(@RequestParam(name = "name") String name, @RequestParam(name = "surname") String surName, @RequestParam(name = "bday") String bday, Model model) {
        if (name == null || surName == null || bday == null
                || name.trim().isEmpty() || surName.trim().isEmpty() || bday.trim().isEmpty()) {
            return "";
        }
        log.info("Add " + name + " " + surName);
        try {
            humanService.addHuman(name, surName, bday);
            model.addAttribute("name", name);
            model.addAttribute("surname", surName);
            model.addAttribute("bday", bday);
        } catch (Exception e) {
            log.error("fail create human ", e);
            model.addAttribute("error", e);
        }

        return "addhuman";
    }

    @GetMapping("/removehuman")
    public String removeHuman(@RequestParam(name = "name") String name, @RequestParam(name = "surname") String surName, Model model) {
        if (name == null || surName == null
                || name.trim().isEmpty() || surName.trim().isEmpty()) {
            model.addAttribute("error", "something is NULL!");
            return "removehuman";
        }
        log.info("remove " + name + " " + surName);

        try {
            humanService.removeHumanByFullName(name + " " + surName);
            model.addAttribute("name", name);
            model.addAttribute("surname", surName);

        } catch (final Exception e) {
            log.error("fail remove human ", e);
            model.addAttribute("error", e);
        }

        return "removehuman";
    }


    @GetMapping("/next")
    public String next(final Model model) {
        List<Human> nextHumanBdays = humanService.whosBdayAtDay(DateTime.now(), DateTime.now().plusDays(21));
        if (nextHumanBdays.isEmpty()) {
            return "next";
        }
        for (int i = 0; i < nextHumanBdays.size() && i < 5; i++) {
            model.addAttribute("human" + i, nextHumanBdays.get(i).toString());
        }

        return "next";
    }
}
