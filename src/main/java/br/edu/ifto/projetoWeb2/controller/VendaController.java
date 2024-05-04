package br.edu.ifto.projetoWeb2.controller;

import br.edu.ifto.projetoWeb2.model.entity.ItemVenda;
import br.edu.ifto.projetoWeb2.model.entity.Produto;
import br.edu.ifto.projetoWeb2.model.entity.Venda;
import br.edu.ifto.projetoWeb2.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private ItemVenda itemVenda; //O spring vai criar o objeto na session.

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

/*    @PostMapping("/itemVenda/cria")
    public ModelAndView ItemVendaCria(@RequestParam("descricao") String descricao,
                                     @RequestParam("valor") double valor) {
        // Criar um objeto itemVenda com os atributos recebidos
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setDescricao(descricao);
        itemVenda.setValor(valor);
        itemVenda.setQuantidade(1); // Quantidade fixa igual a 1
        return new ModelAndView("redirect:/itemVenda/add");
    }
 */

    @PostMapping("/itemVenda/add")
    public ModelAndView itemVendaAdd(ItemVenda itemVenda){
        // Adiciona o itemVenda enviado por parâmetro na lista da venda da sessão
        venda.getItensVenda().add(itemVenda);
        // Associa a venda ao itemVenda
        itemVenda.setVenda(venda);
        return new ModelAndView("redirect:/produto/vitrine");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("venda", repository.venda(id));
        return new ModelAndView("/venda/detail", model); //Aponta para o caminho da view no projeto em templates/venda.
    }

    /**
     * @param id
     * @return
     * @PathVariable é utilizado quando o valor da variável é passada diretamente na URL.
     */

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/venda/list"); //Aponta o caminho da view no projeto em templates/venda.
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
