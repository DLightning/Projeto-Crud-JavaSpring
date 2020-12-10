package edu.ieso.projeto.Controller;

import edu.ieso.projeto.Model.Cliente;
import edu.ieso.projeto.Model.Pedido;
import edu.ieso.projeto.Repository.ClienteRepository;
import edu.ieso.projeto.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping({"/clientes"})
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PedidoRepository pedidoRepository;

    ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Funcionando
    //Metodo de Cadastrar Clientes
    @RequestMapping(value = "/cadastrarCliente", method = RequestMethod.GET)
    public String form(){
        return "page/cadcliente";
    }
    @RequestMapping(value = "/cadastrarCliente", method = RequestMethod.POST)
    public String form(Cliente cliente){
        clienteRepository.save(cliente);
        return "redirect:/";
    }


    //Funcionando
    //Metodo de Listar Clientes
    @RequestMapping(value = "/listarCliente")
    public ModelAndView findAll(){
    ModelAndView mv = new ModelAndView("page/bcliente");
    Iterable<Cliente> clientes = clienteRepository.findAll();
    mv.addObject("clientes", clientes);
    return mv;
    }


    //Funcionando
    //Acessar o pedido do cliente ao clicar no nome na lista cliente
    @RequestMapping(value = "/listarPedidosClientes/{id}")
    public ModelAndView listarPedClie(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("page/cliente-pedidos");
        Optional<Cliente> cliente =  clienteRepository.findById(id);

        mv.addObject("pedidos", cliente.get().getPedidos());
        mv.addObject("cliente", cliente.get());
        return mv;
    }
    

    //Funcionando
    //Metodo para atualizar Cliente
    @GetMapping(value="/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cliente Id:" + id));

        model.addAttribute("cliente", cliente);
        return "page/update-cliente";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String updateCliente(@PathVariable("id") Long id,Cliente cliente,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            cliente.setId(id);
            return "page/update-cliente";
        }

        clienteRepository.save(cliente);
        model.addAttribute("clientes", clienteRepository.findAll());
        return "redirect:/listarCliente";

    }


    //Funcionando
    //Metodo para remover um cliente (GET):
    @RequestMapping(value = {"/delete/{id}"})
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {

        if(id==null || id == 0){
            attributes.addFlashAttribute("mensagem", "ID não pode ser vazio ou nullo!");
            return "redirect:/listarCliente";
        }

        Optional<Cliente> cliente =  clienteRepository.findById(id);
        if(cliente.isPresent()){
            clienteRepository.deleteById(id);
            attributes.addFlashAttribute("mensagem", "removido com sucesso!");
        }else{
            attributes.addFlashAttribute("mensagem", " Cliente não existe!");
        }

        return "redirect:/listarCliente";
    }
}
