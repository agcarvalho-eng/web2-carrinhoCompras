package br.edu.ifto.projetoWeb2.controller;

import br.edu.ifto.projetoWeb2.model.entity.Venda;
import br.edu.ifto.projetoWeb2.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Transactional //VERIFICAR SE SERÁ NECESSÁRIO ESTA ANOTAÇÃO.
@Controller
@RequestMapping("venda")
public class VendaController {

    @Autowired
    VendaRepository vendaRepository;
    /**
     * @param venda necessário devido utilizar no form.html o th:object que faz referência ao objeto esperado no controller.
     * @return
     */
    @GetMapping("/form")
    public String form(Venda venda){
        return "/venda/form";
    }

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model){
        model.addAttribute("msg", "Lista de Vendas");
        model.addAttribute("vendas", vendaRepository.vendas());
        return new ModelAndView(("/venda/list"), model);//Aponta o caminho da view no projeto em /templates/venda.
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("venda", vendaRepository.venda(id));
        return new ModelAndView("/venda/detail", model); //Aponta para o caminho da view no projeto em templates/venda.
    }

    /**
     * @param id
     * @return
     * @PathVariable é utilizado quando o valor da variável é passada diretamente na URL.
     */

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        vendaRepository.remove(id);
        return new ModelAndView("redirect:/venda/list"); //Aponta o caminho da view no projeto em templates/venda.
    }
    /**
     * @param id
     * @return
     * @PathVariable é utilizado quando o valor da variável é passada diretamente na URL
     */
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("venda", vendaRepository.venda(id));
        return new ModelAndView("/venda/form", model); //Aponta para o caminho da view no projeto em templates/venda.
    }
    @PostMapping("/update")
    public ModelAndView update(Venda venda){
        vendaRepository.update(venda);
        return  new ModelAndView("redirect:/venda/list"); //Aponta o caminho da view no projeto em templates/venda.
    }

}
