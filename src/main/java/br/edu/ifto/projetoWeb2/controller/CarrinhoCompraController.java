package br.edu.ifto.projetoWeb2.controller;

import br.edu.ifto.projetoWeb2.model.entity.CarrinhoCompra;
import br.edu.ifto.projetoWeb2.model.entity.ItemVenda;
import br.edu.ifto.projetoWeb2.model.repository.CarrinhoCompraRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Scope("request")
@Controller
@RequestMapping("/carrinhoCompra")
public class CarrinhoCompraController {

    @Autowired
    CarrinhoCompraRepository carrinhoCompraRepository;

    @Autowired
    CarrinhoCompra carrinhoCompra;

    @GetMapping("/form")
    public String form(CarrinhoCompra carrinhoCompra, ItemVenda itemVenda) {
        return "/carrinhoCompra/form";
    }

    @PostMapping("/itemVenda/add")
    public ModelAndView itemVendaadd(ItemVenda itemVenda) {
        // Adiciona o itemVenda enviando por parâmetr na lista do carrinho de compra da sessão.
        carrinhoCompra.getItensVenda().add(itemVenda);
        //Associa o carrinho de compra ao itemVenda.
        itemVenda.setCarrinhoCompra(carrinhoCompra);
        return new ModelAndView("redirect:/carrinhoCompra/form");
    }

    @PostMapping("/save")
    public ModelAndView save(CarrinhoCompra carrinhoCompra, HttpSession session) {
        // Atribui o nome informado para o carrinho de compra por meio do
        // form para o objeto da sessão.
        this.carrinhoCompra.setNome(carrinhoCompra.getNome());
        // Salva o carrinho de compras no banco.
        carrinhoCompraRepository.save(this.carrinhoCompra);
        session.invalidate();
        return new ModelAndView("redirect:/carrinhoCompra/list");
    }

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("carrinhosCompra", carrinhoCompraRepository.carrinhoCompras());
        return new ModelAndView("/carrinhoCompra/list"); // Caminho para a view.
    }
}
