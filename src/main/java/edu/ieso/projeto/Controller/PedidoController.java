package edu.ieso.projeto.Controller;

import edu.ieso.projeto.Model.Cliente;
import edu.ieso.projeto.Model.Enums.PedidoStatus;
import edu.ieso.projeto.Model.Pedido;
import edu.ieso.projeto.Model.Produto;
import edu.ieso.projeto.Repository.ClienteRepository;
import edu.ieso.projeto.Repository.PedidoRepository;
import edu.ieso.projeto.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class PedidoController {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    //Funcionando
    //Metodo para Listar todos produtos
    @RequestMapping(value = "/listarPedidos")
        public ModelAndView findAll(){
            ModelAndView mv = new ModelAndView("page/bpedido");
            List<Pedido> pedidos = pedidoRepository.findAll();
            mv.addObject("pedidos", pedidos);

            return mv;
        }

    //Funcionando
    //DropDown Cliente e Produto
    @RequestMapping(value = "/fazerPedido", method = RequestMethod.GET)
    public ModelAndView showPagPedido(){
        ModelAndView mv = new ModelAndView("page/cadpedido");
        List<Cliente> clientes = clienteRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();

        mv.addObject("produtos", produtos);
        mv.addObject("clientes", clientes);
        return mv;
    }

    //Funcionando
    //Criar Pedido
    @RequestMapping(value = "/fazerPedido", method = RequestMethod.POST)
    public String cadPedidoform(Pedido pedido){
        pedido.setMoment(Instant.now());
        pedido.setPedidoStatus(PedidoStatus.PAID);
        pedidoRepository.save(pedido);
        return "redirect:/fazerPedido";
    }

    //Funcionando
    //Atualizar pedido
    @GetMapping(value="/editPedido/{id}")
    public String showUpdateFormPedido(@PathVariable("id") Long id, Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();
        List<PedidoStatus> status = Arrays.asList(PedidoStatus.values());
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pedido Id:" + id));

        model.addAttribute("status", status);
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clientes);
        model.addAttribute("produtos", produtos);

        return "page/update-pedido";
    }
    @RequestMapping(value="/updatePedido/{id}", method = RequestMethod.POST)
    public String updatePedido(@PathVariable("id") Long id, Pedido pedido,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            pedido.setId(id);
            return "page/update-pedido";
        }
        pedido.setMoment(Instant.now());
        pedidoRepository.save(pedido);
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "redirect:/listarPedidos";

    }


    //FUNCIOANANDOOO
    //Deletar Pedido
    @RequestMapping(value = {"/deletePedido/{id}"})
    public String deletePedido(@PathVariable Long id, RedirectAttributes attributes) {

        if(id==null || id == 0){
            attributes.addFlashAttribute("mensagem", "ID não pode ser vazio ou nullo!");
            return "redirect:/listarPedidos";
        }

        Optional<Pedido> pedido =  pedidoRepository.findById(id);
        if(pedido.isPresent()){
            pedidoRepository.deleteById(id);
            attributes.addFlashAttribute("mensagem", "removido com sucesso!");
        }else{
            attributes.addFlashAttribute("mensagem", " Pedido não existe!");
        }

        return "redirect:/listarPedidos";
    }
}
