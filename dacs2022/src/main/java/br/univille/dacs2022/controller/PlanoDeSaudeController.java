package br.univille.dacs2022.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.univille.dacs2022.dto.PlanoDeSaudeDTO;
import br.univille.dacs2022.service.PlanoDeSaudeService;

@Controller
@RequestMapping("/planoDeSaude")
public class PlanoDeSaudeController {
    @Autowired
    private PlanoDeSaudeService service;

    @GetMapping
    public ModelAndView index() {
        List<PlanoDeSaudeDTO> planos = service.getAll();

        return new ModelAndView("planoDeSaude/index", "listaPlanos", planos);
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        PlanoDeSaudeDTO plano = new PlanoDeSaudeDTO();
        return new ModelAndView("planoDeSaude/form", "plano", plano);
    }

    @PostMapping(params = "form")
    public ModelAndView save(@Valid @ModelAttribute("plano") PlanoDeSaudeDTO plano, BindingResult BindingResult) {
        if (BindingResult.hasErrors()) {
            return new ModelAndView("planoDeSaude/form");
        }
        service.save(plano);
        return new ModelAndView("redirect:/planoDeSaude");
    }

    @GetMapping(path = "/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") long id) {
        PlanoDeSaudeDTO plano = service.getById(id);
        return new ModelAndView("planoDeSaude/form", "plano", plano);
    }

    @GetMapping(path = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") long id) {
        service.delete(id);
        return new ModelAndView("redirect:/planoDeSaude");
    }

}
