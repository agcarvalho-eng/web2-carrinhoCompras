package br.edu.ifto.projetoWeb2.controller;

import br.edu.ifto.projetoWeb2.model.entity.*;
import br.edu.ifto.projetoWeb2.model.repository.PessoaFisicaRepository;
import br.edu.ifto.projetoWeb2.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Transactional
@Scope("request")
@Controller
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    VendaRepository repository;

    /**
     * @param venda necessário devido utilizar no form.html o th:object que faz referência ao objeto esperado no controller.
     * @return
     */

    @Autowired
    private Venda venda; //O spring vai criar o objeto na session.

    @GetMapping("/form")
    public String form(Venda venda){
        return "/venda/form";
    }

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model){
        model.addAttribute("msg", "Lista de Vendas");
        model.addAttribute("vendas", repository.vendas());
        return new ModelAndView(("/venda/list"), model);//Aponta o caminho da view no projeto em /templates/venda.
    }

    @PostMapping("/addItem")
    public ModelAndView VendaAddItem(ItemVenda item){
        //System.out.println("ID= " + item.getId());
        //System.out.println("Quantidade= " + item.getQuantidade());
        // Adiciona o itemVenda enviado por parâmetro na lista da venda da sessão
        venda.getItensVenda().add(item);
        // Associa a venda ao item
        item.setVenda(venda);
        //System.out.println("ITENS: "+venda.getItensVenda().size());
        return new ModelAndView("redirect:/produto/list-vitrine");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("venda", repository.venda(id));
        return new ModelAndView("/venda/detail", model); //Aponta para o caminho da view no projeto em templates/venda.
    }


    @GetMapping("/save")
    public ModelAndView save() {
        Pessoa p = new PessoaFisicaRepository().pessoaFisica(1L);
        venda.setPessoa(p);
        venda.setData(LocalDate.now());
        repository.save(venda);
        return new ModelAndView("redirect:/venda/list");
    }


    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/venda/list"); //Aponta o caminho da view no projeto em templates/venda.
    }

    @GetMapping("removeItem/{id}")
    public ModelAndView removeItem(@PathVariable("id") Long id){
        venda.getItensVenda().remove(id);
        return new ModelAndView("redirect:/list-carrinho/" + venda.getId()); //ESTÁ CORRETO ESTA SINTAXE AQUI????
    }

    @GetMapping("/list-carrinho")
    public ModelAndView listarCarrinho() {
        return new ModelAndView("/venda/carrinhoCompra"); //Aponta o caminho da view no projeto em /templates/venda.
    }

    /**
     * @param id
     * @return
     * @PathVariable é utilizado quando o valor da variável é passada diretamente na URL
     */
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("venda", repository.venda(id));
        return new ModelAndView("/venda/form", model); //Aponta para o caminho da view no projeto em templates/venda.
    }
    @PostMapping("/update")
    public ModelAndView update(Venda venda){
        repository.update(venda);
        return  new ModelAndView("redirect:/venda/list"); //Aponta o caminho da view no projeto em templates/venda.
    }

}
