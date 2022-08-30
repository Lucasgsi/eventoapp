package com.eventoapp.eventoapp.controllers;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.eventoapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository repository;
    @Autowired
    private ConvidadoRepository convidadoRepository;

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    private String form() {
        return "evento/formEvento";
    }

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
    private String salvarForm(@Validated Evento evento, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/cadastrarEvento";
        }
        repository.save(evento);
        attributes.addFlashAttribute("mensagem", "Salvo com sucesso!");
        return "redirect:/cadastrarEvento";
    }

    @RequestMapping(value = "/eventos")
    private ModelAndView listaEventos() {
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = repository.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    private ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
        Evento evento = repository.findById(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
        mv.addObject("convidados", convidados);
        return mv;
    }

    @RequestMapping(value = "/deletarEvento")
    private String deleteEvento(long codigo) {
        Evento evento = repository.findById(codigo);
        repository.delete(evento);
        return "redirect:/eventos";
    }

    //Add convidado, salva direto na base de dados
    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
    private String addConvidado(@PathVariable("codigo") long codigo, @Validated Convidado convidado, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/{codigo}";
        }
        Evento evento = repository.findById(codigo);
        if (evento != null)
            convidado.setEvento(evento);
        convidadoRepository.save(convidado);
        attributes.addFlashAttribute("mensagem", "Adicionado com sucesso!");
        return "redirect:/{codigo}";
    }

    @RequestMapping(value = "/deletarConvidado")
    private String deleteConvidado(String rg) {
        Convidado convidado = convidadoRepository.findByRg(rg);
        convidado.getEvento().getConvidados().remove(convidado);
        repository.save(convidado.getEvento());
        return "redirect:/" + convidado.getEvento().getId().toString();
    }
}
